package me.ultrusmods.moborigins.event;

import io.github.apace100.apoli.component.PowerHolderComponent;
import me.ultrusmods.moborigins.power.CustomSleepPower;
import net.fabricmc.fabric.api.entity.event.v1.EntitySleepEvents;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.minecraft.entity.Dismounting;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.Direction;

import java.util.List;


public class SleepEvents {
    public static void init() {
        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (!world.isClient()) {
                List<CustomSleepPower> powers = PowerHolderComponent.getPowers(player, CustomSleepPower.class);
                if (powers.size() > 0) {
                    for (CustomSleepPower customSleepPower : powers) {
                        if (customSleepPower.doesApply(world, hitResult.getBlockPos())) {
                            player.trySleep(hitResult.getBlockPos()).ifLeft(reason -> {
                                if (reason != null && reason.toText() != null) {
                                    player.sendMessage(reason.toText(), true);
                                }
                            });
                            return ActionResult.SUCCESS;
                        }
                    }
                }
            }
            return ActionResult.PASS;
        });
        EntitySleepEvents.MODIFY_SLEEPING_DIRECTION.register(((entity, sleepingPos, sleepingDirection) -> {
            if (entity instanceof PlayerEntity playerEntity) {
                List<CustomSleepPower> powers = PowerHolderComponent.getPowers(playerEntity, CustomSleepPower.class);
                if (powers.size() > 0) {
                    for (CustomSleepPower customSleepPower : powers) {
                        if (customSleepPower.doesApply(playerEntity.world, sleepingPos)) {
                            return Direction.NORTH;
                        }
                    }
                }
            }
            return sleepingDirection;
        }));
        EntitySleepEvents.ALLOW_BED.register(((entity, sleepingPos, state, vanillaResult) -> {
            if (entity instanceof PlayerEntity playerEntity) {
                List<CustomSleepPower> powers = PowerHolderComponent.getPowers(playerEntity, CustomSleepPower.class);
                if (powers.size() > 0) {
                    for (CustomSleepPower customSleepPower : powers) {
                        if (customSleepPower.doesApply(playerEntity.world, sleepingPos)) {
                            return ActionResult.SUCCESS;
                        }
                    }
                }
            }

            return ActionResult.PASS;
        }));
        EntitySleepEvents.ALLOW_RESETTING_TIME.register(player -> {
            var sleepPos = player.getSleepingPosition();
            if (sleepPos.isPresent()) {
                List<CustomSleepPower> powers = PowerHolderComponent.getPowers(player, CustomSleepPower.class);
                if (powers.size() > 0) {
                    if (player.world.isDay()) {
                        return false;
                    }
                    for (CustomSleepPower customSleepPower : powers) {
                        if (customSleepPower.doesApply(player.world, sleepPos.get())) {
                            return true;
                        }
                    }
                }
            }
            return true;
        });

        // This seems to cause issues with actually respawning.
//        EntitySleepEvents.ALLOW_SETTING_SPAWN.register(((player, sleepingPos) -> {
//            List<CustomSleepPower> powers = PowerHolderComponent.getPowers(player, CustomSleepPower.class);
//            if (powers.size() > 0) {
//                for (CustomSleepPower customSleepPower : powers) {
//                    if (customSleepPower.doesApply(player.world, sleepingPos)) {
//                        return true;
//                    }
//                }
//            }
//            return false;
//        }));

        EntitySleepEvents.MODIFY_WAKE_UP_POSITION.register(((entity, sleepingPos, bedState, wakeUpPos) -> {
            List<CustomSleepPower> powers = PowerHolderComponent.getPowers(entity, CustomSleepPower.class);
            if (powers.size() > 0) {
                for (CustomSleepPower customSleepPower : powers) {
                    if (customSleepPower.doesApply(entity.world, sleepingPos)) {
                        return Dismounting.findRespawnPos(entity.getType(), entity.world, sleepingPos, true);
                    }
                }
            }
            return wakeUpPos;
        }));
    }
}
