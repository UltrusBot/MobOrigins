package io.github.ultrusbot.moborigins.client;

import io.github.ultrusbot.moborigins.client.entity.render.OriginSlimeRenderer;
import io.github.ultrusbot.moborigins.entity.EntityRegistry;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.rendereregistry.v1.EntityRendererRegistry;

@Environment(EnvType.CLIENT)
public class MobOriginsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.INSTANCE.register(EntityRegistry.ORIGIN_SLIME, (dispatcher, context) -> new OriginSlimeRenderer(dispatcher));
    }
}
