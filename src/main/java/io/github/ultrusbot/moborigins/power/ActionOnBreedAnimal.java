package io.github.ultrusbot.moborigins.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.Pair;

import java.util.function.Consumer;
import java.util.function.Predicate;

public class ActionOnBreedAnimal extends Power {
    private final Consumer<Pair<Entity, Entity>> biEntityAction;
    private final Predicate<Pair<Entity, Entity>> bientityCondition;

    public ActionOnBreedAnimal(PowerType<?> type, LivingEntity entity, Consumer<Pair<Entity, Entity>> biEntityAction, Predicate<Pair<Entity, Entity>> bientityCondition) {
        super(type, entity);
        this.biEntityAction = biEntityAction;
        this.bientityCondition = bientityCondition;
    }
    public boolean shouldExecute(Entity tamed) {
        return bientityCondition == null || bientityCondition.test(new Pair<>(entity, tamed));
    }
    public void executeAction(Entity tamed) {
        if(biEntityAction != null) {
            biEntityAction.accept(new Pair<>(entity, tamed));
        }
    }
}
