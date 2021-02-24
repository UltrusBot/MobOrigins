package io.github.ultrusbot.moborigins.power;

import io.github.apace100.origins.Origins;
import io.github.apace100.origins.power.ModelColorPower;
import io.github.apace100.origins.power.factory.PowerFactory;
import io.github.apace100.origins.registry.ModRegistries;
import io.github.apace100.origins.util.SerializableData;
import io.github.apace100.origins.util.SerializableDataType;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class MobOriginPowerFactories {
    public static void register() {
        register(new PowerFactory<>(new Identifier("moborigins", "totem_chance"),
                new SerializableData()
                        .add("chance", SerializableDataType.FLOAT, 0.1F),
                data ->
                        (type, player) ->
                                new TotemChancePower(type, player, data.getFloat("chance")))
                .allowCondition());

        register(new PowerFactory<>(new Identifier("moborigins", "spiked"),
                new SerializableData()
                        .add("spike_damage", SerializableDataType.INT, 2),
                data ->
                        (type, player) ->
                                new SpikedPower(type, player, data.getInt("spike_damage")))
                .allowCondition());

    }


    private static void register(PowerFactory serializer) {
        Registry.register(ModRegistries.POWER_FACTORY, serializer.getSerializerId(), serializer);
    }
}
