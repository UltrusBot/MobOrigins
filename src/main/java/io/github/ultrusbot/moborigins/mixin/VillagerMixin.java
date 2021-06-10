package io.github.ultrusbot.moborigins.mixin;

import io.github.ultrusbot.moborigins.power.MobOriginsPowers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerData;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(VillagerEntity.class)
public abstract class VillagerMixin extends Entity {
    @Shadow public abstract VillagerData getVillagerData();


    public VillagerMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "interactMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/VillagerEntity;beginTradeWith(Lnet/minecraft/entity/player/PlayerEntity;)V"), cancellable = true)
    public void interactMob(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (MobOriginsPowers.PILLAGER_ALIGNED.isActive(player)) {
            cir.setReturnValue(ActionResult.FAIL);
        }
    }
    @Inject(method = "onDeath", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/VillagerEntity;releaseAllTickets()V"), cancellable = true)
    public void onDeath(DamageSource source, CallbackInfo ci) {
        if (source.getAttacker() instanceof PlayerEntity && MobOriginsPowers.PILLAGER_ALIGNED.isActive(source.getAttacker())) {
            PlayerEntity player = (PlayerEntity) source.getAttacker();
            String villagerProfession = this.getVillagerData().getProfession().toString();
            VillagerEntity villager = (VillagerEntity)(Object)this;
            LootTable lootTable = this.getServer().getLootManager().getTable(new Identifier("moborigins", "illager/" + villagerProfession ));
            List<ItemStack> loot = lootTable.generateLoot(new LootContext.Builder((ServerWorld) this.world).parameter(LootContextParameters.LAST_DAMAGE_PLAYER, player).parameter(LootContextParameters.THIS_ENTITY, villager).parameter(LootContextParameters.ORIGIN, this.getPos()).parameter(LootContextParameters.DAMAGE_SOURCE, source).build(LootContextTypes.ENTITY));
            for (ItemStack stack : loot) {
                this.dropStack(stack);
            }

        }
    }

}
