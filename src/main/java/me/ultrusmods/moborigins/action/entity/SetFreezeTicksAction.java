package me.ultrusmods.moborigins.action.entity;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import me.ultrusmods.moborigins.MobOriginsMod;
import net.minecraft.entity.Entity;

/** {DOCS}
    NAME: Set Freeze Ticks
    DESC: Sets the freeze ticks of an entity.
    PARAMS:
        - {ticks} {Integer} {https://origins.readthedocs.io/en/latest/types/data_types/integer/} { }  {The amount of ticks to set the freeze ticks to.}
    EXAMPLE:
{
    "type": "moborigins:set_freeze_ticks",
    "ticks": 20
}
    POWER_DESC: Sets the freeze ticks of an entity to 20 ticks.
 */
public class SetFreezeTicksAction {
    public static ActionFactory<Entity> createFactory() {
        return new ActionFactory<>(MobOriginsMod.id("set_freeze_ticks"), new SerializableData()
                .add("ticks", SerializableDataTypes.INT, 20),
                ((instance, entity) -> {
                    entity.setFrozenTicks(instance.getInt("ticks"));
                }));
    }
}
