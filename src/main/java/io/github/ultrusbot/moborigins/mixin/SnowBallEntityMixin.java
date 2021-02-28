package io.github.ultrusbot.moborigins.mixin;

import io.github.ultrusbot.moborigins.power.MobOriginsPowers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.util.hit.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SnowballEntity.class)
public class SnowBallEntityMixin {

    @Inject(method = "onEntityHit", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/Entity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"), cancellable = true)
    public void onEntityHit$MobOrigins(EntityHitResult entityHitResult, CallbackInfo ci) {
        if (MobOriginsPowers.STRONGER_SNOWBALLS.isActive(((SnowballEntity)(Object)this).getOwner())) {
            Entity entity = entityHitResult.getEntity();
            entity.damage(DamageSource.thrownProjectile(((SnowballEntity) (Object) this), ((SnowballEntity) (Object) this).getOwner()), 3);
            return;
        }
    }
}
