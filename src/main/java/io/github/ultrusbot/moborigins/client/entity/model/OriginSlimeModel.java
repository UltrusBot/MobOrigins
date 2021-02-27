package io.github.ultrusbot.moborigins.client.entity.model;

import com.google.common.collect.ImmutableList;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.CompositeEntityModel;
import net.minecraft.entity.Entity;

@Environment(EnvType.CLIENT)
public class OriginSlimeModel<T extends Entity> extends CompositeEntityModel<T> {
    private final ModelPart innerCube;
    private final ModelPart rightEye;
    private final ModelPart leftEye;
    private final ModelPart mouth;

    public OriginSlimeModel(int size) {
        this.innerCube = new ModelPart(this, 0, size);
        this.rightEye = new ModelPart(this, 32, 0);
        this.leftEye = new ModelPart(this, 32, 4);
        this.mouth = new ModelPart(this, 32, 8);
        if (size > 0) {
            this.innerCube.addCuboid(-3.0F, 17.0F, -3.0F, 6.0F, 6.0F, 6.0F);
            this.rightEye.addCuboid(-3.25F, 18.0F, -3.5F, 2.0F, 2.0F, 2.0F);
            this.leftEye.addCuboid(1.25F, 18.0F, -3.5F, 2.0F, 2.0F, 2.0F);
            this.mouth.addCuboid(0.0F, 21.0F, -3.5F, 1.0F, 1.0F, 1.0F);
        } else {
            this.innerCube.addCuboid(-4.0F, 16.0F, -4.0F, 8.0F, 8.0F, 8.0F);
        }

    }

    public void setAngles(T entity, float limbAngle, float limbDistance, float animationProgress, float headYaw, float headPitch) {
    }

    public Iterable<ModelPart> getParts() {
        return ImmutableList.of(this.innerCube, this.rightEye, this.leftEye, this.mouth);
    }
}
