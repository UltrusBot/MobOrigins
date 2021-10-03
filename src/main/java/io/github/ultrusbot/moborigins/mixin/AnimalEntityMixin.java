package io.github.ultrusbot.moborigins.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.ultrusbot.moborigins.power.ActionOnBreedAnimal;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(AnimalEntity.class)
public abstract class AnimalEntityMixin extends PassiveEntity{
    protected AnimalEntityMixin(EntityType<? extends PassiveEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "breed", at = @At(value = "INVOKE", target = "Lnet/minecraft/server/network/ServerPlayerEntity;incrementStat(Lnet/minecraft/util/Identifier;)V"), locals = LocalCapture.CAPTURE_FAILHARD)
    public void breed$MobOrigins(ServerWorld world, AnimalEntity other, CallbackInfo ci, PassiveEntity passiveEntity, ServerPlayerEntity serverPlayerEntity) {
        List<ActionOnBreedAnimal> powers = PowerHolderComponent.getPowers(serverPlayerEntity, ActionOnBreedAnimal.class).stream().filter(p -> p.shouldExecute(passiveEntity)).toList();
        powers.forEach((actionOnBreedAnimal -> {
            actionOnBreedAnimal.executeAction(passiveEntity);
        }));

    }

}
