package me.ultrusmods.moborigins.action.entity;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import me.ultrusmods.moborigins.MobOriginsMod;
import me.ultrusmods.moborigins.entity.slime.OriginSlimeEntity;
import me.ultrusmods.moborigins.power.DyeableModelColorPower;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

/** {DOCS}
    NAME: Summon Slime
    DESC: Summons a mob origins slime, which is a slime that has the player name, is owned by the player, and has the same color as the player, if they have the Dyeable Model Color Power.
    PARAMS:
    EXAMPLE:
{
    "entity_action": {
        "type": "moborigins:summon_slime",
    }
}
    POWER_DESC: Summons a mob origins slime.
 */
public class SummonSlimeAction {
    public static ActionFactory<Entity> createFactory() {
        return new ActionFactory<>(MobOriginsMod.id("summon_slime"), new SerializableData()
                .add("size", SerializableDataTypes.INT, 2),
                SummonSlimeAction::summonSlime);
    }

    private static void summonSlime(SerializableData.Instance data, Entity entity) {
        OriginSlimeEntity originSlimeEntity = new OriginSlimeEntity(entity.world, entity.getX(), entity.getY(), entity.getZ());
        if (entity instanceof PlayerEntity playerEntity) {
            originSlimeEntity.setOwner(playerEntity);
        }
        originSlimeEntity.setSize(data.getInt("size"), true);
        originSlimeEntity.setCustomName(entity.getDisplayName());
        if (PowerHolderComponent.hasPower(entity, DyeableModelColorPower.class)) {
            DyeableModelColorPower power = PowerHolderComponent.KEY.get(entity).getPowers(DyeableModelColorPower.class).get(0);
            originSlimeEntity.setColor(power.getRed(), power.getGreen(), power.getBlue());
        }
        entity.world.spawnEntity(originSlimeEntity);
    }
}
