package io.github.ultrusbot.moborigins.power;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MobOriginPowerFactories {
    public static void register() {
        register(new PowerFactory<>(new Identifier("moborigins", "totem_chance"),
                new SerializableData()
                        .add("chance", SerializableDataTypes.FLOAT, 0.1F),
                data ->
                        (type, livingEntity) -> new TotemChancePower(type, livingEntity, data.getFloat("chance")))
                .allowCondition());

        register(new PowerFactory<>(new Identifier("moborigins", "spiked"),
                new SerializableData()
                        .add("spike_damage", SerializableDataTypes.INT, 2),
                data ->
                        (type, player) ->
                                new SpikedPower(type, (PlayerEntity) player, data.getInt("spike_damage")))
                .allowCondition());
        register(new PowerFactory<>(new Identifier("moborigins", "riptide_override"),
                new SerializableData()
                        .add("trident_damage", SerializableDataTypes.INT, 1),
                data ->
                        (type, player) ->
                                new RiptideOverridePower(type, (PlayerEntity) player, data.getInt("trident_damage")))
        .allowCondition());

        register(new PowerFactory<>(new Identifier("moborigins", "prevent_villager_interact"),
                new SerializableData(),
                data ->
                        (type, player) ->
                                new PreventVillagerInteractPower(type, player))
                .allowCondition());

        register(new PowerFactory<>(new Identifier("moborigins", "remove_mob_hostility"),
                new SerializableData()
                        .add("entity_condition", ApoliDataTypes.ENTITY_CONDITION, null),
                data ->
                        (type, player) -> new RemoveMobHostilityPower(type, player, (ConditionFactory<LivingEntity>.Instance)data.get("entity_condition")))
                .allowCondition());

    }


    private static void register(PowerFactory serializer) {
        Registry.register(ApoliRegistries.POWER_FACTORY, serializer.getSerializerId(), serializer);
    }
}
