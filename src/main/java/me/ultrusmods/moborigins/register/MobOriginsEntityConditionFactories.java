package me.ultrusmods.moborigins.register;

import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import me.ultrusmods.moborigins.condition.entity.*;
import net.minecraft.entity.Entity;
import net.minecraft.util.registry.Registry;

public class MobOriginsEntityConditionFactories {

    public static void register() {
        register(NearbyEntitiesCondition.createFactory());
        register(InRaidAreaCondition.createFactory());
        register(TimeSinceRestCondition.createFactory()); //TODO: Make this into a general stat condition
        register(IsFullMoonCondition.createFactory());
        register(HasItemCooldown.createFactory());
        register(CameraCondition.createFactory());


    }

    private static void register(ConditionFactory<Entity> serializer) {
        Registry.register(ApoliRegistries.ENTITY_CONDITION, serializer.getSerializerId(), serializer);
    }
}
