package io.github.ultrusbot.moborigins;

import io.github.apace100.origins.Origins;
import io.github.apace100.origins.power.factory.condition.ConditionFactory;
import io.github.apace100.origins.registry.ModRegistries;
import io.github.apace100.origins.util.Comparison;
import io.github.apace100.origins.util.SerializableData;
import io.github.apace100.origins.util.SerializableDataType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CustomPlayerConditions {
    private static final String MOD_ID = MobOriginsMod.MOD_ID;
    public static void register() {
        register(new ConditionFactory<>(new Identifier(MOD_ID, "biome_temperature"), new SerializableData()
                .add("comparison", SerializableDataType.COMPARISON)
                .add("compare_to", SerializableDataType.FLOAT),
                (data, player) -> ((Comparison)data.get("comparison")).compare(player.world.getBiome(player.getBlockPos()).getTemperature(), data.getFloat("compare_to"))));
    }


    private static void register(ConditionFactory<PlayerEntity> conditionFactory) {
        Registry.register(ModRegistries.PLAYER_CONDITION, conditionFactory.getSerializerId(), conditionFactory);
    }
    
}
