package me.ultrusmods.moborigins.power;

import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.factory.PowerFactory;
import io.github.apace100.calio.data.SerializableData;
import io.github.apace100.calio.data.SerializableDataTypes;
import me.ultrusmods.moborigins.MobOriginsMod;
import me.ultrusmods.moborigins.data.MobOriginsDataTypes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

/** {DOCS} {IGNORE}
    NAME: Health Boss Bar
    DESC: Shows a boss bar with the entity's health.
 Valid colors: pink, blue, red, green, yellow, purple, white.
 Valid styles: progress, notched_6, notched_10, notched_12, notched_20.
    PARAMS:
        - {color} {Boss Bar Color} { } {"white"}  {The color of the boss bar.}
        - {style} {Boss Bar Style} { } {"progress"}  {The style of the boss bar.}
        - {text} {Text} {https://origins.readthedocs.io/en/latest/types/data_types/text_component/} { }  {The text to display on the boss bar.}
        - {darken_sky} {Boolean} {https://origins.readthedocs.io/en/latest/types/data_types/boolean/} {false}  {Whether the sky should darken when the boss bar is visible.}
        - {thicken_fog} {Boolean} {https://origins.readthedocs.io/en/latest/types/data_types/boolean/} {false}  {Whether the fog should thicken when the boss bar is visible.}
        - {visible_to_all} {Boolean} {https://origins.readthedocs.io/en/latest/types/data_types/boolean/} {true}  {Whether the boss bar should be visible to all players.}
    EXAMPLE:
{
    "type": "moborigins:health_boss_bar",
    "color": "blue",
    "style": "notched_10",
    "text": "The Destroyer",
    "darken_sky": true,
    "thicken_fog": true,
}
 POWER_DESC: Shows a boss bar, where the progress is the entity's health, styled blue, with 10 notches, and the text "The Destroyer". The sky darkens and the fog thickens when the boss bar is visible for all players in a distance.
 */
public class HealthBossBarPower extends BossBarPower {
    public HealthBossBarPower(PowerType<?> type, LivingEntity entity, BossBar.Color color, BossBar.Style style, @Nullable Text bossTitle, boolean darkenSky, boolean thickenFog, boolean visibleToAll) {
        super(type, entity, color, style, bossTitle, darkenSky, thickenFog, visibleToAll);

    }

    @Override
    public float getPercent() {
        return entity.getHealth() / entity.getMaxHealth();
    }

    public static PowerFactory createFactory() {
        return new PowerFactory<>(MobOriginsMod.id("health_boss_bar"),
                new SerializableData()
                        .add("color", MobOriginsDataTypes.BOSS_BAR_COLORS, BossBar.Color.WHITE)
                        .add("style", MobOriginsDataTypes.BOSS_BAR_STYLES, BossBar.Style.PROGRESS)
                        .add("text", SerializableDataTypes.TEXT, null)
                        .add("darken_sky", SerializableDataTypes.BOOLEAN, false)
                        .add("thicken_fog", SerializableDataTypes.BOOLEAN, false)
                        .add("visible_to_all", SerializableDataTypes.BOOLEAN, true),

        data ->
                        (type, livingEntity) -> new HealthBossBarPower(type, livingEntity, data.get("color"), data.get("style"), data.get("text"), data.getBoolean("darken_sky"), data.getBoolean("thicken_fog"), data.getBoolean("visible_to_all")))
                .allowCondition();
    }
}
