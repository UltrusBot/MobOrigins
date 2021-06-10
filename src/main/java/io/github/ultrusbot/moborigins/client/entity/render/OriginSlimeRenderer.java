package io.github.ultrusbot.moborigins.client.entity.render;

import io.github.ultrusbot.moborigins.client.entity.model.OriginSlimeModel;
import io.github.ultrusbot.moborigins.entity.slime.OriginSlimeEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.MobEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class OriginSlimeRenderer extends MobEntityRenderer<OriginSlimeEntity, OriginSlimeModel<OriginSlimeEntity>> {
    private static final Identifier TEXTURE = new Identifier("textures/entity/slime/slime.png");

    public OriginSlimeRenderer(EntityRendererFactory.Context context) {
        super(context, new OriginSlimeModel<>(context.getPart(EntityModelLayers.SLIME)), 0.25F);
        this.addFeature(new OriginSlimeOverlayFeatureRenderer<>(this, context.getModelLoader()));
    }

    public void render(OriginSlimeEntity originSlimeEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        this.shadowRadius = 0.25F * (float)originSlimeEntity.getSize();
        super.render(originSlimeEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }

    protected void scale(OriginSlimeEntity originSlimeEntity, MatrixStack matrixStack, float f) {
        float g = 0.999F;
        matrixStack.scale(0.999F, 0.999F, 0.999F);
        matrixStack.translate(0.0D, 0.0010000000474974513D, 0.0D);
        float h = (float)originSlimeEntity.getSize();
        float i = MathHelper.lerp(f, originSlimeEntity.lastStretch, originSlimeEntity.stretch) / (h * 0.5F + 1.0F);
        float j = 1.0F / (i + 1.0F);
        matrixStack.scale(j * h, 1.0F / j * h, j * h);
    }

    public Identifier getTexture(OriginSlimeEntity originSlimeEntity) {
        return TEXTURE;
    }
}
