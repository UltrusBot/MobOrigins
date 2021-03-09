package io.github.ultrusbot.moborigins.mixin;

import io.github.apace100.origins.component.OriginComponent;
import io.github.ultrusbot.moborigins.power.MobOriginsPowers;
import io.github.ultrusbot.moborigins.power.SpikedPower;
import io.github.ultrusbot.moborigins.power.TotemChancePower;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow public abstract boolean isSpectator();

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At(value = "TAIL"), cancellable = true)
    public void tick(CallbackInfo ci) {
        if (MobOriginsPowers.SNOW_TRAIL.isActive((PlayerEntity)(Object)this)) {
            BlockState blockState = Blocks.SNOW.getDefaultState();
            for(int l = 0; l < 4; ++l) {
                int i = MathHelper.floor(this.getX() + (double)((float)(l % 2 * 2 - 1) * 0.25F));
                int j = MathHelper.floor(this.getY());
                int k = MathHelper.floor(this.getZ() + (double)((float)(l / 2 % 2 * 2 - 1) * 0.25F));
                BlockPos blockPos = new BlockPos(i, j, k);
                if (this.world.getBlockState(blockPos).isAir() && this.world.getBiome(blockPos).getTemperature(blockPos) < 0.8F && blockState.canPlaceAt(this.world, blockPos)) {
                    this.world.setBlockState(blockPos, blockState);
                }
            }
        }
    }
    @Inject(method = "interact", at = @At(value = "HEAD"), cancellable = true)
    public void interact(Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (entity instanceof PlayerEntity && MobOriginsPowers.RIDEABLE_CREATURE.isActive(entity)) {
            if (!this.hasPassengers() && !((PlayerEntity)(Object)this).shouldCancelInteraction()) {
                if (!this.world.isClient) {
                    ((PlayerEntity)(Object)this).startRiding(entity);
                }
                cir.setReturnValue(ActionResult.success(((PlayerEntity)(Object)this).world.isClient));
            } else {
                cir.setReturnValue(ActionResult.FAIL);
            }
        }
    }
    @Inject(method = "damage", at = @At(value = "HEAD"))
    public void damage$MobOrigins(DamageSource source, float amount, CallbackInfoReturnable<Boolean> cir) {
        List<SpikedPower> spikedPowers = OriginComponent.getPowers(((PlayerEntity)(Object)this), SpikedPower.class);
        if (source.getSource() instanceof LivingEntity && !source.getMagic() && !source.isExplosive() && spikedPowers.size() > 0) {
            int damage = spikedPowers.stream().map(SpikedPower::getSpikeDamage).reduce(Integer::sum).get();
            System.out.println(damage);
            if (((PlayerEntity)(Object)this).getRandom().nextFloat() <= 0.75) {
                source.getSource().damage(DamageSource.thorns(((PlayerEntity)(Object)this)), damage);
            }
        }
    }
    @ModifyVariable(method = "tickMovement", at = @At("STORE"), index = 2)
    public Box tickMovement$MobOrigins(Box box2) {
        if (MobOriginsPowers.ITEM_COLLECTOR.isActive(((PlayerEntity)(Object)this))) {
            return box2.expand(2);
        }
        return box2;
    }
}
