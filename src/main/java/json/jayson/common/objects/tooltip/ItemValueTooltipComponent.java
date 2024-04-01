package json.jayson.common.objects.tooltip;

import json.jayson.common.init.FadenItems;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;

public class ItemValueTooltipComponent implements TooltipComponent {

    @Override
    public void drawItems(TextRenderer textRenderer, int x, int y, DrawContext context) {
        context.getMatrices().push();
        context.getMatrices().scale(0.5f, 0.5f,0.5f);
        context.drawItem(FadenItems.SILVER_COIN.getDefaultStack(), x * 2, y * 2);
        context.drawItem(FadenItems.COPPER_COIN.getDefaultStack(), x * 2, (y + 8) * 2);
        context.getMatrices().pop();
    }

    @Override
    public void drawText(TextRenderer textRenderer, int x, int y, Matrix4f matrix, VertexConsumerProvider.Immediate vertexConsumers) {
        textRenderer.draw("5", x + 10, y, 0xAFFFFFFF, true, matrix, vertexConsumers, TextRenderer.TextLayerType.NORMAL, 0, 15728880);
        textRenderer.draw("1", x + 10, y  + 8, 0xAFFFFFFF, true, matrix, vertexConsumers, TextRenderer.TextLayerType.NORMAL, 0, 15728880);
    }

    @Override
    public int getHeight() {
        return 16;
    }

    @Override
    public int getWidth(TextRenderer textRenderer) {
        return 16 + 4 + textRenderer.getWidth("50");
    }
}
