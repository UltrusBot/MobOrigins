package me.ultrusmods.moborigins.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import me.ultrusmods.moborigins.MobOriginsMod;
import net.minecraft.entity.LivingEntity;

/** {DOCS}
    NAME: Walk on Powder Snow

    DESC: This power type allows you to walk on powder snow.

    PARAMS:

    EXAMPLE:
{
    "type": "moborigins:walk_on_powder_snow"
}

    POWER_DESC: This lets you walk on powder snow.
 */
public class PowderSnowPower extends Power {
    public PowderSnowPower(PowerType<?> type, LivingEntity entity) {
        super(type, entity);
    }

    public static PowerFactory createFactory() {
        return new PowerFactory<>(MobOriginsMod.id("walk_on_powder_snow"),
                new SerializableData(),
                data ->
                        PowderSnowPower::new)
                .allowCondition();
    }
}