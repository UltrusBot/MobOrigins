package me.ultrusmods.moborigins.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import me.ultrusmods.moborigins.MobOriginsMod;
import net.minecraft.entity.LivingEntity;

/** {DOCS}
    NAME: Totem Chance
    DESC: Gives the entity a chance to not consume a totem of undying when they die.
    PARAMS:
        - {chance} {float} {https://origins.readthedocs.io/en/latest/types/data_types/float/} {0.1}  {The chance that the entity will not consume a totem of undying when they die.}
    EXAMPLE:
    {
      "type": "moborigins:totem_chance",
      "chance": 0.5
    }
    POWER_DESC: Gives the entity a 50% chance to not consume a totem of undying when they die.
 */
public class TotemChancePower extends Power {
    private final float breakChance;

    public TotemChancePower(PowerType<?> type, LivingEntity livingEntity, float breakChance) {
        super(type, livingEntity);
        this.breakChance = breakChance;
    }

    public float getBreakChance() {
        return breakChance;
    }

    public static PowerFactory createFactory() {
        return new PowerFactory<>(MobOriginsMod.id("totem_chance"),
                new SerializableData()
                        .add("chance", SerializableDataTypes.FLOAT, 0.1F),
                data ->
                        (type, livingEntity) -> new TotemChancePower(type, livingEntity, data.getFloat("chance")))
                .allowCondition();
    }
}
