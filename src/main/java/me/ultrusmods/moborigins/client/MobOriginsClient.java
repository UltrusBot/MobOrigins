package me.ultrusmods.moborigins.client;

import me.ultrusmods.moborigins.MobOriginsMod;
import me.ultrusmods.moborigins.client.entity.render.OriginSlimeRenderer;
import me.ultrusmods.moborigins.entity.MobOriginsEntities;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.client.rendering.v1.EntityRendererRegistry;
import net.minecraft.item.ItemStack;

@Environment(EnvType.CLIENT)
public class MobOriginsClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        EntityRendererRegistry.register(MobOriginsEntities.ORIGIN_SLIME, OriginSlimeRenderer::new);
        ClientPlayNetworking.registerGlobalReceiver(MobOriginsMod.id("floating_item"), (client, handler, buf, responseSender) -> {
            ItemStack stack = buf.readItemStack();
            client.execute(() -> {
                client.gameRenderer.showFloatingItem(stack);
            });
        });
    }
}
