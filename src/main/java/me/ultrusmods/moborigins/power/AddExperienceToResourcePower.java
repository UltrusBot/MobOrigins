package me.ultrusmods.moborigins.power;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.VariableIntPower;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import me.ultrusmods.moborigins.MobOriginsMod;
import net.minecraft.entity.LivingEntity;

/** {DOCS}
    NAME: Add Experience to Resource
    DESC: This power type adds experience points to a resource when collected by the player through an experience orb.
    PARAMS:
        - {resource} {Identifier} {https://origins.readthedocs.io/en/latest/types/data_types/identifier/} { }  {The resource to add experience to}
    EXAMPLE:
{
  "type": "apoli:multiple",
  "energy": {
    "type": "origins:resource",
    "min": 0,
    "max": 100,
    "hud_render": {
      "sprite_location": "origins:textures/gui/community/spiderkolo/resource_bar_01.png",
      "bar_index": 3
    }
  },
  "exp": {
    "type": "moborigins:add_experience_to_resource",
    "resource": "*:*_energy"
  }
}
    POWER_DESC: Defines a resource, and adds xp to it.
 */
public class AddExperienceToResourcePower extends Power {
    private final PowerType<?> resourceType;

    public AddExperienceToResourcePower(PowerType<?> type, LivingEntity entity, PowerType<?> resource) {
        super(type, entity);
        this.resourceType = resource;
    }
    public int addToResource(int value) {
        Power resource = PowerHolderComponent.KEY.get(entity).getPower(resourceType);
        int amount = 0;
        if (resource instanceof VariableIntPower power) {
            amount = Math.min(power.getMax() - power.getValue(), value);
            power.setValue(power.getValue() + amount);
            PowerHolderComponent.syncPower(entity, resourceType);
        }
        return value - amount;

    }

    public static PowerFactory createFactory() {
        return new PowerFactory<>(MobOriginsMod.id("add_experience_to_resource"),
                new SerializableData()
                        .add("resource", ApoliDataTypes.POWER_TYPE, null),
                data ->
                        (type, livingEntity) -> new AddExperienceToResourcePower(type, livingEntity, data.get("resource")))
                .allowCondition();
    }
}
