package io.github.ultrusbot.moborigins.power;

import io.github.apace100.origins.power.Power;
import io.github.apace100.origins.power.PowerType;
import net.minecraft.entity.player.PlayerEntity;

public class SpikedPower extends Power {
    int spikeDamage;
    public SpikedPower(PowerType<?> type, PlayerEntity player, int damage) {
        super(type, player);
        this.spikeDamage = damage;
    }

    public int getSpikeDamage() {
        return spikeDamage;
    }
}
