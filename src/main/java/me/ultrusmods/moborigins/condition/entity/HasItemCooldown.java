package me.ultrusmods.moborigins.condition.entity;

import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import me.ultrusmods.moborigins.MobOriginsMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;

/** {DOCS}
    NAME: Has Item Cooldown
    DESC: Checks if the player has an item cooldown.
    PARAMS:
         - {item} {Item} { } {Optional}  {The item to set the cooldown of.}
         - {hand} {Hand} { } {Optional}  {The hand to check for an item to set the cooldown of, if set, will ignore the item parameter. Valid values are `MAIN_HAND` and `OFF_HAND`.}
    EXAMPLE:
{
    "type": "moborigins:has_item_cooldown",
    "item": "minecraft:iron_sword"
}
    POWER_DESC: Checks if the player has an item cooldown for iron swords.
 */
public class HasItemCooldown {
    public static ConditionFactory<Entity> createFactory() {
        return new ConditionFactory<>(MobOriginsMod.id("has_item_cooldown"), new SerializableData()
                .add("item", SerializableDataTypes.ITEM, null)
                .add("hand", SerializableDataTypes.HAND, null),
                (data, entity) -> {
                    if (entity instanceof PlayerEntity playerEntity) {
                        if (data.isPresent("hand")) {
                            return playerEntity.getItemCooldownManager().isCoolingDown(playerEntity.getStackInHand(data.get("hand")).getItem());
                        } else {
                            return playerEntity.getItemCooldownManager().isCoolingDown(data.get("item"));
                        }
                    } else {
                        return false;
                    }
                });
    }

}
