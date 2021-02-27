package io.github.ultrusbot.moborigins.mixin;

import io.github.ultrusbot.moborigins.power.MobOriginsPowers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.GuardianEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.entity.mob.GuardianEntity$FireBeamGoal")
public abstract class GuardianMixin extends Goal {

    @Shadow @Final private GuardianEntity guardian;

    @Inject(method = "Lnet/minecraft/entity/mob/GuardianEntity$FireBeamGoal;tick()V", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/mob/GuardianEntity;getTarget()Lnet/minecraft/entity/LivingEntity;", shift = At.Shift.AFTER))
    public void tick$MobOrigins(CallbackInfo ci) {
        LivingEntity livingEntity = this.guardian.getTarget();
        if (MobOriginsPowers.GUARDIAN_ALLY.isActive(livingEntity)) {
            this.guardian.setTarget(null);
        }
    }
}
