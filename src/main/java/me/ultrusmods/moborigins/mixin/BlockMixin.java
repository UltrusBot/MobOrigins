package me.ultrusmods.moborigins.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import me.ultrusmods.moborigins.power.BouncePower;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(Block.class)
public class BlockMixin {
    @Inject(method = "onEntityLand", at = @At("HEAD"), cancellable = true)
    public void onEntityLand(BlockView world, Entity entity, CallbackInfo ci) {
        if (entity.isLiving() && !entity.bypassesLandingEffects() && PowerHolderComponent.hasPower(entity, BouncePower.class)) {
            List<BouncePower> powers = PowerHolderComponent.getPowers(entity, BouncePower.class);
            var velocity = powers.stream().map(BouncePower::getVelocity).reduce(Double::sum).get();
            entity.setVelocity(entity.getVelocity().multiply(1.0, velocity, 1.0));
            ci.cancel();
        }
    }
}
