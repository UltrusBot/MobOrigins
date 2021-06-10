package io.github.ultrusbot.moborigins;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.apoli.util.Comparison;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.stat.Stats;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.Registry;

public class CustomPlayerConditions {
    private static final String MOD_ID = MobOriginsMod.MOD_ID;
    public static void register() {
        register(new ConditionFactory<>(new Identifier(MOD_ID, "is_full_moon"), new SerializableData(), (data, player) -> player.world.getMoonSize() == 1.0));

        register(new ConditionFactory<>(new Identifier(MOD_ID, "time_since_rest"), new SerializableData()
                .add("comparison", ApoliDataTypes.COMPARISON)
                .add("compare_to", SerializableDataTypes.INT),
                (data, player) -> ((Comparison)data.get("comparison")).compare(MathHelper.clamp(((ServerPlayerEntity)player).getStatHandler().getStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST)), 1, Integer.MAX_VALUE), data.getInt("compare_to"))));

        register(new ConditionFactory<>(new Identifier(MOD_ID, "nearby_entities"), new SerializableData()
                .add("entity_type", SerializableDataTypes.ENTITY_TYPE)
                .add("player_box_multiplier", SerializableDataTypes.FLOAT)
                .add("comparison", ApoliDataTypes.COMPARISON)
                .add("compare_to", SerializableDataTypes.INT),
                (data, player) -> {
                    EntityType<?> entityType = (EntityType<?>)data.get("entity_type");
                    Float playerBoxMultiplier = (Float)data.get("player_box_multiplier");
                    int amount = player.world.getOtherEntities(player, player.getBoundingBox().expand(playerBoxMultiplier), entity -> {
                        return entity.getType() == entityType;
                    }).size();
                    Comparison comparison = ((Comparison)data.get("comparison"));
                    int compareTo = data.getInt("compare_to");

                    return comparison.compare(amount, compareTo);
                }));


    }


    private static void register(ConditionFactory<LivingEntity> conditionFactory) {
        Registry.register(ApoliRegistries.ENTITY_CONDITION, conditionFactory.getSerializerId(), conditionFactory);
    }
    
}
