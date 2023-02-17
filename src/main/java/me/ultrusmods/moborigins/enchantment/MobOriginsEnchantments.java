package me.ultrusmods.moborigins.enchantment;

import me.ultrusmods.moborigins.MobOriginsMod;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.util.registry.Registry;

public class MobOriginsEnchantments {
    public static final Enchantment HEAT_PROTECTION = Registry.register(Registry.ENCHANTMENT, MobOriginsMod.id("heat_protection"), new HeatProtectionEnchantment());
    public static final Enchantment GROUND_SPIKES = Registry.register(Registry.ENCHANTMENT, MobOriginsMod.id("ground_spikes"), new GroundSpikesEnchantment());
    public static void init() {

    }
}
