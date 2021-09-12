package io.github.ultrusbot.moborigins.mixin;

import io.github.ultrusbot.moborigins.power.MobOriginsPowers;
import net.minecraft.entity.attribute.EntityAttributeModifier;
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
    /*
    TODO:
    Turn this into a power called ActionOnTameMob which is called from TameableEntity#setOwner that lets you do a entity action on the tamed entity.
     */
    @Inject(method = "interactMob", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/passive/WolfEntity;setOwner(Lnet/minecraft/entity/player/PlayerEntity;)V", shift = At.Shift.AFTER))
    public void interactMob$MobOrigins(PlayerEntity player, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (MobOriginsPowers.ALPHA_WOLF.isActive(player)) {
            ((WolfEntity)(Object)this).getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH).addPersistentModifier(new EntityAttributeModifier("Wolf Origin Bonus", 10.0, EntityAttributeModifier.Operation.ADDITION));
            ((WolfEntity)(Object)this).getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE).addPersistentModifier(new EntityAttributeModifier("Wolf Origin Bonus", 2.0, EntityAttributeModifier.Operation.ADDITION));
        }
    }
}
