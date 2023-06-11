package me.ultrusmods.moborigins.mixin;


import me.ultrusmods.moborigins.power.MobOriginsPowers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.RavagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;

@Mixin(RavagerEntity.class)
public abstract class RavagerEntityMixin extends HostileEntity {



    protected RavagerEntityMixin(EntityType<? extends HostileEntity> entityType, World world) {
        super(entityType, world);
    }

    @ModifyVariable(method = "roar()V", at = @At("STORE"), ordinal = 0)
    private List<Entity> roar$MobOrigins(List<Entity> list) {
        list.removeIf(MobOriginsPowers.PILLAGER_ALIGNED::isActive);
        return list;
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

    @Override
    public void travel(Vec3d movementInput) {
        if (this.isAlive()) {
            Entity entity = this.getPrimaryPassenger();
            if (this.hasPassengers() && entity instanceof LivingEntity livingEntity && MobOriginsPowers.PILLAGER_ALIGNED.isActive(livingEntity)) {
                this.setYaw(livingEntity.getYaw());
                this.prevYaw = this.getYaw();
                this.setPitch(livingEntity.getPitch() * 0.5F);
                this.setRotation(this.getYaw(), this.getPitch());
                this.bodyYaw = this.getYaw();
                this.headYaw = this.bodyYaw;
                float f = livingEntity.sidewaysSpeed * 0.5F;
                float g = livingEntity.forwardSpeed;
                if (this.isLogicalSideForUpdatingMovement()) {
                    this.setMovementSpeed((float)this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED) / 2.0F);
                    super.travel(new Vec3d(f, movementInput.y, g));
                } else if (livingEntity instanceof PlayerEntity) {
                    this.setVelocity(Vec3d.ZERO);
                }
                this.updateLimbs(false);
            }
        }
        super.travel(movementInput);
    }
}

