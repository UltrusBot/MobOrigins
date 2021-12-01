package io.github.ultrusbot.moborigins.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

//TODO: Generalize as BlockTrailPower
public class SnowTrailPower extends Power {
    public SnowTrailPower(PowerType<Power> type, LivingEntity livingEntity) {
        super(type, livingEntity);
    }
    public void tickTrail() {
        BlockState blockState = Blocks.SNOW.getDefaultState();
        for(int l = 0; l < 4; ++l) {
            int i = MathHelper.floor(this.entity.getX() + (double)((float)(l % 2 * 2 - 1) * 0.25F));
            int j = MathHelper.floor(this.entity.getY());
            int k = MathHelper.floor(this.entity.getZ() + (double)((float)(l / 2 % 2 * 2 - 1) * 0.25F));
            BlockPos blockPos = new BlockPos(i, j, k);
            if (this.entity.world.getBlockState(blockPos).isAir() && this.entity.world.getBiome(blockPos).getTemperature() < 0.8F && blockState.canPlaceAt(this.entity.world, blockPos)) {
                this.entity.world.setBlockState(blockPos, blockState);
            }
        }
    }
}
