package me.ultrusmods.moborigins.data;

import io.github.apace100.calio.data.SerializableDataType;
import net.minecraft.entity.boss.BossBar;


public class MobOriginsDataTypes {
    public static final SerializableDataType<BossBar.Color> BOSS_BAR_COLORS = SerializableDataType.enumValue(BossBar.Color.class);
    public static final SerializableDataType<BossBar.Style> BOSS_BAR_STYLES = SerializableDataType.enumValue(BossBar.Style.class);
    public static final SerializableDataType<MathOperation> MATH_OPERATOR = SerializableDataType.enumValue(MathOperation.class);

}
