package me.ultrusmods.moborigins.power;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.power.ModelColorPower;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import me.ultrusmods.moborigins.MobOriginsMod;
import net.minecraft.entity.LivingEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;

/** {DOCS}
    NAME: Dyeable Model Color
    DESC: Sets the color of a model, while allowing it to be dyed by the [Set Dyeable Model Color](https://moborigins.readthedocs.io/en/latest/actions/entity/set_dyeable_model_color) action, or the [Consume Dye](https://moborigins.readthedocs.io/en/latest/actions/entity/consume_dye_color/) action.
    PARAMS:
        - {red} {Float} {https://origins.readthedocs.io/en/latest/types/data_types/float/} {1.0}  {The red component of the color.}
        - {green} {Float} {https://origins.readthedocs.io/en/latest/types/data_types/float/} {1.0}  {The green component of the color.}
        - {blue} {Float} {https://origins.readthedocs.io/en/latest/types/data_types/float/} {1.0}  {The blue component of the color.}
        - {alpha} {Float} {https://origins.readthedocs.io/en/latest/types/data_types/float/} {1.0}  {The alpha component of the color.}
    EXAMPLE:
{
    "type": "moborigins:dyeable_model_color",
    "red": 0.0,
    "green": 0.0,
    "blue": 1.0,
    "alpha": 0.9
}
    POWER_DESC: Sets the color of a model to blue, and slightly translucent.
 */
public class DyeableModelColorPower extends ModelColorPower {
    private float red;
    private float green;
    private float blue;

    public DyeableModelColorPower(PowerType<?> type, LivingEntity entity, float red, float green, float blue, float alpha) {
        super(type, entity, red, green, blue, alpha);
        this.red = red;
        this.green = green;
        this.blue = blue;

    }

    @Override
    public float getRed() {
        return red;
    }

    @Override
    public float getGreen() {
        return green;
    }

    @Override
    public float getBlue() {
        return blue;
    }


    @Override
    public NbtElement toTag() {
        NbtCompound nbtCompound = new NbtCompound();
        nbtCompound.putFloat("red", red);
        nbtCompound.putFloat("green", green);
        nbtCompound.putFloat("blue", blue);
        return nbtCompound;

    }

    @Override
    public void fromTag(NbtElement tag) {
        NbtCompound nbtCompound = (NbtCompound) tag;
        red = nbtCompound.getFloat("red");
        green = nbtCompound.getFloat("green");
        blue = nbtCompound.getFloat("blue");
    }


    public void blendColor(float[] color) {
        red = (red + color[0]) / 2.0F;
        green = (green + color[1]) / 2.0F;
        blue = (blue + color[2]) / 2.0F;
        PowerHolderComponent.syncPower(this.entity, this.type);
    }

    public void setColor(float red, float green, float blue) {
        this.red = red;
        this.green = green;
        this.blue = blue;
        PowerHolderComponent.syncPower(this.entity, this.type);
    }

    public static PowerFactory createFactory() {
        return new PowerFactory<>(MobOriginsMod.id("dyeable_model_color"),
                new SerializableData()
                        .add("red", SerializableDataTypes.FLOAT, 1.0F)
                        .add("green", SerializableDataTypes.FLOAT, 1.0F)
                        .add("blue", SerializableDataTypes.FLOAT, 1.0F)
                        .add("alpha", SerializableDataTypes.FLOAT, 1.0F),
                data ->
                        (type, player) ->
                                new DyeableModelColorPower(type, player, data.getFloat("red"), data.getFloat("green"), data.getFloat("blue"), data.getFloat("alpha")))
                .allowCondition();
    }
}
