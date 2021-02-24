package io.github.ultrusbot.moborigins.mixin;

import io.github.apace100.origins.component.OriginComponent;
import io.github.apace100.origins.power.ModelColorPower;
import io.github.ultrusbot.moborigins.power.TotemChancePower;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;


import java.util.List;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity {

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(method = "tryUseTotem", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;decrement(I)V"), locals = LocalCapture.CAPTURE_FAILSOFT)
    public void tryUseTotem(DamageSource source, CallbackInfoReturnable<Boolean> cir, ItemStack itemStack, ItemStack itemStack2, Hand var4[], int var5, int var6, Hand hand) {
        List<TotemChancePower> totemChancePowers = OriginComponent.getPowers((LivingEntity)(Object)this, TotemChancePower.class);
        if (totemChancePowers.size() > 0) {
            float chance = totemChancePowers.stream().map(TotemChancePower::getBreakChance).reduce(Float::sum).get();
            if (((LivingEntity)(Object)this).getRandom().nextFloat() < chance) {
                itemStack2.increment(1);
            }

        }
    }

}
