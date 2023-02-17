package me.ultrusmods.moborigins.condition.entity;

import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.calio.data.SerializableData;
import me.ultrusmods.moborigins.MobOriginsMod;
import net.minecraft.entity.Entity;

/** {DOCS}
    NAME: Is Full Moon
    DESC: Checks if the moon is full.
    PARAMS:
    EXAMPLE:
{
    "entity_condition": {
        "type": "moborigins:is_full_moon"
    }
}
    POWER_DESC: Checks if the moon is full.
 */
public class IsFullMoonCondition {
    public static ConditionFactory<Entity> createFactory() {
        return new ConditionFactory<>(MobOriginsMod.id("is_full_moon"), new SerializableData(),
                (data, entity) -> entity.world.getMoonSize() == 1.0F);
    }
}
