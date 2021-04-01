package io.github.ultrusbot.moborigins.mixin;

import io.github.ultrusbot.moborigins.power.MobOriginsPowers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.FollowTargetGoal;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.GoalSelector;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;

@Mixin(RavagerEntity.class)
public abstract class RavagerEntityMixin extends HostileEntity {

    @Shadow @Nullable public abstract Entity getPrimaryPassenger();

    protected RavagerEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyVariable(method = "roar()V", at = @At("STORE"), ordinal = 0)
    private List<Entity> roar$MobOrigins(List<Entity> list) {
        list.removeIf(MobOriginsPowers.PILLAGER_ALIGNED::isActive);
        return list;
    }
    @Redirect(method = "initGoals", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/ai/goal/GoalSelector;add(ILnet/minecraft/entity/ai/goal/Goal;)V", ordinal = 6))
    public void initGoalsFollow$MobOrigins(GoalSelector goalSelector, int priority, Goal goal) {
        this.targetSelector.add(priority, new FollowTargetGoal<>(this, PlayerEntity.class, 10, true, false, (entity) -> !MobOriginsPowers.PILLAGER_ALIGNED.isActive(entity)));
    }

    @ModifyVariable(method = "updateGoalControls", at = @At("STORE"), ordinal = 0)
    private boolean updateGoalControls$MobOrigins(boolean bl) {
        return bl || !(MobOriginsPowers.PILLAGER_ALIGNED.isActive(getPrimaryPassenger()));
    }


    @Override
    protected ActionResult interactMob(PlayerEntity player, Hand hand) {
        if (!this.hasPassengers() && !player.shouldCancelInteraction() && MobOriginsPowers.PILLAGER_ALIGNED.isActive(player)) {
            if (!this.world.isClient) {
                player.startRiding(this);
            }

            return ActionResult.success(this.world.isClient);
        } else {
            return ActionResult.PASS;
        }

    }
}
