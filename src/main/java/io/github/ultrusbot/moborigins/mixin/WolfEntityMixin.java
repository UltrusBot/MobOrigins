package io.github.ultrusbot.moborigins.mixin;

import io.github.ultrusbot.moborigins.power.MobOriginsPowers;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.passive.WolfEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WolfEntity.class)
public class WolfEntityMixin {
    @Inject(method = "interactMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/WolfEntity;setOwner(Lnet/minecraft/entity/player/PlayerEntity;)V", shift = At.Shift.AFTER))
    public void interactMob$MobOrigins(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (MobOriginsPowers.ALPHA_WOLF.isActive(player)) {
            ((WolfEntity)(Object)this).getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).setBaseValue(30.0D);
            ((WolfEntity)(Object)this).getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).setBaseValue(6.0D);
        }
    }
}
