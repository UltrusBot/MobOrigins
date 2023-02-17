package me.ultrusmods.moborigins.power;

import io.github.apace100.apoli.power.ModelColorPower;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import me.ultrusmods.moborigins.MobOriginsMod;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.color.world.BiomeColors;
import net.minecraft.entity.LivingEntity;

/** {DOCS}
    NAME: Biome Model Color
    DESC: Changes the entity's model color to the biomes grass color.
    PARAMS:
        - {alpha} {float} {https://origins.readthedocs.io/en/latest/types/data_types/float/} {1.0}  {The alpha value of the color.}
    EXAMPLE:
    {
      "type": "moborigins:biome_model_color",
      "alpha": 0.5
    }
    POWER_DESC: Changes the entity's model color to the biomes grass color with an alpha value of 0.5.
 */
public class BiomeModelColorPower extends ModelColorPower {
    public BiomeModelColorPower(PowerType<?> type, LivingEntity entity, float alpha) {
        super(type, entity, 1f, 1f, 1f, alpha);
    }

    @Override
    public float getRed() {
           if (entity.world.isClient()) {
               return (float)(MinecraftClient.getInstance().world.getColor(entity.getBlockPos(), BiomeColors.GRASS_COLOR) >> 16 & 0xFF)/255F;
           } else {
                return super.getRed();
           }
    }

    @Override
    public float getGreen() {
        if (entity.world.isClient()) {
            return (float)(MinecraftClient.getInstance().world.getColor(entity.getBlockPos(), BiomeColors.GRASS_COLOR) >> 8 & 0xFF)/255F;
        } else {
            return super.getGreen();
        }
    }

    @Override
    public float getBlue() {
        if (entity.world.isClient()) {
            return (float)((MinecraftClient.getInstance().world.getColor(entity.getBlockPos(), BiomeColors.GRASS_COLOR)) & 0xFF)/255F;
        } else {
            return super.getBlue();
        }
    }

    public static PowerFactory createFactory() {
        return new PowerFactory<>(MobOriginsMod.id("biome_model_color"),
                new SerializableData()
                        .add("alpha", SerializableDataTypes.FLOAT, 1.0F),
                data ->
                        (type, player) ->
                                new BiomeModelColorPower(type, player, data.getFloat("alpha")))
                .allowCondition();
    }
}
