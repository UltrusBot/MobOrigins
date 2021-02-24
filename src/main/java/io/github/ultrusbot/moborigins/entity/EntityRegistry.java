package io.github.ultrusbot.moborigins.entity;

import io.github.ultrusbot.moborigins.MobOriginsMod;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class EntityRegistry {
    public static EntityType<OriginSlimeEntity> ORIGIN_SLIME;
    public static void register() {
        ORIGIN_SLIME = Registry.register(
                Registry.ENTITY_TYPE,
                new Identifier(MobOriginsMod.MOD_ID, "origin_slime"),
                FabricEntityTypeBuilder.create(SpawnGroup.CREATURE, OriginSlimeEntity::new).dimensions(EntityDimensions.fixed(1F, 1F)).trackRangeBlocks(10).build()
        );
    }
}
