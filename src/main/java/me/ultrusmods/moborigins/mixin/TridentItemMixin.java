package me.ultrusmods.moborigins.mixin;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.Power;
import me.ultrusmods.moborigins.power.RiptideOverridePower;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.TridentItem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(TridentItem.class)
public class TridentItemMixin {
    @Inject(method = "onStoppedUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/enchantment/EnchantmentHelper;getRiptide(Lnet/minecraft/item/ItemStack;)I", shift = At.Shift.AFTER))
    public void tridentStoppedUsing$MobOrigins(ItemStack stack, World world, LivingEntity user, int remainingUseTicks, CallbackInfo ci) {
        PlayerEntity playerEntity = (PlayerEntity) user;
        List<RiptideOverridePower> riptideOverridePowers = PowerHolderComponent.getPowers(playerEntity, RiptideOverridePower.class);
        if (riptideOverridePowers.size() > 0) {
            int damage = riptideOverridePowers.stream().map(RiptideOverridePower::getTridentDamage).reduce(Integer::sum).get();
            int j = EnchantmentHelper.getRiptide(stack);
            if (j > 0 && !playerEntity.isTouchingWaterOrRain()) {
                if (!world.isClient) {
                    stack.damage(damage, playerEntity, ((p) -> {
                        p.sendToolBreakStatus(user.getActiveHand());
                    }));
                }
                float f = playerEntity.getYaw();
                float g = playerEntity.getPitch();
                float h = -MathHelper.sin(f * 0.017453292F) * MathHelper.cos(g * 0.017453292F);
                float k = -MathHelper.sin(g * 0.017453292F);
                float l = MathHelper.cos(f * 0.017453292F) * MathHelper.cos(g * 0.017453292F);
                float m = MathHelper.sqrt(h * h + k * k + l * l);
                float n = 3.0F * ((1.0F + (float) j) / 4.0F);
                h *= n / m;
                k *= n / m;
                l *= n / m;
                playerEntity.addVelocity(h, k, l);
                playerEntity.startRiptideAttack(20);
                if (playerEntity.isOnGround()) {
                    float o = 1.1999999F;
                    playerEntity.move(MovementType.SELF, new Vec3d(0.0D, 1.1999999284744263D, 0.0D));
                }

                SoundEvent soundEvent3;
                if (j >= 3) {
                    soundEvent3 = SoundEvents.ITEM_TRIDENT_RIPTIDE_3;
                } else if (j == 2) {
                    soundEvent3 = SoundEvents.ITEM_TRIDENT_RIPTIDE_2;
                } else {
                    soundEvent3 = SoundEvents.ITEM_TRIDENT_RIPTIDE_1;
                }

                world.playSoundFromEntity(null, playerEntity, soundEvent3, SoundCategory.PLAYERS, 1.0F, 1.0F);
            }
        }
    }

    @Redirect(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isTouchingWaterOrRain()Z"))
    public boolean isTouchingWaterOrRain$UseMobOrigins(PlayerEntity playerEntity) {
        List<RiptideOverridePower> riptideOverridePowers = PowerHolderComponent.getPowers(playerEntity, RiptideOverridePower.class);
        if (riptideOverridePowers.size() > 0) {
            boolean active = riptideOverridePowers.stream().anyMatch((Power::isActive));
            if (active) {
                return true;
            }
        }

        return playerEntity.isTouchingWaterOrRain();
    }
}
