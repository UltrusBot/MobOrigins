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
  NAME: Action On Breed Animal
  
  DESC:
  This power type allows you to run a bi-entity action when you breed a mob.
  PARAMS:
  - {bientity_action} {Bi-entity Action Type} {https://origins.readthedocs.io/en/latest/types/bientity_action_types/} {optional} {This is a bi-entity action with the actor, the player, and the target, the animal you breed last.}
  - {bientity_condition} {Bi-entity Condition Type} {https://origins.readthedocs.io/en/latest/types/bientity_condition_types/} {optional} {This is a bi-entity condtion with the actor, the player, and the target, the animal you breed last.}
  
  EXAMPLE:
  {
  "type": "moborigins:action_on_entity_tame",
  "bientity_condition": {
    "type": "origins:target_condition",
    "condition": {
      "type": "origins:entity_type",
      "entity_type": "minecraft:pig"
    }
  },
  "bientity_action": {
    "type": "origins:target_action",
    "action": {
       "type": "origins:spawn_entity",
       "entity_type": "minecraft:pig",
       "tag": "{Age:-24000}"
    }
  }
}
POWER_DESC:  This power will make it so when you breed two pigs, another baby pig spawns.
 */

public class ActionOnBreedAnimalPower extends Power {
    private final Consumer<Pair<Entity, Entity>> biEntityAction;
    private final Predicate<Pair<Entity, Entity>> bientityCondition;

    public ActionOnBreedAnimalPower(PowerType<?> type, LivingEntity entity, Consumer<Pair<Entity, Entity>> biEntityAction, Predicate<Pair<Entity, Entity>> bientityCondition) {
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
        return new PowerFactory<>(MobOriginsMod.id("action_on_breed_animal"),
                new SerializableData()
                        .add("bientity_action", ApoliDataTypes.BIENTITY_ACTION, null)
                        .add("bientity_condition", ApoliDataTypes.BIENTITY_CONDITION, null),
                data ->
                        (type, player) -> new ActionOnBreedAnimalPower(type, player, data.get("bientity_action"), data.get("bientity_condition")))
                .allowCondition();
    }
}
