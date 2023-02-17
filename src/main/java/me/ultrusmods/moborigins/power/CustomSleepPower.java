package me.ultrusmods.moborigins.power;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import me.ultrusmods.moborigins.MobOriginsMod;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.WorldView;

import java.util.function.Predicate;

/** {DOCS}
    NAME: Custom Sleep Block
    DESC: This power type allows you to sleep on blocks other than beds.

    PARAMS:
    - {block_condition} {Block Condition Type} {https://origins.readthedocs.io/en/latest/types/block_condition_types/} {optional} {This determines what type of block you can sleep on, if null, any block will work.}

    EXAMPLE:
 {
  "type": "moborigins:custom_sleep_block",
  "block_condition": {
    "type": "origins:in_tag",
    "tag": "minecraft:logs"
  }
}

        POWER_DESC: This power will make it so that you can sleep on any log block.
 */
public class CustomSleepPower extends Power {
    private final Predicate<CachedBlockPosition> blockCondition;

    public CustomSleepPower(PowerType<?> type, LivingEntity entity, Predicate<CachedBlockPosition> blockCondition) {
        super(type, entity);
        this.blockCondition = blockCondition;
    }

    public boolean doesApply(WorldView world, BlockPos pos) {
        if (blockCondition == null) {
            return true;
        }
        CachedBlockPosition cbp = new CachedBlockPosition(world, pos, true);
        return blockCondition.test(cbp);
    }

    public static PowerFactory createFactory() {
        return new PowerFactory<>(MobOriginsMod.id("custom_sleep_block"),
                new SerializableData()
                        .add("block_condition", ApoliDataTypes.BLOCK_CONDITION, null),

                data ->
                        (type, livingEntity) -> new CustomSleepPower(type, livingEntity, data.get("block_condition")))
                .allowCondition();
    }
}
