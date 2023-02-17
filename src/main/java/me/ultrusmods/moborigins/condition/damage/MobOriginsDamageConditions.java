package me.ultrusmods.moborigins.condition.damage;

import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import me.ultrusmods.moborigins.MobOriginsMod;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.util.Pair;
import net.minecraft.util.registry.Registry;

public class MobOriginsDamageConditions {
    public static void register() {
        /** {DOCS}
            NAME: Is Magic
            DESC: Checks if the damage source is magic.
            PARAMS:
            EXAMPLE:
{
    "damage_condition": {
        "type": "moborigins:is_magic"
    }
}
            POWER_DESC: Checks if the damage source is magic.
         */
        register(new ConditionFactory<>(MobOriginsMod.id("is_magic"), new SerializableData(),
                (data, pair) -> pair.getLeft().isMagic()));

    }

    private static void register(ConditionFactory<Pair<DamageSource, Float>> conditionFactory) {
        Registry.register(ApoliRegistries.DAMAGE_CONDITION, conditionFactory.getSerializerId(), conditionFactory);
    }
}
