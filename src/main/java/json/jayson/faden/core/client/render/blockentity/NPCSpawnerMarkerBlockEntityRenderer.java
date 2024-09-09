package json.jayson.faden.core.client.render.blockentity;

import json.jayson.faden.core.common.objects.blockentity.NPCSpawnerMarkerBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.block.entity.BlockEntityRenderer;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.joml.Matrix4f;

/* LONG NAME - JAVA MOMENT */
public class NPCSpawnerMarkerBlockEntityRenderer implements BlockEntityRenderer<NPCSpawnerMarkerBlockEntity> {



    @Override
    public void render(NPCSpawnerMarkerBlockEntity entity, float tickDelta, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, int overlay) {
        matrices.push();
        matrices.translate(0.0F, 1.35f, 0.5F);
        matrices.scale(0.025F, -0.025F, 0.025F);
        Matrix4f matrix4f = matrices.peek().getPositionMatrix();
        float h = 20 + (float)(-MinecraftClient.getInstance().textRenderer.getWidth(entity.npc) / 2);
        MinecraftClient.getInstance().textRenderer.draw(entity.npc, h, 0, 0xFF5CFF87, false, matrix4f, vertexConsumers, TextRenderer.TextLayerType.POLYGON_OFFSET, 0, light);
        matrices.pop();

        matrices.push();
        matrices.translate(0.48F, 1.35f, 0.5F);
        matrices.scale(-0.025F, -0.025F, 0.025F);
        matrix4f = matrices.peek().getPositionMatrix();
        h = (float)(-MinecraftClient.getInstance().textRenderer.getWidth(entity.npc) / 2);
        MinecraftClient.getInstance().textRenderer.draw(entity.npc, h, 0, 0xFF5CFF87, false, matrix4f, vertexConsumers, TextRenderer.TextLayerType.POLYGON_OFFSET, 0, light);
        matrices.pop();
    }

}
