package net.fuchsia.util;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.BakedQuad;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;

import java.util.List;

public class FadenRenderUtil {

    public static void renderBakedModel(MatrixStack matrices, VertexConsumerProvider vertexConsumers, BakedModel model, int light) {
        Random random = Random.create();
        Direction[] var10 = Direction.values();

        for (Direction direction : var10) {
            random.setSeed(42L);
            renderModelQuads(matrices, vertexConsumers.getBuffer(RenderLayer.getCutout()), model.getQuads(null, direction, random), light);
        }

        random.setSeed(42L);
        renderModelQuads(matrices, vertexConsumers.getBuffer(RenderLayer.getCutout()), model.getQuads(null, null, random), light);
        matrices.pop();
    }

    public static void renderModelQuads(MatrixStack matrices, VertexConsumer vertices, List<BakedQuad> quads, int light) {
        MatrixStack.Entry entry = matrices.peek();

        for (BakedQuad bakedQuad : quads) {
            int i = bakedQuad.hasColor() ? bakedQuad.getColorIndex() : -1;
            float f = (float) (i >> 16 & 255) / 255.0F;
            float g = (float) (i >> 8 & 255) / 255.0F;
            float h = (float) (i & 255) / 255.0F;
            vertices.quad(entry, bakedQuad, f, g, h, light, light);
        }
    }

}
