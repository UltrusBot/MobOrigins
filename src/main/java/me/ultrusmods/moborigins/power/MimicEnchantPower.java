package me.ultrusmods.moborigins.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import me.ultrusmods.moborigins.MobOriginsMod;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.LivingEntity;

//TODO: Make it a modifying power

/** {DOCS}
    NAME: Mimic Enchant
    DESC: Mimics an enchantment on the entity, acting as if the entity had the enchantment, while not actually having it.
    PARAMS:
        - {enchantment} {Enchantment} {https://origins.readthedocs.io/en/latest/types/data_types/enchantment/} { }  {The enchantment to mimic.}
        - {level} {Integer} {https://origins.readthedocs.io/en/latest/types/data_types/integer/} { }  {The level of the enchantment to mimic.}
    EXAMPLE:
{
  "type": "moborigins:mimic_enchant",
  "enchantment": "minecraft:frost_walker",
  "level": 3
}
    POWER_DESC: Mimics the Frost Walker enchantment on the entity.
 ### Extra Info

This will also change the result of the `origins:enchantment` entity condition.

This doesn't work on all enchantments due to limitations in minecraft. These are the ones that do work:
 - All Protection Enchantments
 - Sweeping Edge
 - Knockback
 - Fire Aspect
 - Respiration
 - Depth Strider
 - Efficiency
 - Looting
 - Aqua Affinity
 - Frost Walker
 - Soul Speed
 - Power
 - Punch
 - Flame
 */
public class MimicEnchantPower extends Power {
    private final Enchantment enchantment;
    private final int level;

    public MimicEnchantPower(PowerType<Power> type, LivingEntity livingEntity, Enchantment enchantment, int level) {
        super(type, livingEntity);
        this.enchantment = enchantment;
        this.level = level;
    }

    public int getLevel() {
        return level;
    }

    public Enchantment getEnchantment() {
        return enchantment;
    }

    public static PowerFactory createFactory() {
        return new PowerFactory<>(MobOriginsMod.id("mimic_enchant"),
                new SerializableData()
                        .add("enchantment", SerializableDataTypes.ENCHANTMENT, null)
                        .add("level", SerializableDataTypes.INT, 1),
                data ->
                        (type, livingEntity) -> new MimicEnchantPower(type, livingEntity, data.get("enchantment"), data.getInt("level")))
                .allowCondition();
    }
}
