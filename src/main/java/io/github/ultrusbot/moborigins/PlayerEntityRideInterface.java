package io.github.ultrusbot.moborigins;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;

public interface PlayerEntityRideInterface {
    ActionResult ride(PlayerEntity player, Hand hand);
}
