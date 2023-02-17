package me.ultrusmods.moborigins;

import com.mojang.logging.LogUtils;
import me.ultrusmods.moborigins.condition.block.MobOriginsBlockConditions;
import me.ultrusmods.moborigins.condition.damage.MobOriginsDamageConditions;
import me.ultrusmods.moborigins.condition.item.MobOriginsItemConditions;
import me.ultrusmods.moborigins.enchantment.MobOriginsEnchantments;
import me.ultrusmods.moborigins.entity.MobOriginsEntities;
import me.ultrusmods.moborigins.event.SleepEvents;
import me.ultrusmods.moborigins.register.*;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;

public class MobOriginsMod implements ModInitializer {
    public static final String MOD_ID = "moborigins";
    private static final Logger LOGGER = LogUtils.getLogger();

    public static Identifier id(String path) {
        return new Identifier(MOD_ID, path);
    }

    @Override
    public void onInitialize() {
        MobOriginsPowerFactories.register();
        MobOriginsEntityActionFactories.register();
        MobOriginsItemConditions.register();
        MobOriginsBlockConditions.register();
        MobOriginsBiEntityActionFactories.register();
        MobOriginsEntityConditionFactories.register();
        MobOriginsBlockActions.register();
        MobOriginsDamageConditions.register();
        SleepEvents.init();
        MobOriginsEntities.init();
        MobOriginsEnchantments.init();
        FabricLoader.getInstance().getModContainer(MOD_ID).ifPresent(modContainer -> {
            var version = modContainer.getMetadata().getVersion().getFriendlyString();
            LOGGER.info("Mob Origins version " + version + " is loaded!");
        });
    }
}
