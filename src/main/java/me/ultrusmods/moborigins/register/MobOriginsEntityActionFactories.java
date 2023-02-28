package me.ultrusmods.moborigins.register;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import me.ultrusmods.moborigins.action.entity.*;
import net.minecraft.entity.Entity;
import net.minecraft.registry.Registry;

public class MobOriginsEntityActionFactories {

    public static void register() {
        register(ConsumeDyeColorAction.createFactory());
//        register(CopyResourceAction.createFactory());
        register(SetFreezeTicksAction.createFactory());
        register(ShowFloatingItemAction.createFactory());
        register(SetDyeableModelColor.createFactory());
        register(ResourceMathAction.createFactory());
        register(JumpAction.createFactory());
        register(SummonFangsAction.createFactory());
        register(DamageEquipmentAction.createFactory());
        register(SummonSlimeAction.createFactory());
        register(SetItemCooldownAction.createFactory());

    }

    private static void register(ActionFactory<Entity> serializer) {
        Registry.register(ApoliRegistries.ENTITY_ACTION, serializer.getSerializerId(), serializer);
    }
}
