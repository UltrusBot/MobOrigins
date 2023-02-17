package me.ultrusmods.moborigins.power;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import me.ultrusmods.moborigins.MobOriginsMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Pair;

import java.util.function.Consumer;
import java.util.function.Predicate;

/** {DOCS}
    NAME: Action On Entity Tame

    DESC: This power type allows you to run a bi-entity action when you tame a mob, on the tamer (Actor) and the tamed mob (Target).

    PARAMS:
    - {bientity_action} {Bi-entity Action Type} {https://origins.readthedocs.io/en/latest/types/bientity_action_types/} {optional} {This is a bi-entity action with the actor, the player, and the target, the animal you tamed.}
    - {bientity_condition} {Bi-entity Condition Type} {https://origins.readthedocs.io/en/latest/types/bientity_condition_types/} {optional} {This is a bi-entity condtion with the actor, the player, and the target, the animal you tamed.}

    EXAMPLE:
{
  "type": "moborigins:action_on_entity_tame",
  "bientity_condition": {
    "type": "origins:target_condition",
    "condition": {
      "type": "origins:entity_type",
      "entity_type": "minecraft:cat"
    }
  },
  "bientity_action": {
    "type": "origins:target_action",
    "action": {
      "type": "origins:explode",
      "power": 10
    }
  }
}

    POWER_DESC: This power will make it so when you tame a cat, it explodes.
 */
public class ActionOnEntityTamePower extends Power {
    private final Consumer<Pair<Entity, Entity>> biEntityAction;
    private final Predicate<Pair<Entity, Entity>> bientityCondition;

    public ActionOnEntityTamePower(PowerType<?> type, LivingEntity entity, Consumer<Pair<Entity, Entity>> biEntityAction, Predicate<Pair<Entity, Entity>> bientityCondition) {
        super(type, entity);
        this.biEntityAction = biEntityAction;
        this.bientityCondition = bientityCondition;
    }

    public boolean shouldExecute(Entity tamed) {
        return bientityCondition == null || bientityCondition.test(new Pair<>(entity, tamed));
    }

    public void executeAction(Entity tamed) {
        if (biEntityAction != null) {
            biEntityAction.accept(new Pair<>(entity, tamed));
        }
    }

    public static PowerFactory createFactory() {
        return new PowerFactory<>(MobOriginsMod.id("action_on_entity_tame"),
                new SerializableData()
                        .add("bientity_action", ApoliDataTypes.BIENTITY_ACTION, null)
                        .add("bientity_condition", ApoliDataTypes.BIENTITY_CONDITION, null),
                data ->
                        (type, player) -> new ActionOnEntityTamePower(type, player, data.get("bientity_action"), data.get("bientity_condition")))
                .allowCondition();
    }
}
