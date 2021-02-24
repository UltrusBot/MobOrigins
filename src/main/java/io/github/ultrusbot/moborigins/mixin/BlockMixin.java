package io.github.ultrusbot.moborigins.mixin;

import io.github.ultrusbot.moborigins.power.MobOriginsPowers;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BlockView;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Block.class)
public class BlockMixin {
    @Inject(method = "onEntityLand", at = @At("HEAD"), cancellable = true)
    public void onEntityLand(BlockView world, Entity entity, CallbackInfo ci) {
        if (MobOriginsPowers.BOUNCY.isActive(entity) && !entity.bypassesLandingEffects()) {
            entity.setVelocity(entity.getVelocity().multiply(1.0F, -0.8, 1.0F));
            ci.cancel();
            return;
        }
    }
}
