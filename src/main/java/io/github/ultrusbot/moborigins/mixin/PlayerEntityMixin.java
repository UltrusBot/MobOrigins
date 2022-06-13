package io.github.ultrusbot.moborigins.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.ultrusbot.moborigins.entity.PlayerEntityMixinInterface;
import io.github.ultrusbot.moborigins.power.MobOriginsPowers;
import io.github.ultrusbot.moborigins.power.SnowTrailPower;
import io.github.ultrusbot.moborigins.power.SpikedPower;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements PlayerEntityMixinInterface {
    @Shadow public abstract boolean isSpectator();

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tick", at = @At(value = "TAIL"))
    public void tick(CallbackInfo ci) {
        PowerHolderComponent.getPowers(this, SnowTrailPower.class).stream().findFirst().ifPresent(SnowTrailPower::tickTrail);
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
        List<SpikedPower> spikedPowers = PowerHolderComponent.getPowers(((PlayerEntity)(Object)this), SpikedPower.class);
        if (source.getSource() instanceof LivingEntity && !source.isMagic() && !source.isExplosive() && spikedPowers.size() > 0) {
            int damage = spikedPowers.stream().map(SpikedPower::getSpikeDamage).reduce(Integer::sum).get();
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

//    @Nullable
//    @Override
//    public LivingEntity getNearestBeamTarget() {
//        LivingEntity user = (LivingEntity)(Object)this;
//        if (MobOriginsPowers.GUARDIAN_BEAM.isActive(user)) {
//            return user.world.getClosestEntity(LivingEntity.class, new TargetPredicate().setBaseMaxDistance(20d), this,  this.getX(), this.getY(), this.getZ(), this.getBoundingBox().expand(10D));
//        } else {
//            return null;
//        }
//    }
}
