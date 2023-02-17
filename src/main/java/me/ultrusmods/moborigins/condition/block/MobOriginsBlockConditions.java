package me.ultrusmods.moborigins.condition.block;

import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import me.ultrusmods.moborigins.MobOriginsMod;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.util.registry.Registry;

public class MobOriginsBlockConditions {
    public static void register() {
        /** {DOCS}
            NAME: Is Air
            DESC: Checks if the block is air.
            PARAMS:
            EXAMPLE:
{
    "type": "moborigins:is_air",
    "block": "minecraft:stone"
}
            POWER_DESC: Checks if the block is air.
         */
        register(new ConditionFactory<>(MobOriginsMod.id("is_air"), new SerializableData(),
                (data, blockPosition) -> blockPosition.getBlockState().isAir()));
        /** {DOCS}
            NAME: Is Solid
            DESC: Checks if the block is solid.
            PARAMS:
            EXAMPLE:
{
    "type": "moborigins:is_solid",
    "block": "minecraft:stone"
}
            POWER_DESC: Checks if the block is solid.
         */
        register(new ConditionFactory<>(MobOriginsMod.id("is_solid"), new SerializableData(),
                (data, blockPosition) -> blockPosition.getBlockState().isSolidBlock(blockPosition.getWorld(), blockPosition.getBlockPos())));

    }

    private static void register(ConditionFactory<CachedBlockPosition> conditionFactory) {
        Registry.register(ApoliRegistries.BLOCK_CONDITION, conditionFactory.getSerializerId(), conditionFactory);
    }
}
