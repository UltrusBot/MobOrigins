package io.github.ultrusbot.moborigins.mixin;

import io.github.ultrusbot.moborigins.power.MobOriginsPowers;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;

@Mixin(BeehiveBlock.class)
public class BeehiveBlockMixin {

    @ModifyVariable(method = "angerNearbyBees", at = @At("STORE"), index = 4)
    public List<PlayerEntity> angerNearbyBees$MobOrigins(List<PlayerEntity> list) {
        list.removeIf(MobOriginsPowers.QUEEN_BEE::isActive);
        return list;
    }
}
