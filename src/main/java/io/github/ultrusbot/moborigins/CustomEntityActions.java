package io.github.ultrusbot.moborigins;

import io.github.apace100.origins.power.factory.action.ActionFactory;
import io.github.apace100.origins.registry.ModRegistries;
import io.github.apace100.origins.util.SerializableData;
import io.github.ultrusbot.moborigins.entity.slime.OriginSlimeEntity;
import io.github.ultrusbot.moborigins.origins.EvokerSummonFangs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CustomEntityActions {
    private static final String MOD_ID = MobOriginsMod.MOD_ID;

    public static void register() {

        register(new ActionFactory<>(new Identifier(MOD_ID, "summon_fangs"), new SerializableData(),
                (data, entity) -> {
                    EvokerSummonFangs.summonFangs(entity);
                }));

        register(new ActionFactory<>(new Identifier(MOD_ID, "summon_slime"), new SerializableData(),
                (data, entity) -> {
                    OriginSlimeEntity originSlimeEntity = new OriginSlimeEntity(entity.world, entity.getX(), entity.getY(), entity.getZ());
                    originSlimeEntity.setOwner((PlayerEntity)entity);
                    originSlimeEntity.setSize(2, true);
                    originSlimeEntity.setCustomName(((PlayerEntity)entity).getDisplayName());
                    entity.world.spawnEntity(originSlimeEntity);

                }));
    }

    private static void register(ActionFactory<Entity> actionFactory) {
        Registry.register(ModRegistries.ENTITY_ACTION, actionFactory.getSerializerId(), actionFactory);
    }
}
