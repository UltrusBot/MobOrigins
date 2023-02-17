package me.ultrusmods.moborigins.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import me.ultrusmods.moborigins.MobOriginsMod;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;

/** {DOCS}
    NAME: Snow Trail
    DESC: Leaves a trail of snow wherever the entity walks.
    PARAMS:
    EXAMPLE:
{
  "type": "moborigins:snow_trail"
}
    POWER_DESC: Leaves a trail of snow wherever the entity walks.
 */
public class SnowTrailPower extends Power {
    public SnowTrailPower(PowerType<Power> type, LivingEntity livingEntity) {
        super(type, livingEntity);
        setTicking(false);
    }

    @Override
    public void tick() {
        BlockState blockState = Blocks.SNOW.getDefaultState();
        for(int l = 0; l < 4; ++l) {
            int i = MathHelper.floor(this.entity.getX() + (double)((float)(l % 2 * 2 - 1) * 0.25F));
            int j = MathHelper.floor(this.entity.getY());
            int k = MathHelper.floor(this.entity.getZ() + (double)((float)(l / 2 % 2 * 2 - 1) * 0.25F));
            BlockPos blockPos = new BlockPos(i, j, k);
            if (this.entity.world.getBlockState(blockPos).isAir() && blockState.canPlaceAt(this.entity.getWorld(), blockPos)) {
                this.entity.world.setBlockState(blockPos, blockState);
            }
        }
    }

    public static PowerFactory createFactory() {
        return new PowerFactory<>(MobOriginsMod.id("snow_trail"), new SerializableData(),
                data -> SnowTrailPower::new).allowCondition();
    }

}
