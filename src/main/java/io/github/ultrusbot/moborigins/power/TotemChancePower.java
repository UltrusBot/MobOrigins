package io.github.ultrusbot.moborigins.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.LivingEntity;

public class TotemChancePower extends Power {
    private float breakChance;
    public TotemChancePower(PowerType<?> type, LivingEntity livingEntity, float breakChance) {
        super(type, livingEntity);
        this.breakChance = breakChance;
    }

    public float getBreakChance() {
        return breakChance;
    }
}
