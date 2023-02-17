package me.ultrusmods.moborigins.mixin;

//import io.github.apace100.apoli.component.PowerHolderComponent;
//import me.ultrusmods.moborigins.power.BossBarPower;
//import net.minecraft.entity.Entity;
//import net.minecraft.server.network.ServerPlayerEntity;
//import org.spongepowered.asm.mixin.Mixin;
//import org.spongepowered.asm.mixin.Shadow;
//import org.spongepowered.asm.mixin.injection.At;
//import org.spongepowered.asm.mixin.injection.Inject;
//import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
//
//@Mixin(Entity.class)
//public abstract class EntityMixin {
//
//    @Shadow
//    public abstract boolean isLiving();
//
//    @Inject(method = "onStartedTrackingBy", at = @At("HEAD"))
//    void onStartedTrackingBy$UPL(ServerPlayerEntity player, CallbackInfo ci) {
//        if (isLiving()) {
//            PowerHolderComponent.getPowers((Entity) (Object) this, BossBarPower.class).forEach(bossBarPower -> {
//                bossBarPower.addPlayer(player);
//            });
//        }
//    }
//
//    @Inject(method = "onStoppedTrackingBy", at = @At("HEAD"))
//    void onStoppedTrackingBy$UPL(ServerPlayerEntity player, CallbackInfo ci) {
//        if (isLiving()) {
//            PowerHolderComponent.getPowers((Entity) (Object) this, BossBarPower.class).forEach(bossBarPower -> {
//                bossBarPower.removePlayer(player);
//            });
//        }
//    }
//}
