package io.github.ultrusbot.moborigins.entity;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.TargetPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.ai.goal.TrackTargetGoal;

import java.util.EnumSet;

public class OriginSlimeAttackWithOwnerGoal extends TrackTargetGoal {
    private final OriginSlimeEntity originSlimeEntity;
    private LivingEntity attacking;
    private int lastAttackTime;

    public OriginSlimeAttackWithOwnerGoal(OriginSlimeEntity originSlimeEntity) {
        super(originSlimeEntity, false);
        this.originSlimeEntity = originSlimeEntity;
        this.setControls(EnumSet.of(Goal.Control.TARGET));
    }

    public boolean canStart() {
        LivingEntity livingEntity = this.originSlimeEntity.getOwner();
        if (livingEntity == null) {
            return false;
        } else {
            this.attacking = livingEntity.getAttacking();
            int i = livingEntity.getLastAttackTime();
            return i != this.lastAttackTime && this.canTrack(this.attacking, TargetPredicate.DEFAULT) && this.originSlimeEntity.canAttackWithOwner(this.attacking, livingEntity);
        }
    }

    public void start() {
        this.mob.setTarget(this.attacking);
        LivingEntity livingEntity = this.originSlimeEntity.getOwner();
        if (livingEntity != null) {
            this.lastAttackTime = livingEntity.getLastAttackTime();
        }

        super.start();
    }
}
