package me.ultrusmods.moborigins.action.bientity;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import me.ultrusmods.moborigins.MobOriginsMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.util.Pair;

/** {DOCS}
    NAME: Set Angered At
    DESC: Sets the target entity to be angered at the actor entity for a specified time. This only works on angerable entities, such as: Endermen, Zombified Piglins, Bees, Iron Golems, Polar Bears, and Wolves.
    PARAMS:
        - {time} {int} {https://origins.readthedocs.io/en/latest/types/data_types/integer/} {100}  {The time the entity will be angry for.}
    EXAMPLE:
{
    "bientity_action": {
        "type": "moborigins:set_angered_at",
        "time": 100
    }
}
    POWER_DESC: Sets the entity to be angry at the other entity.
 */
public class SetAngeredAtBiEntityAction {
    public static ActionFactory<Pair<Entity, Entity>> createFactory() {
        return new ActionFactory<>(MobOriginsMod.id("set_angered_at"), new SerializableData()
                .add("time", SerializableDataTypes.INT, 100), SetAngeredAtBiEntityAction::executeAction);
    }

    public static void executeAction(SerializableData.Instance data, Pair<Entity, Entity> entities) {
        if (entities.getRight() instanceof Angerable && entities.getLeft() instanceof LivingEntity) {
            ((Angerable)entities.getRight()).setAngryAt(entities.getLeft().getUuid());
            ((Angerable)entities.getRight()).setAngerTime(data.getInt("time"));
        }
    }
}
