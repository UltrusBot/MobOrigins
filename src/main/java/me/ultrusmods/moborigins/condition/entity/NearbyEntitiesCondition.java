package me.ultrusmods.moborigins.condition.entity;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.util.Comparison;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import me.ultrusmods.moborigins.MobOriginsMod;
import net.minecraft.entity.Entity;
import net.minecraft.util.Pair;

import java.util.function.Predicate;

/** {DOCS}
    NAME: Nearby Entities
    DESC: Compare the amount of nearby entities based on a given condition.
!!! tip "The 'actor' entity is the entity with the power, while the 'target' entity is the entity that is being checked."
    PARAMS:
          - {multiplier} {Double} {https://origins.readthedocs.io/en/latest/types/data_types/float/} { }  {The factor by which the checking hitbox is multiplied by, with the starting value being your player hitbox.}
          - {comparison} {Comparison} {https://origins.readthedocs.io/en/latest/types/data_types/comparison/} { }  {The comparison to use.}
          - {compare_to} {Integer} {https://origins.readthedocs.io/en/latest/types/data_types/integer/} { }  {The value to compare to.}
          - {bientity_condition} {BiEntityCondition} {https://origins.readthedocs.io/en/latest/types/bientity_condition_types/} {null}  {The condition to check the nearby entities with.}
    EXAMPLE:
{
  "type": "origins:self_glow",
  "use_teams": false,
  "red": 1,
  "green": 0,
  "blue": 0,
  "condition": {
    "type": "moborigins:nearby_entities",
    "multiplier": 3,
    "comparison": ">=",
    "compare_to": 3,
    "bientity_condition": {
      "type": "origins:target_condition",
      "condition": {
        "type": "origins:entity_group",
        "group": "undead"
      }
    }
  }
}
    POWER_DESC: Makes the entity glow red if there are 3 or more undead entities within 3 blocks of it.
 */
public class NearbyEntitiesCondition {
    public static ConditionFactory<Entity> createFactory() {
        return new ConditionFactory<>(MobOriginsMod.id("nearby_entities"), new SerializableData()
                .add("multiplier", SerializableDataTypes.DOUBLE)
                .add("comparison", ApoliDataTypes.COMPARISON)
                .add("compare_to", SerializableDataTypes.INT)
                .add("bientity_condition", ApoliDataTypes.BIENTITY_CONDITION, null),
                (data, entity) -> {
                    Predicate<Pair<Entity, Entity>> bientityCondition = data.get("bientity_condition");
                    int amount = entity.getWorld().getOtherEntities(entity, entity.getBoundingBox().expand(data.getDouble("multiplier")), otherEntity -> bientityCondition.test(new Pair<>(entity, otherEntity))).size();
                    Comparison comparison = data.get("comparison");
                    int compareTo = data.getInt("compare_to");
                    return comparison.compare(amount, compareTo);
                });
    }
}
