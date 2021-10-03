package io.github.ultrusbot.moborigins.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.ultrusbot.moborigins.power.ActionOnEntityTamePower;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(TameableEntity.class)
public abstract class TameableEntityMixin extends AnimalEntity implements Tameable {
    protected TameableEntityMixin(EntityType<? extends AnimalEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "setOwner", at = @At("TAIL"))
    public void setOwner$MobOrigins(PlayerEntity player, CallbackInfo ci) {
        List<ActionOnEntityTamePower> powers = PowerHolderComponent.getPowers(player, ActionOnEntityTamePower.class).stream().filter(p -> p.shouldExecute(this)).toList();
        powers.forEach((actionOnEntityTamePower -> {
            actionOnEntityTamePower.executeAction(this);
        }));


    }
}
