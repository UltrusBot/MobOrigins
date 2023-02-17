package me.ultrusmods.moborigins.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import me.ultrusmods.moborigins.power.FallSoundPower;
import me.ultrusmods.moborigins.power.ModifyAttackDistanceScalingFactorPower;
import me.ultrusmods.moborigins.power.TotemChancePower;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.item.ItemStack;
import net.minecraft.sound.SoundEvent;
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
    public LivingEntityMixin(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "tryUseTotem", at = @At(value = "INVOKE", target = "Lnet/minecraft/item/ItemStack;decrement(I)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void tryUseTotem$MobOrigins(DamageSource source, CallbackInfoReturnable<Boolean> cir, ItemStack itemStack, ItemStack itemStack2, Hand[] var4, int var5, int var6, Hand hand) {
        List<TotemChancePower> totemChancePowers = PowerHolderComponent.getPowers((LivingEntity) (Object) this, TotemChancePower.class);
        if (totemChancePowers.size() > 0) {
            float chance = totemChancePowers.stream().map(TotemChancePower::getBreakChance).reduce(Float::sum).get();
            if (((LivingEntity) (Object) this).getRandom().nextFloat() < chance) {
                itemStack2.increment(1);
            }
        }
    }

    @Inject(method = "getAttackDistanceScalingFactor", at = @At("TAIL"),locals = LocalCapture.CAPTURE_FAILHARD, cancellable = true)
    void changeAttackDistanceScalingFactor$MobOrigins(Entity entity, CallbackInfoReturnable<Double> cir, double d) {
        float newValue = PowerHolderComponent.modify(((LivingEntity) (Object) this), ModifyAttackDistanceScalingFactorPower.class, (float)d, p -> p.doesApply(entity));
        cir.setReturnValue((double) newValue);
    }

    @Inject(method = "getFallSound", at = @At("HEAD"), cancellable = true)
    public void getFallSounds$MobOrigins(int distance, CallbackInfoReturnable<SoundEvent> cir) {
        List<FallSoundPower> powers = PowerHolderComponent.getPowers((LivingEntity)(Object)this, FallSoundPower.class);
        if (powers.size() > 0) {
            FallSoundPower power = powers.get(0);
            cir.setReturnValue(distance > power.getDistance() ? power.getBigSound() : power.getSmallSound());
        }
    }

}
