package me.ultrusmods.moborigins.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.LivingEntity;

/** {DOCS}
    NAME: Hostile Axolotls
    DESC: Makes the axolotls hostile to the entity.
    PARAMS:
    EXAMPLE:
    {
        "type": "moborigins:hostile_axolotls"
    }
    POWER_DESC: This makes axolotls hostile to the entity.
 */
public class HostileAxolotlsPower extends Power {
    public HostileAxolotlsPower(PowerType<?> type, LivingEntity entity) {
        super(type, entity);
    }
}
