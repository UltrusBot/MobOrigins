package me.ultrusmods.moborigins.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.apace100.apoli.component.PowerHolderComponent;
import me.ultrusmods.moborigins.power.MobOriginsPowers;
import me.ultrusmods.moborigins.power.ModifyReputationPower;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.context.LootContext;
import net.minecraft.loot.context.LootContextParameterSet;
import net.minecraft.loot.context.LootContextParameters;
import net.minecraft.loot.context.LootContextTypes;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.Identifier;
import net.minecraft.village.VillagerData;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(VillagerEntity.class)
public abstract class VillagerEntityMixin extends Entity {
    public VillagerEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Shadow public abstract VillagerData getVillagerData();

    @ModifyReturnValue(method = "getReputation", at = @At("RETURN"))
    public int modifyReputation$MobOrigins(int original, PlayerEntity player) {
        return Math.round(PowerHolderComponent.modify(player, ModifyReputationPower.class, original, p -> p.doesApply((LivingEntity)(Object)this)));
    }

    @Inject(method = "onDeath", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/VillagerEntity;releaseAllTickets()V"), cancellable = true)
    public void onDeath(DamageSource source, CallbackInfo ci) {
        if (source.getAttacker() instanceof PlayerEntity player && MobOriginsPowers.PILLAGER_ALIGNED.isActive(source.getAttacker())) {
            String villagerProfession = this.getVillagerData().getProfession().toString();
            VillagerEntity villager = (VillagerEntity)(Object)this;
            LootTable lootTable = this.getServer().getLootManager().getLootTable(new Identifier("moborigins", "illager/" + villagerProfession ));
//            List<ItemStack> loot = lootTable.generateLoot(new LootContext.Builder((ServerWorld) this.world).parameter(LootContextParameters.LAST_DAMAGE_PLAYER, player).parameter(LootContextParameters.THIS_ENTITY, villager).parameter(LootContextParameters.ORIGIN, this.getPos()).parameter(LootContextParameters.DAMAGE_SOURCE, source).build(LootContextTypes.ENTITY));
            List<ItemStack> loot = lootTable.generateLoot(
                    new LootContextParameterSet.Builder((ServerWorld) this.getWorld())
                            .add(LootContextParameters.THIS_ENTITY, villager)
                            .add(LootContextParameters.LAST_DAMAGE_PLAYER, player)
                            .add(LootContextParameters.ORIGIN, villager.getPos())
                            .add(LootContextParameters.DAMAGE_SOURCE, source)
                            .build(LootContextTypes.ENTITY)
                    );
            for (ItemStack stack : loot) {
                this.dropStack(stack);
            }

        }
    }
}
