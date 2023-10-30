package me.ultrusmods.moborigins.mixin;

import me.ultrusmods.moborigins.power.MobOriginsPowers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.s2c.play.EntityPassengersUpdateS2CPacket;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Box;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {
    @Shadow public abstract boolean isSpectator();

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }
    // TODO: Look into this more at some point.
    @Inject(method = "interact", at = @At(value = "HEAD"), cancellable = true)
    public void interact(Entity entity, Hand hand, CallbackInfoReturnable<ActionResult> cir) {
        if (entity instanceof PlayerEntity && MobOriginsPowers.RIDEABLE_CREATURE.isActive(entity)) {
            if (!this.hasPassengers() && !((PlayerEntity)(Object)this).shouldCancelInteraction()) {
                this.startRiding(entity);
                cir.setReturnValue(ActionResult.success(this.getWorld().isClient));
                if (!entity.getWorld().isClient && entity instanceof ServerPlayerEntity) {
                    ((ServerPlayerEntity)entity).networkHandler.send(new EntityPassengersUpdateS2CPacket(entity));
                }
            } else {
                cir.setReturnValue(ActionResult.FAIL);
            }
        }
    }
    @ModifyVariable(method = "tickMovement", at = @At("STORE"), index = 2)
    public Box tickMovement$MobOrigins(Box box2) {
        if (MobOriginsPowers.ITEM_COLLECTOR.isActive(((PlayerEntity)(Object)this))) {
            return box2.expand(2);
        }
        return box2;
    }

}
