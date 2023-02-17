package me.ultrusmods.moborigins.power;


import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import me.ultrusmods.moborigins.MobOriginsMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

import java.util.function.Predicate;


/** {DOCS}
    NAME: Remove Mob Hostility
    DESC: This power allows you to remove the hostility of certain mobs. It's limited to certain mobs which have the TargetGoal goal type. This means entities with brains (Piglins, Hoglins, and Villagers) won't be affected by this power.
    PARAMS:
        - {entity_condition} {entity_condition} {https://origins.readthedocs.io/en/latest/types/entity_condition_types/} { }  {The condition that the mob must meet to be affected by this power.}
    EXAMPLE:
{
    "type": "moborigins:remove_mob_hostility",
    "entity_condition": {
        "type": "origins:entity_group",
        "group": "undead"
    }
}
    POWER_DESC: Removes the hostility of undead mobs towards the player.
 */
public class RemoveMobHostilityPower extends Power {

    private final Predicate<LivingEntity> entityCondition;

    public RemoveMobHostilityPower(PowerType<?> type, LivingEntity entity, Predicate<LivingEntity> entityCondition) {
        super(type, entity);
        this.entityCondition = entityCondition;
    }

    public boolean apply(Entity entity) {
        return entity instanceof LivingEntity && (entityCondition == null || entityCondition.test((LivingEntity) entity));
    }

    public static PowerFactory createFactory() {
        return new PowerFactory<>(MobOriginsMod.id("remove_mob_hostility"),
                new SerializableData()
                        .add("entity_condition", ApoliDataTypes.ENTITY_CONDITION, null),
                data ->
                        (type, player) -> new RemoveMobHostilityPower(type, player, data.get("entity_condition")))
                .allowCondition();
    }

}
