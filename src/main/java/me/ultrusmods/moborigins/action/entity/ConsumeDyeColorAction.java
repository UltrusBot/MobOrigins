package me.ultrusmods.moborigins.action.entity;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import me.ultrusmods.moborigins.MobOriginsMod;
import me.ultrusmods.moborigins.power.DyeableModelColorPower;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;

/** {DOCS}
    NAME: Consume Dye
    DESC: Consumes a dye item and blends the color with the entity's Dyeable Model Color Power, if it has one.
    PARAMS:
    EXAMPLE:
{
 "entity_action": {
     "type": "moborigins:consume_dye"
 }
}
    POWER_DESC: Consumes a dye item and blends the color with the entity's Dyeable Model Color Power, if it has one.
 */
public class ConsumeDyeColorAction {
    public static ActionFactory<Entity> createFactory() {
        return new ActionFactory<>(MobOriginsMod.id("consume_dye"), new SerializableData(),
                ConsumeDyeColorAction::consumeDyeColor);


    }

    public static void consumeDyeColor(SerializableData.Instance data, Entity entity) {
        if (entity instanceof PlayerEntity playerEntity) {
            ItemStack itemStack = playerEntity.getMainHandStack();
            if (itemStack.getItem() instanceof DyeItem dyeItem) {
                float[] col = dyeItem.getColor().getColorComponents();
                PowerHolderComponent.getPowers(entity, DyeableModelColorPower.class).forEach(dyeableModelColorPower -> dyeableModelColorPower.blendColor(col));
                itemStack.decrement(1);
            }
        }
    }
}
