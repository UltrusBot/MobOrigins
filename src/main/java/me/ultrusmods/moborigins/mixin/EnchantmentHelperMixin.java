package me.ultrusmods.moborigins.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import me.ultrusmods.moborigins.power.MimicEnchantPower;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(EnchantmentHelper.class)
public abstract class EnchantmentHelperMixin {

    @Inject(method = "getEquipmentLevel", at = @At("HEAD"), cancellable = true)
    private static void getEquipmentLevel$UPL(Enchantment enchantment, LivingEntity entity, CallbackInfoReturnable<Integer> cir) {
        List<MimicEnchantPower> powers = PowerHolderComponent.getPowers(entity, MimicEnchantPower.class);
        powers.forEach(mimicEnchantPower -> {
            if (EnchantmentHelper.getEnchantmentId(mimicEnchantPower.getEnchantment()) == EnchantmentHelper.getEnchantmentId(enchantment)) {
                cir.setReturnValue(mimicEnchantPower.getLevel());
            }
        });
    }

}
