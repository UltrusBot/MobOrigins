package io.github.ultrusbot.moborigins.power;

import io.github.apace100.origins.power.Power;
import io.github.apace100.origins.power.PowerType;
import net.minecraft.entity.player.PlayerEntity;

public class RiptideOverridePower extends Power {
    private int tridentDamage;
    public RiptideOverridePower(PowerType<?> type, PlayerEntity player, int tridentDamage) {
        super(type, player);
        this.tridentDamage = tridentDamage;
    }

    public int getTridentDamage() {
        return tridentDamage;
    }
}
