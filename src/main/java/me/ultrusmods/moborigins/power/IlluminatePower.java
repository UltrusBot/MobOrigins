package me.ultrusmods.moborigins.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import me.ultrusmods.moborigins.MobOriginsMod;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.MathHelper;

/** {DOCS}
    NAME: Illuminate

    DESC: This power type allows you to make a mob emit light like a glow squid.

    PARAMS:
    - {light} {Integer} {https://origins.readthedocs.io/en/latest/types/data_types/integer/} {15} {The light level the mob will emit.}

    EXAMPLE:
{
    "type": "moborigins:illuminate",
    "light": 15,
    "condition": {
        "type": "origins:exposed_to_sun",
        "inverted": true
    }
}

    POWER_DESC: This power makes it so when you are not exposed to the sun (eg: in a cave, or at night) you glow just like a glow squid.
 */
public class IlluminatePower extends Power {
    private final int light;

    public IlluminatePower(PowerType<?> type, LivingEntity entity, int light) {
        super(type, entity);
        this.light = MathHelper.clamp(light, 0, 15);
    }

    public int getLight() {
        return light;
    }

    public static PowerFactory createFactory() {
        return new PowerFactory<>(MobOriginsMod.id("illuminate"),
                new SerializableData()
                        .add("light", SerializableDataTypes.INT, 15),
                data ->
                        (type, livingEntity) -> new IlluminatePower(type, livingEntity, data.getInt("light")))
                .allowCondition();
    }
}
