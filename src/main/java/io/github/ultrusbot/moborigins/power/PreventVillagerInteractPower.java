package io.github.ultrusbot.moborigins.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.LivingEntity;

public class PreventVillagerInteractPower extends Power {
    public PreventVillagerInteractPower(PowerType<?> type, LivingEntity entity) {
        super(type, entity);
    }
}
