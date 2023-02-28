package me.ultrusmods.moborigins.register;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import me.ultrusmods.moborigins.action.bientity.SetAngeredAtBiEntityAction;
import net.minecraft.entity.Entity;
import net.minecraft.util.Pair;
import net.minecraft.registry.Registry;

public class MobOriginsBiEntityActionFactories {
    public static void register() {
        register(SetAngeredAtBiEntityAction.createFactory());
    }

    private static void register(ActionFactory<Pair<Entity, Entity>> serializer) {
        Registry.register(ApoliRegistries.BIENTITY_ACTION, serializer.getSerializerId(), serializer);
    }
}
