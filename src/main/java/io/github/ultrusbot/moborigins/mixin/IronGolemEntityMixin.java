package io.github.ultrusbot.moborigins.mixin;

import io.github.ultrusbot.moborigins.power.MobOriginsPowers;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.goal.TargetGoal;
import net.minecraft.entity.passive.GolemEntity;
import net.minecraft.entity.passive.IronGolemEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(IronGolemEntity.class)
public class IronGolemEntityMixin extends GolemEntity {


    protected IronGolemEntityMixin(EntityType<? extends GolemEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "initGoals", at = @At("TAIL"), cancellable = true)
    protected void initGoals$MobOrigins(CallbackInfo ci) {
        this.targetSelector.add(3, new TargetGoal<>(this, LivingEntity.class, 5, true, false, MobOriginsPowers.PILLAGER_ALIGNED::isActive));
    }
}