package me.ultrusmods.moborigins.enchantment;

import me.ultrusmods.moborigins.MobOriginsMod;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class MobOriginsEnchantments {
    public static final Enchantment HEAT_PROTECTION = Registry.register(Registries.ENCHANTMENT, MobOriginsMod.id("heat_protection"), new HeatProtectionEnchantment());
    public static final Enchantment GROUND_SPIKES = Registry.register(Registries.ENCHANTMENT, MobOriginsMod.id("ground_spikes"), new GroundSpikesEnchantment());
    public static void init() {

    }
}
