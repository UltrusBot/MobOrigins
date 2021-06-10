package io.github.ultrusbot.moborigins.client.entity.render;

import io.github.ultrusbot.moborigins.client.entity.model.OriginSlimeModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

@Environment(EnvType.CLIENT)
public class OriginSlimeOverlayFeatureRenderer<T extends LivingEntity> extends FeatureRenderer<T, OriginSlimeModel<T>> {
    private final EntityModel<T> model;

    public OriginSlimeOverlayFeatureRenderer(FeatureRendererContext<T, OriginSlimeModel<T>> context, EntityModelLoader loader) {
        super(context);
        this.model = new OriginSlimeModel<>(loader.getModelPart(EntityModelLayers.SLIME_OUTER));
    }

    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        boolean bl = minecraftClient.hasOutline(livingEntity) && livingEntity.isInvisible();
        if (!livingEntity.isInvisible() || bl) {
            VertexConsumer vertexConsumer2;
            if (bl) {
                vertexConsumer2 = vertexConsumerProvider.getBuffer(RenderLayer.getOutline(this.getTexture(livingEntity)));
            } else {
                vertexConsumer2 = vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucent(this.getTexture(livingEntity)));
            }

            this.getContextModel().copyStateTo(this.model);
            this.model.animateModel(livingEntity, f, g, h);
            this.model.setAngles(livingEntity, f, g, j, k, l);
            this.model.render(matrixStack, vertexConsumer2, i, LivingEntityRenderer.getOverlay(livingEntity, 0.0F), 1.0F, 1.0F, 1.0F, 1.0F);
        }
    }
}
