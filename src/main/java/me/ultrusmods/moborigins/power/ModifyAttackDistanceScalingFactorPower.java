package me.ultrusmods.moborigins.power;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.ValueModifyingPower;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.util.modifier.Modifier;
import io.github.apace100.calio.data.SerializableData;
import me.ultrusmods.moborigins.MobOriginsMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Pair;

import java.util.List;
import java.util.function.Predicate;

/** {DOCS}
    NAME: Modify Attack Distance Scaling Factor
    DESC: Modifies the distance in which mobs will begin to attack the player.
    PARAMS:
        - {bientity_condition} {bientity_condition} {https://origins.readthedocs.io/en/latest/types/bientity_condition_types/} { }  {A bi-entity condition with the actor, the mob, and the target, the player.}
        - {modifier} {modifier} {https://origins.readthedocs.io/en/latest/types/data_types/attribute_modifier/} { }  {The modifier to apply to the scaling factor.}
        - {modifiers} {List of Modifier Types} {https://origins.readthedocs.io/en/latest/types/data_types/attribute_modifier/} { }  {A list of modifiers to apply to the scaling factor.}
    EXAMPLE:
{
  "type": "moborigins:modify_attack_distance_scale",
  "modifier": {
    "operation": "multiply_total",
    "value": -1
  },
  "bientity_condition": {
    "type": "origins:actor_condition",
    "condition": {
      "type": "origins:in_tag",
      "tag": "minecraft:raiders"
    }
  }
}
    POWER_DESC: Makes it so raiders are peaceful towards the player, but will attack them if the player attacks them first.
 */
public class ModifyAttackDistanceScalingFactorPower extends ValueModifyingPower {
    private final Predicate<Pair<Entity, Entity>> biEntityCondition;

    public ModifyAttackDistanceScalingFactorPower(PowerType<?> type, LivingEntity entity, Predicate<Pair<Entity, Entity>> biEntityCondition) {
        super(type, entity);
        this.biEntityCondition = biEntityCondition;
    }

    public static PowerFactory<Power> createFactory() {
        return new PowerFactory<>(MobOriginsMod.id("modify_attack_distance_scale"),
                new SerializableData()
                        .add("bientity_condition", ApoliDataTypes.BIENTITY_CONDITION, null)
                        .add("modifier", Modifier.DATA_TYPE, null)
                        .add("modifiers", Modifier.LIST_TYPE, null),
                data ->
                        (type, livingEntity) -> {
                            ModifyAttackDistanceScalingFactorPower power = new ModifyAttackDistanceScalingFactorPower(type, livingEntity, data.get("bientity_condition"));
                            data.ifPresent("modifier", power::addModifier);
                            data.<List<Modifier>>ifPresent("modifiers", mods ->
                                    mods.forEach(power::addModifier));
                            return power;
                        })
                .allowCondition();
    }

    public boolean doesApply(Entity other) {
        return biEntityCondition == null || biEntityCondition.test(new Pair(other, entity));
    }
}
