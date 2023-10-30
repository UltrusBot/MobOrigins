package me.ultrusmods.moborigins.condition.item;

import io.github.apace100.apoli.power.factory.condition.ConditionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import me.ultrusmods.moborigins.MobOriginsMod;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registry;
import net.minecraft.util.Pair;
import net.minecraft.world.World;

public class MobOriginsItemConditions {
    public static void register() {
        /** {DOCS}
            NAME: Is Dye
            DESC: Checks if the item is a dye.
            PARAMS:
            EXAMPLE:
{
    "item_condition": {
        "type": "moborigins:is_dye"
    }
}
            POWER_DESC: Checks if the item is a dye.
         */
        register(new ConditionFactory<>(MobOriginsMod.id("is_dye"), new SerializableData(),
                (data, worldItemStackPair) -> worldItemStackPair.getRight().getItem() instanceof DyeItem));

    }

    private static void register(ConditionFactory<Pair<World, ItemStack>> conditionFactory) {
        Registry.register(ApoliRegistries.ITEM_CONDITION, conditionFactory.getSerializerId(), conditionFactory);
    }
}
