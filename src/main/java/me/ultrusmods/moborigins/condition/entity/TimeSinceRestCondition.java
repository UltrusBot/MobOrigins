package me.ultrusmods.moborigins.condition.entity;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.util.Comparison;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import me.ultrusmods.moborigins.MobOriginsMod;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.math.MathHelper;

/** {DOCS}
    NAME: Time Since Rest
    DESC: Checks how long the player has been sleeping, time is in ticks.
    PARAMS:
     - {comparison} {Comparison} {https://origins.readthedocs.io/en/latest/types/data_types/comparison/} { }  {The comparison to use.}
     - {compare_to} {Integer} {https://origins.readthedocs.io/en/latest/types/data_types/integer/} { }  {The value to compare to.}
    EXAMPLE:
    {
        "entity_condition": {
            "type": "moborigins:time_since_rest",
            "comparison": "greater_than",
            "compare_to": 100
        }
    }
    POWER_DESC: Checks how long the player has been sleeping.
 */
public class TimeSinceRestCondition {
    public static ConditionFactory<Entity> createFactory() {
        return new ConditionFactory<>(MobOriginsMod.id("time_since_rest"), new SerializableData()
                .add("comparison", ApoliDataTypes.COMPARISON)
                .add("compare_to", SerializableDataTypes.INT),
                (data, entity) -> ((Comparison) data.get("comparison")).compare(MathHelper.clamp(((ServerPlayerEntity) entity).getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST)), 1, Integer.MAX_VALUE), data.getInt("compare_to")));
    }
}
