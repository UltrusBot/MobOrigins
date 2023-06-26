package me.ultrusmods.moborigins.power;

import io.github.apace100.apoli.power.Power;
import io.github.apace100.apoli.power.PowerType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

/** {DOCS} {IGNORE}
 *
 */
public abstract class BossBarPower extends Power {

    private final ServerBossBar bossBar;
    private final boolean visibleToAll;

    public BossBarPower(PowerType<?> type, LivingEntity entity, BossBar.Color color, BossBar.Style style, @Nullable Text bossTitle, boolean darkenSky, boolean thickenFog, boolean visibleToAll) {
        super(type, entity);
        if (entity != null) {
            Text text = (bossTitle != null) ? bossTitle : entity.getDisplayName();
            bossBar = (ServerBossBar) new ServerBossBar(text, color, style).setDarkenSky(darkenSky).setThickenFog(thickenFog);
        } else {
            bossBar = null;
        }
        this.visibleToAll = visibleToAll;
        setTicking();

    }

    @Override
    public void tick() {
        if (!entity.getWorld().isClient()) {
            if (bossBar.getPercent() != getPercent()) {
                bossBar.setPercent(getPercent());
            }
        }
    }

    public abstract float getPercent();

    public void addPlayer(ServerPlayerEntity player) {
        bossBar.addPlayer(player);
    }


    public void removePlayer(ServerPlayerEntity player) {
        bossBar.removePlayer(player);
    }

    @Override
    public void onRemoved() {
        super.onRemoved();
    }
}
