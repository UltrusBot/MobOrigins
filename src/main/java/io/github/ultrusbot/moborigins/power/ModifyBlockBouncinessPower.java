package io.github.ultrusbot.moborigins.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;

import java.util.function.Predicate;

public class ModifyBlockBouncinessPower extends Power {
    private final Predicate<CachedBlockPosition> blockCondition;
    private final float bounciness;

    public ModifyBlockBouncinessPower(PowerType<?> type, LivingEntity entity, Predicate<CachedBlockPosition> predicate, float bounciness) {
        super(type, entity);
        this.blockCondition = predicate;
        this.bounciness = bounciness;
    }

    public boolean doesApply(BlockPos pos) {
        CachedBlockPosition cbp = new CachedBlockPosition(entity.world, pos, true);
        return doesApply(cbp);
    }

    public boolean doesApply(CachedBlockPosition pos) {
        return blockCondition == null || blockCondition.test(pos);
    }
    public float getBounciness() {
        return bounciness;
    }

}
