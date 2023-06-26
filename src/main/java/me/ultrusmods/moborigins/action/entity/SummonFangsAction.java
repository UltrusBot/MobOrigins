package me.ultrusmods.moborigins.action.entity;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import me.ultrusmods.moborigins.MobOriginsMod;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.mob.EvokerFangsEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.shape.VoxelShape;


/** {DOCS}
    NAME: Summon Fangs
    DESC: Summons fangs in a row in front of the entity, or in a circle if the entity is sneaking.
    PARAMS:
    EXAMPLE:
{
    "entity_action": {
        "type": "moborigins:summon_fangs"
    }
}
    POWER_DESC: Executes the summon fangs action.
 */
public class SummonFangsAction {
    public static ActionFactory<Entity> createFactory() {
        return new ActionFactory<>(MobOriginsMod.id("summon_fangs"), new SerializableData(),
                SummonFangsAction::summonFangs);
    }
    public static void summonFangs(SerializableData.Instance instance, Entity entity) {
        double d = entity.getY();
        double e = entity.getY() + 1;
        float f = entity.getHeadYaw() + 180;
        int j;
        if (entity.isSneaking()) {
            float h;
            for(j = 0; j < 5; ++j) {
                h = f + (float)j * 3.1415927F * 0.4F;
                conjureFangs(entity, entity.getX() + (double) MathHelper.cos(h) * 1.5D, entity.getZ() + (double)MathHelper.sin(h) * 1.5D, d, e, h, 0);
            }

            for(j = 0; j < 8; ++j) {
                h = f + (float)j * 3.1415927F * 2.0F / 8.0F + 1.2566371F;
                conjureFangs(entity,entity.getX() + (double)MathHelper.cos(h) * 2.5D, entity.getZ() + (double)MathHelper.sin(h) * 2.5D, d, e, h, 3);
            }
        } else {
            Vec3d vector3f = fromPolar(entity.getPitch(0.0f), entity.getHeadYaw());
            for (j = 0; j < 16; ++j) {
                double l = 1.25D * (double) (j + 1);
                conjureFangs(entity, entity.getX() + vector3f.getX() * l, entity.getZ() + vector3f.getZ() * l, d, e, f, j);
            }
        }
    }
    private static void conjureFangs(Entity entity, double x, double z, double maxY, double y, float yaw, int warmup) {
        BlockPos blockPos = BlockPos.create(x, y, z);
        boolean bl = false;
        double d = 0.0D;

        while (blockPos.getY() >= MathHelper.floor(maxY) - 1) {
            BlockPos blockPos2 = blockPos.down();
            BlockState blockState = entity.getWorld().getBlockState(blockPos2);
            if (blockState.isSideSolidFullSquare(entity.getWorld(), blockPos2, Direction.UP)) {
                if (!entity.getWorld().isAir(blockPos)) {
                    BlockState blockState2 = entity.getWorld().getBlockState(blockPos);
                    VoxelShape voxelShape = blockState2.getCollisionShape(entity.getWorld(), blockPos);
                    if (!voxelShape.isEmpty()) {
                        d = voxelShape.getMax(Direction.Axis.Y);
                    }
                }

                bl = true;
                break;
            }

            blockPos = blockPos.down();
        }

        if (bl) {
            entity.getWorld().spawnEntity(new EvokerFangsEntity(entity.getWorld(), x, (double)blockPos.getY() + d, z, yaw, warmup, (LivingEntity) entity));
        }

    }
    public static Vec3d fromPolar(float pitch, float yaw) {
        float f = MathHelper.cos(-yaw * 0.017453292F - 3.1415927F);
        float g = MathHelper.sin(-yaw * 0.017453292F - 3.1415927F);
        float h = -MathHelper.cos(-pitch * 0.017453292F);
        float i = MathHelper.sin(-pitch * 0.017453292F);
        return new Vec3d(g * h, i, f * h);
    }
}
