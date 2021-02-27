package io.github.ultrusbot.moborigins.mixin;

import io.github.ultrusbot.moborigins.power.MobOriginsPowers;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BeehiveBlockEntity.class)
public class BeehiveBlockEntityMixin {

    @Inject(method = "angerBees", at = @At("HEAD"), cancellable = true)
    public void angerBees$MobOrigins(@Nullable PlayerEntity player, BlockState state, BeehiveBlockEntity.BeeState beeState, CallbackInfo ci) {
        if (MobOriginsPowers.QUEEN_BEE.isActive(player)) {
            ci.cancel();
            return;
        }
    }
}
