package me.ultrusmods.moborigins.power;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import me.ultrusmods.moborigins.MobOriginsMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

import java.util.function.Consumer;
/** {DOCS}
 NAME: Channeling Override
 DESC: Allows using the channeling enchantment without having the enchantment, and allows you to specify an action to be performed on the trident thrower or the entity hit by the lightning bolt.
 PARAMS:
    - {entity_action} {Entity Action} {https://origins.readthedocs.io/en/latest/types/entity_action_types/} {optional} {The action to be performed on the trident thrower.}
    - {hit_entity_action} {Entity Action} {https://origins.readthedocs.io/en/latest/types/entity_action_types/} {optional} {The action to be performed on the entity hit by the lightning bolt.}
 EXAMPLE:
{
  "type": "moborigins:channeling_override",
  "entity_action": {
    "type": "origins:explode",
    "power": 4
  },
  "hit_entity_action": {
    "type": "origins:add_velocity",
    "y": 2
  }
}
 POWER_DESC: This power allows using the channeling enchantment without having the enchantment, and causes the trident thrower to explode and the entity hit by the lightning bolt to be launched into the air.

 */
public class ChannelingOverridePower extends Power {
    private final Consumer<Entity> entityAction;
    private final Consumer<Entity> hitEntityAction;
    public ChannelingOverridePower(PowerType<?> type, LivingEntity entity, Consumer<Entity> entityAction, Consumer<Entity> hitEntityAction ) {
        super(type, entity);
        this.entityAction = entityAction;
        this.hitEntityAction = hitEntityAction;
    }
    public void executeAction(Entity entity) {
        if (entityAction != null) {
            entityAction.accept(entity);
        }
    }
    public void executeHitAction(Entity hitEntity) {
        if (hitEntityAction != null) {
            hitEntityAction.accept(hitEntity);
        }
    }

    public static PowerFactory createFactory() {
        return new PowerFactory<>(MobOriginsMod.id("channeling_override"),
                new SerializableData()
                        .add("entity_action", ApoliDataTypes.ENTITY_ACTION, null)
                        .add("hit_entity_action", ApoliDataTypes.ENTITY_ACTION, null),
                data ->
                        (type, player) ->
                                new ChannelingOverridePower(type, player, data.get("entity_action"), data.get("hit_entity_action")));
    }
}
