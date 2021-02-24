package io.github.ultrusbot.moborigins.mixin;

import io.github.ultrusbot.moborigins.power.MobOriginsPowers;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.PotionItem;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.List;

@Mixin(PotionItem.class)
public class PotionItemMixin {

    @Inject(method = "finishUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;addStatusEffect(Lnet/minecraft/entity/effect/StatusEffectInstance;)Z"), locals = LocalCapture.CAPTURE_FAILSOFT)
    public void finishUsing$MobOrigins(ItemStack stack, World world, LivingEntity user, CallbackInfoReturnable<ItemStack> cir, PlayerEntity playerEntity, List list, Iterator var6, StatusEffectInstance statusEffectInstance) {
        if (MobOriginsPowers.BETTER_POTIONS.isActive(user)) {
            playerEntity.addStatusEffect(new StatusEffectInstance(statusEffectInstance.getEffectType(), statusEffectInstance.getDuration()*2, statusEffectInstance.getAmplifier(), statusEffectInstance.isAmbient(), statusEffectInstance.shouldShowParticles(), statusEffectInstance.shouldShowIcon()));
        }
    }
}
