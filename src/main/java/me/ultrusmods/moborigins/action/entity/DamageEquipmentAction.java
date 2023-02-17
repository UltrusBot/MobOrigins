package me.ultrusmods.moborigins.action.entity;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import me.ultrusmods.moborigins.MobOriginsMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
/** {DOCS}
    NAME: Damage Equipment
    DESC: Damages an entity's equipment at a specified slot by a specified amount.
    PARAMS:
        - {equipment_slot} {EquipmentSlot} {https://origins.readthedocs.io/en/latest/types/data_types/string/} { }  {The equipment slot to damage, options are: `"mainhand"`, `"offhand"`, `"head"`, `"chest"`, `"legs"`, `"feet"`}
        - {amount} {Integer} {https://origins.readthedocs.io/en/latest/types/data_types/integer/} { }  {The amount of damage to deal to the equipment.}
    EXAMPLE:
{
  "type": "apoli:action_over_time",
  "entity_action": {
    "type": "moborigins:damage_equipment",
    "equipment_slot": "head",
    "amount": 1
  },
  "interval": 40,
  "condition": {
    "type": "apoli:exposed_to_sun"
  }
}
    POWER_DESC: Damages the entity's helmet slot item by 1 every 2 seconds while they are exposed to the sun.
 */
public class DamageEquipmentAction {
    public static ActionFactory<Entity> createFactory() {
        return new ActionFactory<>(MobOriginsMod.id("damage_equipment"), new SerializableData()
                .add("equipment_slot", SerializableDataTypes.EQUIPMENT_SLOT)
                .add("amount", SerializableDataTypes.INT),
                DamageEquipmentAction::damageEquipment);
    }

    private static void damageEquipment(SerializableData.Instance data, Entity entity) {
        if (entity instanceof LivingEntity livingEntity) {
            ItemStack itemStack = livingEntity.getEquippedStack(data.get("equipment_slot"));
            if (!itemStack.isEmpty() && itemStack.isDamageable()) {
                itemStack.setDamage(itemStack.getDamage() + data.getInt("amount"));
                if (itemStack.getDamage() >= itemStack.getMaxDamage()) {
                    livingEntity.sendEquipmentBreakStatus(data.get("equipment_slot"));
                    livingEntity.equipStack(data.get("equipment_slot"), ItemStack.EMPTY);
                }
            }
        }
    }
}
