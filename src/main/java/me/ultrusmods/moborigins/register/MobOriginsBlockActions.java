package me.ultrusmods.moborigins.register;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import me.ultrusmods.moborigins.action.block.DecrementCauldronFluidAction;
import me.ultrusmods.moborigins.action.block.GrowBlockAction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.registry.Registry;
import net.minecraft.world.World;
import org.apache.commons.lang3.tuple.Triple;

public class MobOriginsBlockActions {
    public static void register() {
        register(GrowBlockAction.createFactory());
        register(DecrementCauldronFluidAction.createFactory());
    }

    private static void register(ActionFactory<Triple<World, BlockPos, Direction>> serializer) {
        Registry.register(ApoliRegistries.BLOCK_ACTION, serializer.getSerializerId(), serializer);
    }
}
