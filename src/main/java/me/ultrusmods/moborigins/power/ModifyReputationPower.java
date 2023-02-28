package me.ultrusmods.moborigins.power;

import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.ValueModifyingPower;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.apoli.util.modifier.Modifier;
import io.github.apace100.calio.data.SerializableData;
import me.ultrusmods.moborigins.MobOriginsMod;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Pair;

import java.util.List;
import java.util.function.Predicate;

/** {DOCS}
    NAME: Modify Villager Reputation

    DESC: This power type allows you to effectively modify the reputation of a villager when you interact with it.
You can learn more about reputation [here](https://minecraft.fandom.com/wiki/Villager#Gossiping).
!!! tip "The 'actor' is the villager, while the 'target' is the entity with the power, that is interacting with the villager."

    PARAMS:
    - {bientity_condition} {Bi-entity Condition Type} {https://origins.readthedocs.io/en/latest/types/bientity_condition_types/} {optional} {This is a bi-entity condtion with the actor, the villager, and the target, the player.}
    - {modifier} {Attribute Modifier} {https://origins.readthedocs.io/en/latest/types/data_types/attribute_modifier/} {optional} {This is a modifier that will be applied to your reputation with the villager.}
    - {modifiers} {List of Modifier Types} {https://origins.readthedocs.io/en/latest/types/data_types/attribute_modifier/} {optional} {This is a list of modifiers that will be applied to your reputation with the villager.}

    EXAMPLE:
{
  "type": "moborigins:modify_villager_reputation",
  "modifier": {
    "operation": "addition",
    "value": 100.0
  }
}
    POWER_DESC: This power massively increases your reputation with villagers, allowing you to trade with them for much cheaper.
 */

public class ModifyReputationPower extends ValueModifyingPower {
    private final Predicate<Pair<Entity, Entity>> biEntityCondition;

    public ModifyReputationPower(PowerType<?> type, LivingEntity entity, Predicate<Pair<Entity, Entity>> biEntityCondition) {
        super(type, entity);
        this.biEntityCondition = biEntityCondition;
    }

    public boolean doesApply(Entity other) {
        return biEntityCondition == null || biEntityCondition.test(new Pair(other, entity));
    }

    public static PowerFactory<Power> createFactory() {
        return new PowerFactory<>(MobOriginsMod.id("modify_villager_reputation"),
                new SerializableData()
                        .add("bientity_condition", ApoliDataTypes.BIENTITY_CONDITION, null)
                        .add("modifier", Modifier.DATA_TYPE, null)
                        .add("modifiers", Modifier.LIST_TYPE, null),
                data ->
                        (type, livingEntity) -> {
                            ModifyReputationPower power = new ModifyReputationPower(type, livingEntity, data.get("bientity_condition"));
                            data.ifPresent("modifier", power::addModifier);
                            data.<List<Modifier>>ifPresent("modifiers", mods ->
                                    mods.forEach(power::addModifier));
                            return power;
                        })
                .allowCondition();
    }
}