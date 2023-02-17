package me.ultrusmods.moborigins.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import me.ultrusmods.moborigins.MobOriginsMod;
import net.minecraft.entity.LivingEntity;

/** {DOCS}
    NAME: Bouncy
    DESC: Makes the entity bounce off the ground when it lands.
    PARAMS:
        - {multiplier} {Float} {https://origins.readthedocs.io/en/latest/types/data_types/float/} {optional} {The multiplier to apply to the entity's velocity when it lands.}

 EXAMPLE:
{
    "type": "moborigins:bouncy",
    "multiplier": -1.00
}
 POWER_DESC: This makes the entity bounce off the ground when it lands, without losing any velocity.
 */
public class BouncePower extends Power {
    private final double velocity;

    public BouncePower(PowerType<?> type, LivingEntity entity, double velocity) {
        super(type, entity);
        this.velocity = velocity;
    }

    public double getVelocity() {
        return velocity;
    }

    public static PowerFactory createFactory() {
        return new PowerFactory<>(MobOriginsMod.id("bouncy"),
                new SerializableData()
                        .add("multiplier", SerializableDataTypes.DOUBLE, -0.85),
                data ->
                        (type, livingEntity) -> new BouncePower(type, livingEntity, data.getDouble("multiplier")))
                .allowCondition();
    }
}
