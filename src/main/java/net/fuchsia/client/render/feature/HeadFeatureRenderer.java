package net.fuchsia.client.render.feature;

import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.RotationAxis;
import org.joml.Quaternionf;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class HeadFeatureRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public HeadFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        matrices.push();
        BakedModel model = MinecraftClient.getInstance().getBakedModelManager().getModel(new ModelIdentifier(new Identifier("faden", "bunny_ears"), "inventory"));
        matrices.translate(-model.getTransformation().head.translation.x, -model.getTransformation().head.translation.y, -model.getTransformation().head.translation.z);
        matrices.scale(model.getTransformation().head.scale.x, model.getTransformation().head.scale.y, model.getTransformation().head.scale.z);
        //matrices.multiply(RotationAxis.of(model.getTransformation().head.rotation).rotation(360));
        //matrices.multiply(RotationAxis.POSITIVE_Z.rotation(45));
        ((ModelWithHead)this.getContextModel()).getHead().rotate(matrices);
        MinecraftClient.getInstance().getItemRenderer().renderItem(new ItemStack(Items.GRASS_BLOCK), ModelTransformationMode.NONE, false, matrices, vertexConsumers,light, light, model);

        net.minecraft.util.math.random.Random random = net.minecraft.util.math.random.Random.create();
        Direction[] var10 = Direction.values();

        for (Direction direction : var10) {
            random.setSeed(42L);
            renderModel(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityGlint()), model.getQuads(null, direction, random), light);
        }

        random.setSeed(42L);
        renderModel(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityGlint()), model.getQuads(null, null, random), light);
        matrices.pop();
    }

    public static void renderModel(MatrixStack matrices, VertexConsumer vertices, List<BakedQuad> quads, int light) {
        MatrixStack.Entry entry = matrices.peek();
        Iterator var9 = quads.iterator();

        while(var9.hasNext()) {
            BakedQuad bakedQuad = (BakedQuad)var9.next();
            int i = bakedQuad.hasColor() ? bakedQuad.getColorIndex() : -1;
            float f = (float)(i >> 16 & 255) / 255.0F;
            float g = (float)(i >> 8 & 255) / 255.0F;
            float h = (float)(i & 255) / 255.0F;
            vertices.quad(entry, bakedQuad, f, g, h, light, light);
        }
    }
}

