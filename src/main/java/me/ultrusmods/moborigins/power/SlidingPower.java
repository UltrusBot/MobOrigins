package me.ultrusmods.moborigins.power;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import me.ultrusmods.moborigins.MobOriginsMod;
import net.minecraft.block.pattern.CachedBlockPosition;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.WorldView;

import java.util.function.Predicate;


//TODO: add block condition
//Also TODO: Add block conditions for: blockstate isFullCube(), isOpaqueFullCube(), isOpaque()

/** {DOCS} {IGNORE}
 *
 */
//public class SlidingPower extends Power {
//    private final float fallSpeed;
//    private final Predicate<CachedBlockPosition> blockCondition;
//
//    public boolean isSliding = false;
//    public SlidingPower(PowerType<?> type, LivingEntity entity, float fallSpeed, Predicate<CachedBlockPosition> blockCondition) {
//        super(type, entity);
//        this.blockCondition = blockCondition;
//        this.fallSpeed = fallSpeed;
//
//    }
//
//    public boolean doesApply(WorldView world, BlockPos pos) {
//        if (blockCondition == null) {
//            return true;
//        }
//        CachedBlockPosition cbp = new CachedBlockPosition(world, pos, true);
//        return blockCondition.test(cbp);
//    }
//
//    public void tickMovement() {
//        if (entity.isPlayer() && !entity.isOnGround() && !entity.isSpectator() && !entity.isFallFlying() && !((PlayerEntity) entity).getAbilities().flying) {
//            Box box = entity.getBoundingBox().expand(0.001);
//            BlockPos blockPos = new BlockPos(box.minX, box.minY, box.minZ);
//            BlockPos blockPos2 = new BlockPos(box.maxX, box.maxY, box.maxZ);
//            BlockPos.Mutable mutable = new BlockPos.Mutable();
//
//            for(int i = blockPos.getX(); i <= blockPos2.getX(); ++i) {
//                for(int j = blockPos.getY(); j <= blockPos2.getY(); ++j) {
//                    for(int k = blockPos.getZ(); k <= blockPos2.getZ(); ++k) {
//                        mutable.set(i, j, k);
//                        if (doesApply(entity.getWorld(), mutable)) {
//                            double modifiedFallSpeed = entity.isSneaking() ? 0.0 : -fallSpeed;
//                            isSliding = true;
//                            entity.setVelocity(0, modifiedFallSpeed, 0);
//                        } else {
//                            isSliding = false;
//                        }
//                    }
//                }
//            }
//        }
//    }
//
//
//    public static PowerFactory createFactory() {
//        return new PowerFactory<>(MobOriginsMod.id("wall_sliding"),
//                new SerializableData()
//                        .add("fall_speed", SerializableDataTypes.FLOAT)
//                        .add("block_condition", ApoliDataTypes.BLOCK_CONDITION, null),
//
//        data ->
//                        (type, livingEntity) -> new SlidingPower(type, livingEntity, data.getFloat("fall_speed"), data.get("block_condition")))
//                .allowCondition();
//    }
//
//
//}
