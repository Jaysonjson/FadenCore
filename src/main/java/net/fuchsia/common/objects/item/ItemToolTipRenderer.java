package net.fuchsia.common.objects.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fuchsia.common.objects.tooltip.FadenTooltipComponent;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.VertexConsumerProvider;
import org.joml.Matrix4f;

public interface ItemToolTipRenderer {

    default int toolTipWidth(TextRenderer renderer, int width) {
        return width;
    }
    default int toolTipHeight(FadenTooltipComponent component, int height) {
        return height;
    }

    @Environment(EnvType.CLIENT)
    void toolTipDrawText(FadenTooltipComponent component, TextRenderer textRenderer, int x, int y, Matrix4f matrix, VertexConsumerProvider.Immediate vertexConsumers);
    void toolTipDrawItem(FadenTooltipComponent component, TextRenderer textRenderer, int x, int y, DrawContext context);

}
