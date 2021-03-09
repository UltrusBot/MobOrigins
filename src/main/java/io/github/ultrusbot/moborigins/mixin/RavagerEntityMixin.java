package io.github.ultrusbot.moborigins.mixin;

import io.github.ultrusbot.moborigins.power.MobOriginsPowers;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.RavagerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.List;

@Mixin(RavagerEntity.class)
public class RavagerEntityMixin {

//    @Inject(method = "roar()V", at = @At(value = "INVOKE", target = "Ljava/util/Iterator;next()Ljava/lang/Object;"), locals = LocalCapture.PRINT)
    @ModifyVariable(method = "roar()V", at = @At("STORE"), ordinal = 0)
    private List<Entity> roar$MobOrigins(List<Entity> list) {
        list.removeIf(MobOriginsPowers.PILLAGER_ALIGNED::isActive);
        return list;
    }
}
