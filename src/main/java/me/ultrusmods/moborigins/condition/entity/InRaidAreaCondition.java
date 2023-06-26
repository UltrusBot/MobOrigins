package me.ultrusmods.moborigins.condition.entity;

import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.calio.data.SerializableData;
import me.ultrusmods.moborigins.MobOriginsMod;
import net.minecraft.entity.Entity;
import net.minecraft.server.world.ServerWorld;

/** {DOCS}
  NAME: In Raid Area

  DESC:
  This condition checks if the entity is in a raid area.

 PARAMS:
  EXAMPLE:
{
    "type": "origins:conditioned_attribute",
    "modifier": {
        "attribute": "minecraft:generic.attack_damage",
        "operation": "addition",
        "value": 2.0,
        "name": "Increased damage in raid"
    },
    "tick_rate": 20,
    "condition": {
        "type": "moborigins:in_raid_area"
    }
}
POWER_DESC:  This power will increase the player's attack damage by 2.0 while in a raid.
 */
public class InRaidAreaCondition {
    public static ConditionFactory<Entity> createFactory() {
        return new ConditionFactory<>(MobOriginsMod.id("in_raid_area"), new SerializableData(),
                (data, entity) -> {
                    if (!entity.getWorld().isClient() && entity.getWorld() instanceof ServerWorld) {
                        return ((ServerWorld)(entity.getWorld())).getRaidAt(entity.getBlockPos()) != null;
                    } else {
                        return false;
                    }
                });
    }
}
