package io.github.ultrusbot.moborigins;

import io.github.apace100.apoli.power.factory.action.ActionFactory;
import io.github.apace100.apoli.registry.ApoliRegistries;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import io.github.ultrusbot.moborigins.entity.slime.OriginSlimeEntity;
import io.github.ultrusbot.moborigins.origins.EvokerSummonFangs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class CustomEntityActions {
    private static final String MOD_ID = MobOriginsMod.MOD_ID;

    public static void register() {

        register(new ActionFactory<>(new Identifier(MOD_ID, "summon_fangs"), new SerializableData(),
                (data, entity) -> {
                    EvokerSummonFangs.summonFangs(entity);
                }));

        register(new ActionFactory<>(new Identifier(MOD_ID, "summon_slime"), new SerializableData(),
                (data, entity) -> {
                    OriginSlimeEntity originSlimeEntity = new OriginSlimeEntity(entity.world, entity.getX(), entity.getY(), entity.getZ());
                    if (entity instanceof PlayerEntity playerEntity) {
                        originSlimeEntity.setOwner(playerEntity);
                    }
                    originSlimeEntity.setSize(2, true);
                    originSlimeEntity.setCustomName(entity.getDisplayName());
                    entity.world.spawnEntity(originSlimeEntity);

                }));
        register(new ActionFactory<>(new Identifier(MOD_ID, "damage_equipment"), new SerializableData()
                .add("equipment_slot", SerializableDataTypes.EQUIPMENT_SLOT)
                .add("amount", SerializableDataTypes.INT),
                (data, entity) -> {
                    if (entity instanceof LivingEntity) {
                        LivingEntity livingEntity = (LivingEntity)entity;
                        ItemStack itemStack = livingEntity.getEquippedStack((EquipmentSlot)data.get("equipment_slot"));
                        if (!itemStack.isEmpty() && itemStack.isDamageable()) {
                            itemStack.setDamage(itemStack.getDamage() + data.getInt("amount"));
                            if (itemStack.getDamage() >= itemStack.getMaxDamage()) {
                                livingEntity.sendEquipmentBreakStatus((EquipmentSlot) data.get("equipment_slot"));
                                livingEntity.equipStack((EquipmentSlot) data.get("equipment_slot"), ItemStack.EMPTY);
                            }
                        }
                    }
                }));
    }

    private static void register(ActionFactory<Entity> actionFactory) {
        Registry.register(ApoliRegistries.ENTITY_ACTION, actionFactory.getSerializerId(), actionFactory);
    }
}
