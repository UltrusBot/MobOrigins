package me.ultrusmods.moborigins.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import me.ultrusmods.moborigins.power.AddExperienceToResourcePower;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;

@Mixin(ExperienceOrbEntity.class)
public abstract class ExperienceOrbEntityMixin extends Entity {

    @Shadow
    private int amount;

    public ExperienceOrbEntityMixin(EntityType<?> entityType, World world) {
        super(entityType, world);
    }

    @SuppressWarnings("InvalidInjectorMethodSignature")
    @ModifyVariable(
            method = "onPlayerCollision",
            at = @At(value = "INVOKE_ASSIGN", shift = At.Shift.AFTER, target = "Lnet/minecraft/entity/ExperienceOrbEntity;repairPlayerGears(Lnet/minecraft/entity/player/PlayerEntity;I)I"),
            index = 2
    )
    public int changeLeftoverAmount(int value, PlayerEntity player) {
        int newVal = value;
        List<AddExperienceToResourcePower> powers = PowerHolderComponent.getPowers(player, AddExperienceToResourcePower.class);
        for (AddExperienceToResourcePower power : powers) {
            newVal = power.addToResource(newVal);
        }
        return newVal;
    }
}
