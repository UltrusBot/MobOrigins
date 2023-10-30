package me.ultrusmods.moborigins.action.block;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import me.ultrusmods.moborigins.MobOriginsMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.cauldron.LeveledCauldronBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Triple;

/** {DOCS}
    NAME: Decrement Cauldron Fluid
    DESC: Decrements the fluid level of a cauldron.
    PARAMS:
    EXAMPLE:
{
  "type": "apoli:action_on_block_use",
  "block_condition": {
    "type": "apoli:block",
    "block": "minecraft:water_cauldron"
  },
  "block_action": {
    "type": "moborigins:decrement_cauldron_fluid"
  },
  "entity_action": {
    "type": "moborigins:set_dyeable_model_color",
    "red": 1.0,
    "green": 1.0,
    "blue": 1.0
  }
}
    POWER_DESC: This power will make it so when you click on a water cauldron, it will decrement the fluid level and reset your dyeable model color to white.
 */
public class DecrementCauldronFluidAction {
    public static ActionFactory<Triple<World, BlockPos, Direction>> createFactory() {
        return new ActionFactory<>(MobOriginsMod.id("decrement_cauldron_fluid"), new SerializableData(), DecrementCauldronFluidAction::action);
    }

    public static void action(SerializableData.Instance data, Triple<World, BlockPos, Direction> block) {
        World world = block.getLeft();
        BlockPos blockPos = block.getMiddle();
        BlockState blockState = block.getLeft().getBlockState(blockPos);
        if (blockState.getBlock() instanceof LeveledCauldronBlock) {
            LeveledCauldronBlock.decrementFluidLevel(blockState, world, blockPos);
        }
    }
}
