package me.ultrusmods.moborigins.action.entity;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import me.ultrusmods.moborigins.MobOriginsMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

/** {DOCS}
    NAME: Jump
    DESC: Makes the entity jump.
    PARAMS:
    EXAMPLE:
    {
      "type": "origins:action_on_block_break",
      "entity_action": {
        "type": "moborigins:jump"
      }
    }
    POWER_DESC: Makes the entity jump when they break a block.
 */
public class JumpAction {
    public static ActionFactory<Entity> createFactory() {
        return new ActionFactory<>(MobOriginsMod.id("jump"), new SerializableData(),
                JumpAction::jump);
    }

    private static void jump(SerializableData.Instance instance, Entity entity) {
        if (entity instanceof PlayerEntity playerEntity) {
            playerEntity.jump();
        }
    }
}
