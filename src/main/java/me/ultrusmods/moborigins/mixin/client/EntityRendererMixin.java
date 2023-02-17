package me.ultrusmods.moborigins.mixin.client;

import io.github.apace100.apoli.component.PowerHolderComponent;
import me.ultrusmods.moborigins.power.IlluminatePower;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.entity.EntityRenderer;
import net.minecraft.entity.Entity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(EntityRenderer.class)
@Environment(EnvType.CLIENT)
public abstract class EntityRendererMixin<T extends Entity> {

    @Inject(method = "getLight", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/render/LightmapTextureManager;pack(II)I", shift = At.Shift.BEFORE), cancellable = true)
    public void redirectEntityLight$UPL(T entity, float tickDelta, CallbackInfoReturnable<Integer> cir) {
        if (PowerHolderComponent.hasPower(entity, IlluminatePower.class)) {
            int num = PowerHolderComponent.getPowers(entity, IlluminatePower.class).stream().mapToInt(IlluminatePower::getLight).max().orElse(0);
            cir.setReturnValue(num << 4);
        }
    }

}
