package me.ultrusmods.moborigins.mixin.client;

import com.mojang.blaze3d.systems.RenderSystem;
import io.github.apace100.apoli.component.PowerHolderComponent;
import me.ultrusmods.moborigins.power.FogPower;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BackgroundRenderer;
import net.minecraft.client.render.Camera;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(BackgroundRenderer.class)
public class BackgroundRendererMixin {




//    @ModifyArgs(method = "render", at = @At(value = "INVOKE", target = "Lcom/mojang/blaze3d/systems/RenderSystem;clearColor(FFFF)V", ordinal = 1))
//    private static void ChangeColors$UPL(Args args, Camera camera, float tickDelta, ClientWorld world, int i2, float f) {
//        LivingEntity livingEntity = (LivingEntity) (camera.getFocusedEntity());
//        List<FogPower> fogPowers = PowerHolderComponent.getPowers(livingEntity, FogPower.class);
//        if (!fogPowers.isEmpty()) {
//            float red = fogPowers.stream().map(FogPower::getRed).reduce((a, b) -> a * b).get();
//            float green = fogPowers.stream().map(FogPower::getGreen).reduce((a, b) -> a * b).get();
//            float blue = fogPowers.stream().map(FogPower::getBlue).reduce((a, b) -> a * b).get();
//
//            args.set(0, red);
//            args.set(1, green);
//            args.set(2, blue);
//        }
//    }

    //    @Redirect(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/class_6491;method_24895(Lnet/minecraft/util/math/Vec3d;Lnet/minecraft/class_6491$class_4859;)Lnet/minecraft/util/math/Vec3d;"))
//    private static Vec3d changeBiomeFog$UPL(Vec3d vec3d, class_6491.class_4859 arg) {
//        List<FogPower> fogPowers = PowerHolderComponent.getPowers(MinecraftClient.getInstance().getCameraEntity(), FogPower.class);
//        if (!fogPowers.isEmpty()) {
//            OptionalDouble redDouble = fogPowers.stream().map(FogPower::getRed).mapToDouble((Float::doubleValue)).average();
//            OptionalDouble greenDouble = fogPowers.stream().map(FogPower::getGreen).mapToDouble((Float::doubleValue)).average();
//            OptionalDouble blueDouble = fogPowers.stream().map(FogPower::getBlue).mapToDouble((Float::doubleValue)).average();
//
//            return new Vec3d(redDouble.getAsDouble(), blueDouble.getAsDouble(), greenDouble.getAsDouble());
//        }
//        return vec3d;
//    }
    @Inject(method = "applyFog", at = @At("TAIL"))
    private static void changeFogDistance$UPL(Camera camera, BackgroundRenderer.FogType fogType, float viewDistance, boolean thickFog, float tickDelta, CallbackInfo ci) {
        LivingEntity livingEntity = (LivingEntity) (camera.getFocusedEntity());
        List<FogPower> fogPowers = PowerHolderComponent.getPowers(livingEntity, FogPower.class);
        if (!fogPowers.isEmpty()) {
            int start = fogPowers.stream().map(FogPower::getStart).reduce(Integer::sum).get();
            int end = fogPowers.stream().map(FogPower::getEnd).reduce(Integer::sum).get();
            RenderSystem.setShaderFogStart(start);
            RenderSystem.setShaderFogEnd(end);

        }
    }

    @ModifyVariable(method = "render", at = @At(value = "INVOKE_ASSIGN", target = "Lnet/minecraft/client/world/ClientWorld;getSkyColor(Lnet/minecraft/util/math/Vec3d;F)Lnet/minecraft/util/math/Vec3d;"))
    private static Vec3d changeSkyColor$UPL(Vec3d vec3d) {
        LivingEntity livingEntity = (LivingEntity) (MinecraftClient.getInstance().getCameraEntity());
        List<FogPower> fogPowers = PowerHolderComponent.getPowers(livingEntity, FogPower.class);
        if (!fogPowers.isEmpty()) {
            float red = fogPowers.stream().map(FogPower::getRed).reduce((a, b) -> a * b).get();
            float green = fogPowers.stream().map(FogPower::getGreen).reduce((a, b) -> a * b).get();
            float blue = fogPowers.stream().map(FogPower::getBlue).reduce((a, b) -> a * b).get();
            return new Vec3d(red, green, blue);
        }
        return vec3d;
    }

}
