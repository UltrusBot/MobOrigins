package me.ultrusmods.moborigins.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import me.ultrusmods.moborigins.power.ChannelingOverridePower;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TridentEntity.class)
public abstract class TridentEntityMixin extends PersistentProjectileEntity {

    protected TridentEntityMixin(EntityType<? extends PersistentProjectileEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(method = "onEntityHit", at = @At("TAIL"))
    public void onEntityHit$MobOrigins(EntityHitResult entityHitResult, CallbackInfo ci) {
        Entity tridentThrower = this.getOwner();
        if (this.world.isThundering()) { return; } // Regular channeling behavior if thundering

        PowerHolderComponent.getPowers(tridentThrower, ChannelingOverridePower.class).forEach(channelingOverridePower -> {
            if (channelingOverridePower.isActive()) {
                channelingOverridePower.executeAction(tridentThrower);
                if (this.world instanceof ServerWorld) {
                    Entity entity = entityHitResult.getEntity();
                    channelingOverridePower.executeHitAction(entity);
                    BlockPos blockPos = entity.getBlockPos();
                    if (this.world.isSkyVisible(blockPos)) {
                        LightningEntity lightningEntity = EntityType.LIGHTNING_BOLT.create(this.world);
                        lightningEntity.refreshPositionAfterTeleport(Vec3d.ofBottomCenter(blockPos));
                        lightningEntity.setChanneler(tridentThrower instanceof ServerPlayerEntity ? (ServerPlayerEntity)tridentThrower : null);
                        this.world.spawnEntity(lightningEntity);
                    }
                }
            }
        });
    }
}