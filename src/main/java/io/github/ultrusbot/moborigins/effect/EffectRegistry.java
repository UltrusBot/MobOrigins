package io.github.ultrusbot.moborigins.effect;

import io.github.ultrusbot.moborigins.MobOriginsMod;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EffectRegistry {
    public static StatusEffect VEXED;
    public static void register() {
        VEXED = Registry.register(Registry.STATUS_EFFECT, new Identifier(MobOriginsMod.MOD_ID, "vexed"), new CustomStatusEffect(StatusEffectType.NEUTRAL, 0xABABAB));
    }

}
