package me.ultrusmods.moborigins.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import me.ultrusmods.moborigins.MobOriginsMod;
import net.minecraft.entity.LivingEntity;

/** {DOCS}
    NAME: Fog
    DESC: Changes the fog color of the entity.
    PARAMS:
        - {red} {Float} {https://origins.readthedocs.io/en/latest/types/data_types/float/} { }  {The red value of the fog color.}
        - {green} {Float} {https://origins.readthedocs.io/en/latest/types/data_types/float/} { }  {The green value of the fog color.}
        - {blue} {Float} {https://origins.readthedocs.io/en/latest/types/data_types/float/} { }  {The blue value of the fog color.}
        - {start} {Integer} {https://origins.readthedocs.io/en/latest/types/data_types/integer/} { }  {The start distance of the fog.}
        - {end} {Integer} {https://origins.readthedocs.io/en/latest/types/data_types/integer/} { }  {The end distance of the fog.}
    EXAMPLE:
{
  "type": "moborigins:fog",
  "red": 1,
  "green": 1,
  "blue": 1,
  "start": -1,
  "end": 30,
  "condition": {
    "type": "apoli:sneaking",
    "inverted": true
  }
}
POWER_DESC: This makes it so the player has a thick white fog, which can be disabled by sneaking.
This looks like:
![Fog Image](../images/fog.png)
 */
public class FogPower extends Power {
    private final float red;
    private final float green;
    private final float blue;
    private final int start;
    private final int end;

    public FogPower(PowerType<?> type, LivingEntity entity, float red, float green, float blue, int start, int end) {
        super(type, entity);
        this.red = red;
        this.green = green;
        this.blue = blue;
        this.start = start;
        this.end = end;
    }

    public static PowerFactory createFactory() {
        return new PowerFactory<>(MobOriginsMod.id("fog"),
                new SerializableData()
                        .add("red", SerializableDataTypes.FLOAT, 1F)
                        .add("green", SerializableDataTypes.FLOAT, 1F)
                        .add("blue", SerializableDataTypes.FLOAT, 1F)
                        .add("start", SerializableDataTypes.INT, -5)
                        .add("end", SerializableDataTypes.INT, 30),
                data ->
                        (type, player) ->
                                new FogPower(type, player, data.getFloat("red"), data.getFloat("green"), data.getFloat("blue"), data.getInt("start"), data.getInt("end")))
                .allowCondition();
    }

    public float getRed() {
        return red;
    }

    public float getGreen() {
        return green;
    }

    public float getBlue() {
        return blue;
    }

    public int getStart() {
        return start;
    }

    public int getEnd() {
        return end;
    }
}
