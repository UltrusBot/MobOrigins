package me.ultrusmods.moborigins.entity;

import me.ultrusmods.moborigins.MobOriginsMod;
import me.ultrusmods.moborigins.entity.slime.OriginSlimeEntity;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricEntityTypeBuilder;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnGroup;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.registry.Registry;

public class MobOriginsEntities {
    public static EntityType<OriginSlimeEntity> ORIGIN_SLIME = Registry.register(
            Registries.ENTITY_TYPE,
            new Identifier(MobOriginsMod.MOD_ID, "origin_slime"),
            FabricEntityTypeBuilder.<OriginSlimeEntity>create(SpawnGroup.CREATURE, OriginSlimeEntity::new)
                    .dimensions(EntityDimensions.fixed(1F, 1F))
                    .trackRangeBlocks(10)
                    .build()
    );
    public static void init() {
        FabricDefaultAttributeRegistry.register(MobOriginsEntities.ORIGIN_SLIME, HostileEntity.createHostileAttributes());
    }
}
