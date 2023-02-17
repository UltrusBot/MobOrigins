package me.ultrusmods.moborigins.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import me.ultrusmods.moborigins.MobOriginsMod;
import net.minecraft.entity.LivingEntity;


/** {DOCS}
 NAME: Riptide Override
 DESC: Allows using the riptide enchantment without having the enchantment, with a configurable durability cost.
 PARAMS:
 - {trident_damage} {Integer} {https://origins.readthedocs.io/en/latest/types/data_types/integer/} {optional} {The amount of durability to take from the trident when using the riptide enchantment.}
 EXAMPLE:
{
  "type": "moborigins:riptide_override",
  "trident_damage": 10,
  "condition": {
    "type": "origins:daytime",
    "inverted": true
  }
}
 POWER_DESC: This allows using the riptide enchantment without having the enchantment, only during the night, at the cost of 10 durability.

 */
public class RiptideOverridePower extends Power {
    private final int tridentDamage;

    public RiptideOverridePower(PowerType<?> type, LivingEntity player, int tridentDamage) {
        super(type, player);
        this.tridentDamage = tridentDamage;
    }

    public int getTridentDamage() {
        return tridentDamage;
    }

    public static PowerFactory createFactory() {
        return new PowerFactory<>(MobOriginsMod.id("riptide_override"),
                new SerializableData()
                        .add("trident_damage", SerializableDataTypes.INT, 1),
                data ->
                        (type, player) ->
                                new RiptideOverridePower(type, player, data.getInt("trident_damage")))
                .allowCondition();
    }
}
