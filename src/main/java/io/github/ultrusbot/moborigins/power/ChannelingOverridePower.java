package io.github.ultrusbot.moborigins.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;

import java.util.function.Consumer;

public class ChannelingOverridePower extends Power {
    private final Consumer<Entity> entityAction;
    public ChannelingOverridePower(PowerType<?> type, LivingEntity entity, Consumer<Entity> entityAction ) {
        super(type, entity);
        this.entityAction = entityAction;
    }
    public void executeAction(Entity entity) {
        if (entityAction != null) {
            entityAction.accept(entity);
        }
    }
}
