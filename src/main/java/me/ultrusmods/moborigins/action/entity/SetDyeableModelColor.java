package me.ultrusmods.moborigins.action.entity;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import me.ultrusmods.moborigins.MobOriginsMod;
import me.ultrusmods.moborigins.power.DyeableModelColorPower;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

/** {DOCS}
    NAME: Set Dyeable Model Color
    DESC: Sets the color of the entity's Dyeable Model Color Power, if it has one.
    PARAMS:
        - {red} {Float} {https://origins.readthedocs.io/en/latest/types/data_types/float/}  {1.0} {The red value of the color to set the DyeableModelColorPower to.}
        - {green} {Float} {https://origins.readthedocs.io/en/latest/types/data_types/float/} {1.0}  {The green value of the color to set the DyeableModelColorPower to.}
        - {blue} {Float} {https://origins.readthedocs.io/en/latest/types/data_types/float/} {1.0} {The blue value of the color to set the DyeableModelColorPower to.}
    EXAMPLE:
 {
    "type": "origins:action_over_time",
    "duration": 100,
    "entity_action": {
        "type": "origins:if_else",
        "condition": {
             "type": "origins:daytime"
        },
        "if_action": {
            "type": "moborigins:set_dyeable_model_color",
            "red": 1.0,
            "green": 1.0,
            "blue": 1.0
        },
        "else_action": {
            "type": "moborigins:set_dyeable_model_color",
            "red": 10.0,
            "green": 0.0,
            "blue": 0.0
        }
    }
 }
    POWER_DESC: At night, the entity will be red. During the day, the entity will be white.
 */
public class SetDyeableModelColor {
    public static ActionFactory<Entity> createFactory() {
        return new ActionFactory<>(MobOriginsMod.id("set_dyeable_model_color"), new SerializableData()
                .add("red", SerializableDataTypes.FLOAT, 1.0F)
                .add("green", SerializableDataTypes.FLOAT, 1.0F)
                .add("blue", SerializableDataTypes.FLOAT, 1.0F),
                SetDyeableModelColor::setColor);


    }

    public static void setColor(SerializableData.Instance data, Entity entity) {
        if (entity instanceof LivingEntity) {
            PowerHolderComponent.getPowers(entity, DyeableModelColorPower.class).forEach(dyeableModelColorPower
                    -> dyeableModelColorPower.setColor(data.getFloat("red"), data.getFloat("green"), data.getFloat("blue")));
        }
    }
}
