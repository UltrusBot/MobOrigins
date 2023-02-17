package me.ultrusmods.moborigins.action.entity;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.CooldownPower;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.VariableIntPower;
import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import me.ultrusmods.moborigins.MobOriginsMod;
import me.ultrusmods.moborigins.data.MathOperation;
import me.ultrusmods.moborigins.data.MobOriginsDataTypes;
import net.minecraft.entity.Entity;
/** {DOCS}
    NAME: Resource Math
    DESC: This action type allows to do math, from one resource onto another.
    PARAMS:
    - {power_type} {Power Type} {https://origins.readthedocs.io/en/latest/types/power_types/} {required} {Left side of the equation, and the resource that stores the result.}
    - {math_operator} {Math Operator} {/} {required} {The math operator you want to use. Can be "`add`", "`subtract`", "`multiply`", "`divide`", "`mod`", "`exponent`", or "`set`".}
    - {power_type} {Power Type} {https://origins.readthedocs.io/en/latest/types/power_types/} {required} {Right side of the equation, and the resource that stores the value.}
    EXAMPLE:
{
  "type": "origins:multiple",
  "resource1": {
    "type": "origins:resource",
    "min": 0,
    "max": 100,
    "start_value": 1,
    "hud_render": {
      "sprite_location": "origins:textures/gui/community/spiderkolo/resource_bar_01.png",
      "bar_index": 2
    }
  },
  "resource2": {
    "type": "origins:resource",
    "min": 0,
    "max": 100,
    "start_value": 1,
    "hud_render": {
      "sprite_location": "origins:textures/gui/community/spiderkolo/resource_bar_01.png",
      "bar_index": 3
    }
  },
  "active1": {
    "type": "origins:active_self",
    "entity_action": {
      "type": "moborigins:resource_math",
      "to_resource": "*:*_resource1",
      "from_resource": "*:*_resource2",
      "operator": "add"
    }
  },
  "active2": {
    "type": "origins:active_self",
    "key": {
      "key": "key.origins.secondary_active"
    },
    "entity_action": {
      "type": "moborigins:resource_math",
      "to_resource": "*:*_resource2",
      "from_resource": "*:*_resource1",
      "operator": "add"
    }
  }
}
    POWER_DESC: This power defines two resources, and allows using the primary active to add the value of the secondary resource to the primary resource, and the secondary active to add the value of the primary resource to the secondary resource.
 */
public class ResourceMathAction {
    public static ActionFactory<Entity> createFactory() {
        return new ActionFactory<>(MobOriginsMod.id("resource_math"), new SerializableData()
                .add("to_resource", ApoliDataTypes.POWER_TYPE)
                .add("operator", MobOriginsDataTypes.MATH_OPERATOR)
                .add("from_resource", ApoliDataTypes.POWER_TYPE),
        ResourceMathAction::resourceMath);
    }

    private static void resourceMath(SerializableData.Instance instance, Entity entity) {
        PowerHolderComponent component = PowerHolderComponent.KEY.get(entity);
        PowerType<?> fromType = instance.get("from_resource");
        PowerType<?> toType = instance.get("to_resource");
        MathOperation mathOperation = instance.get("operator");
        Power from = component.getPower(fromType);
        Power to = component.getPower(toType);

        int toVal;
        int fromVal;
        if (to instanceof VariableIntPower toPower) {
            toVal = toPower.getValue();
        } else if (to instanceof CooldownPower toPower) {
            toVal = toPower.getRemainingTicks();
        } else {
            return;
        }

        if (from instanceof VariableIntPower toPower) {
            fromVal = toPower.getValue();
        } else if (from instanceof CooldownPower toPower) {
            fromVal = toPower.getRemainingTicks();
        } else {
            return;
        }
        switch (mathOperation) {
            case ADD -> toVal += fromVal;
            case SUBTRACT -> toVal -= fromVal;
            case MULTIPLY -> toVal *= fromVal;
            case DIVIDE -> toVal /= fromVal;
            case MOD -> toVal %= fromVal;
            case SET -> toVal = fromVal;
            case EXPONENT -> toVal = (int) Math.pow(toVal, fromVal);
            default -> throw new IllegalStateException("Unexpected value: " + mathOperation);
        }

        if (to instanceof VariableIntPower toPower) {
            toPower.setValue(toVal);
            PowerHolderComponent.syncPower(entity, toType);
        }

        if (to instanceof CooldownPower toPower) {
            toPower.setCooldown(toVal);
            PowerHolderComponent.syncPower(entity, toType);
        }
    }
}
