package me.ultrusmods.moborigins.mixin;

import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import io.github.apace100.apoli.component.PowerHolderComponent;
import me.ultrusmods.moborigins.power.HostileAxolotlsPower;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.brain.sensor.AxolotlAttackablesSensor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AxolotlAttackablesSensor.class)
public class AxolotlAttackablesSensorMixin {

    @ModifyReturnValue(method = "isAlwaysHostileTo", at = @At("RETURN"))
    public boolean makeHostile$MobOrigins(boolean original, LivingEntity target) {
        return original || PowerHolderComponent.hasPower(target, HostileAxolotlsPower.class);
    }
}
