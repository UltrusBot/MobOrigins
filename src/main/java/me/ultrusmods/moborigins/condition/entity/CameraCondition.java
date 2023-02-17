package me.ultrusmods.moborigins.condition.entity;


import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.calio.data.SerializableData;
import me.ultrusmods.moborigins.MobOriginsMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;

/** {DOCS}
    NAME: Is First Person
    DESC: Checks if the player is in first person.
    EXAMPLE:
{
    "entity_condition": {
        "type": "moborigins:is_first_person"
    }
}
    POWER_DESC: Checks if the player is in first person.
 */
public class CameraCondition {
    public static ConditionFactory<Entity> createFactory() {
        return new ConditionFactory<>(MobOriginsMod.id("is_first_person"), new SerializableData(),
                (data, entity) -> {
                    if (entity.world.isClient) {
                        return MinecraftClient.getInstance().options.getPerspective().isFirstPerson();
                    } else {
                        return false;
                    }
                });
    }
}
