package me.ultrusmods.moborigins.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import me.ultrusmods.moborigins.MobOriginsMod;
import net.minecraft.entity.LivingEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;

/** {DOCS}
    NAME: Fall Sounds
    DESC: Changes the entity's fall sounds.
    PARAMS:
        - {distance} {int} {https://origins.readthedocs.io/en/latest/types/data_types/integer/} {4}  {The distance the entity must fall to play the big fall sound.}
        - {small_fall_sound} {sound_event} {https://origins.readthedocs.io/en/latest/types/data_types/sound_event/} {entity.generic.small_fall}  {The sound to play when the entity falls a small distance.}
        - {big_fall_sound} {sound_event} {https://origins.readthedocs.io/en/latest/types/data_types/sound_event/} {entity.generic.big_fall}  {The sound to play when the entity falls a big distance.}
    EXAMPLE:
{
  "type": "moborigins:fall_sounds",
  "big_fall_sound": "minecraft:entity.slime.squish",
  "small_fall_sound": "minecraft:entity.slime.squish_small"
}
    POWER_DESC: Changes the entity's fall sounds to the slimes squish sounds.
 */
public class FallSoundPower extends Power {

    private final int distance;
    private final SoundEvent smallSound;
    private final SoundEvent bigSound;

    public FallSoundPower(PowerType<?> type, LivingEntity entity, int distance, SoundEvent smallSound, SoundEvent bigSound) {
        super(type, entity);
        this.distance = distance;
        this.smallSound = smallSound;
        this.bigSound = bigSound;
    }

    public int getDistance() {
        return distance;
    }

    public SoundEvent getBigSound() {
        return bigSound;
    }

    public SoundEvent getSmallSound() {
        return smallSound;
    }

    public static PowerFactory createFactory() {
        return new PowerFactory<>(MobOriginsMod.id("fall_sounds"),
                new SerializableData()
                        .add("distance", SerializableDataTypes.INT, 4)
                        .add("small_fall_sound", SerializableDataTypes.SOUND_EVENT, SoundEvents.ENTITY_GENERIC_SMALL_FALL)
                        .add("big_fall_sound", SerializableDataTypes.SOUND_EVENT, SoundEvents.ENTITY_GENERIC_BIG_FALL),
                data ->
                        (type, player) -> new FallSoundPower(type, player, data.getInt("distance"), data.get("small_fall_sound"), data.get("big_fall_sound")))
                .allowCondition();
    }
}
