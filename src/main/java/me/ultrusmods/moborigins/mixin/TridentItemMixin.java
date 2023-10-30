package me.ultrusmods.moborigins.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Power;
import me.ultrusmods.moborigins.power.RiptideOverridePower;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(TridentItem.class)
public class TridentItemMixin {
    @ModifyExpressionValue(method = "onStoppedUsing",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isTouchingWaterOrRain()Z"))
    private boolean allowRiptiding$MobOrigins(boolean original, ItemStack stack, World world, LivingEntity user, int remainingUseTicks) {
        // This should always be a safe cast, since this targets a call which is inside a if user inst of PlayerEntity
        PlayerEntity playerEntity = (PlayerEntity) user;
        List<RiptideOverridePower> riptideOverridePowers = PowerHolderComponent.getPowers(playerEntity, RiptideOverridePower.class);
        if (!riptideOverridePowers.isEmpty()) {
            return true;
        }
        return original;
    }

    @ModifyExpressionValue(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isTouchingWaterOrRain()Z"))
    public boolean isTouchingWaterOrRain$UseMobOrigins(boolean original, World world, PlayerEntity playerEntity, Hand hand) {
        List<RiptideOverridePower> riptideOverridePowers = PowerHolderComponent.getPowers(playerEntity, RiptideOverridePower.class);
        if (!riptideOverridePowers.isEmpty()) {
            boolean active = riptideOverridePowers.stream().anyMatch((Power::isActive));
            if (active) {
                return true;
            }
        }

        return original;
    }
}
