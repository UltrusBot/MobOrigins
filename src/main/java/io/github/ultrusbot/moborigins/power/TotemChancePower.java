package io.github.ultrusbot.moborigins.power;

import io.github.apace100.origins.power.Power;
import io.github.apace100.origins.power.PowerType;
import net.minecraft.entity.player.PlayerEntity;

public class TotemChancePower extends Power {
    private float breakChance;
    public TotemChancePower(PowerType<?> type, PlayerEntity player, float breakChance) {
        super(type, player);
        this.breakChance = breakChance;
    }

    public float getBreakChance() {
        return breakChance;
    }
}
