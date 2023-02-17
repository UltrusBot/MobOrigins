
package me.ultrusmods.moborigins.power;

import io.github.apace100.apoli.component.PowerHolderComponent;
import io.github.apace100.apoli.data.ApoliDataTypes;
import io.github.apace100.apoli.power.CooldownPower;
import io.github.apace100.apoli.power.PowerType;
import io.github.apace100.apoli.power.VariableIntPower;
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
    NAME: Resource Boss Bar
    DESC: Shows a boss bar with the progress of a resource power.
Valid colors: pink, blue, red, green, yellow, purple, white.
Valid styles: progress, notched_6, notched_10, notched_12, notched_20.
    PARAMS:
        - {color} {Boss Bar Color} { } {"white"}  {The color of the boss bar.}
        - {style} {Boss Bar Style} { } {"progress"}  {The style of the boss bar.}
        - {text} {Text} {https://origins.readthedocs.io/en/latest/types/data_types/text_component/} { }  {The text to display on the boss bar.}
        - {darken_sky} {Boolean} {https://origins.readthedocs.io/en/latest/types/data_types/boolean/} {false}  {Whether the sky should darken when the boss bar is visible.}
        - {thicken_fog} {Boolean} {https://origins.readthedocs.io/en/latest/types/data_types/boolean/} {false}  {Whether the fog should thicken when the boss bar is visible.}
        - {visible_to_all} {Boolean} {https://origins.readthedocs.io/en/latest/types/data_types/boolean/} {true}  {Whether the boss bar should be visible to all players.}
        - {resource} {PowerType} {https://origins.readthedocs.io/en/latest/types/data_types/identifier/} { }  {The id of the resource power to use for the boss bar.}
    EXAMPLE:
{
  "type": "origins:multiple",
  "resource": {
    "type": "origins:resource",
    "min": 0,
    "max": 20
  },
  "bar": {
  "type": "moborigins:resource_boss_bar",
  "color": "white",
  "style": "notched_20",
  "text": "Power Meter",
  "darken_sky": false,
  "thicken_fog": false,
  "visible_to_all": true,
  "resource": "*:resource"
  }
}
    POWER_DESC: Shows a boss bar with the progress of a resource power.
 */
public class ResourceBossBarPower extends BossBarPower {
    private final PowerType<?> resource;

    public ResourceBossBarPower(PowerType<?> type, LivingEntity entity, BossBar.Color color, BossBar.Style style, @Nullable Text bossTitle, boolean darkenSky, boolean thickenFog, boolean visibleToAll, PowerType<?> resource) {
        super(type, entity, color, style, bossTitle, darkenSky, thickenFog, visibleToAll);
        this.resource = resource;
    }

    @Override
    public float getPercent() {
        var power = PowerHolderComponent.KEY.get(this.entity).getPower(resource);
        if (power instanceof VariableIntPower variableIntPower) {
            return variableIntPower.getValue() / (float) variableIntPower.getMax();
        }
        if (power instanceof CooldownPower cooldownPower) {
            return cooldownPower.getProgress();
        }
        return 0;
    }

    public static PowerFactory createFactory() {
        return new PowerFactory<>(MobOriginsMod.id("resource_boss_bar"),
                new SerializableData()
                        .add("color", MobOriginsDataTypes.BOSS_BAR_COLORS, BossBar.Color.WHITE)
                        .add("style", MobOriginsDataTypes.BOSS_BAR_STYLES, BossBar.Style.PROGRESS)
                        .add("text", SerializableDataTypes.TEXT, null)
                        .add("darken_sky", SerializableDataTypes.BOOLEAN, false)
                        .add("thicken_fog", SerializableDataTypes.BOOLEAN, false)
                        .add("visible_to_all", SerializableDataTypes.BOOLEAN, true)
                        .add("resource", ApoliDataTypes.POWER_TYPE),
                data ->
                        (type, livingEntity) -> new ResourceBossBarPower(type, livingEntity, data.get("color"), data.get("style"), data.get("text"), data.getBoolean("darken_sky"), data.getBoolean("thicken_fog"), data.getBoolean("visible_to_all"), data.get("resource")))
                .allowCondition();
    }
}
