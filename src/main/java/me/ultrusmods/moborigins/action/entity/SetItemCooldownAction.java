package me.ultrusmods.moborigins.action.entity;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import me.ultrusmods.moborigins.MobOriginsMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;

/** {DOCS}
    NAME: Set Item Cooldown
    DESC: Sets the cooldown of an item for a player.
    PARAMS:
        - {item} {Item} { } {Optional}  {The item to set the cooldown of.}
        - {ticks} {Integer} {https://origins.readthedocs.io/en/latest/types/data_types/integer/} {Required}  {The amount of ticks to set the cooldown to.}
        - {hand} {Hand} { } {Optional}  {The hand to check for an item to set the cooldown of, if set, will ignore the item parameter. Valid values are `MAIN_HAND` and `OFF_HAND`.}
    EXAMPLE:
{
    "type": "moborigins:set_item_cooldown",
    "item": "minecraft:shield",
    "ticks": 100
}
    POWER_DESC: Sets the cooldown of the shield item to 100 ticks.
 */
public class SetItemCooldownAction {
    public static ActionFactory<Entity> createFactory() {
        return new ActionFactory<>(MobOriginsMod.id("set_item_cooldown"), new SerializableData()
                .add("item", SerializableDataTypes.ITEM, null)
                .add("ticks", SerializableDataTypes.INT)
                .add("hand", SerializableDataTypes.HAND, null),
                ((instance, entity) -> {
                    if (entity instanceof PlayerEntity playerEntity) {
                        Hand hand = instance.get("hand");
                        if (hand != null) {
                            playerEntity.getItemCooldownManager().set(playerEntity.getStackInHand(hand).getItem(), instance.getInt("ticks"));
                        } else {
                            playerEntity.getItemCooldownManager().set(instance.get("item"), instance.getInt("ticks"));
                        }
                    };
                }));
    }
}
