package me.ultrusmods.moborigins.action.block;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import me.ultrusmods.moborigins.MobOriginsMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.Fertilizable;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Triple;

/** {DOCS}
    NAME: Grow Block
    DESC: Grows a block if it is fertilizable.
    PARAMS:
    EXAMPLE:
{
  "type": "apoli:action_on_block_use",
  "block_condition": {
    "type": "apoli:block",
    "block": "minecraft:carrots"
  },
  "block_action": {
    "type": "moborigins:grow"
  }
}
    POWER_DESC: This power will grow carrots when right clicked.
 */
public class GrowBlockAction {
    public static ActionFactory<Triple<World, BlockPos, Direction>> createFactory() {
        return new ActionFactory<>(MobOriginsMod.id("grow"), new SerializableData(), GrowBlockAction::action);
    }

    public static void action(SerializableData.Instance data, Triple<World, BlockPos, Direction> block) {
        World world = block.getLeft();
        BlockPos blockPos = block.getMiddle();
        BlockState blockState = block.getLeft().getBlockState(blockPos);
        if (blockState.getBlock() instanceof Fertilizable fertilizable) {
            if (fertilizable.isFertilizable(world, blockPos, blockState, world.isClient) && world instanceof ServerWorld && fertilizable.canFertilize(world, world.random, blockPos, blockState)) {
                fertilizable.fertilize((ServerWorld) world, world.random, blockPos, blockState);
            }
        }
    }
}