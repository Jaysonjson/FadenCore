package net.fuchsia.common.objects.item;

import net.fuchsia.common.init.FadenItems;
import net.fuchsia.common.objects.tooltip.FadenTooltipComponent;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.joml.Matrix4f;

import java.util.LinkedHashMap;

public interface ItemValueToolTipRenderer {

    LinkedHashMap<Item, Integer> getValues(ItemStack itemStack);

    default int toolTipWidth(int width, FadenTooltipComponent component, TextRenderer textRenderer) {
        return width + 16 + 4 + textRenderer.getWidth(String.valueOf(getValues(component.data.itemStack).getOrDefault(FadenItems.COPPER_COIN, 64)));
    }

    default int toolTipHeight(int height, FadenTooltipComponent component) {
        return height + (8 * getValues(component.data.itemStack).size());
    }

    default void toolTipDrawText(FadenTooltipComponent component, TextRenderer textRenderer, int x, int y, Matrix4f matrix, VertexConsumerProvider.Immediate vertexConsumers) {
        int height = 0;
        for (Item item : getValues(component.data.itemStack).keySet()) {
            textRenderer.draw(String.valueOf(getValues(component.data.itemStack).get(item)), x + 10, y + height, 0xAFFFFFFF, true, matrix, vertexConsumers, TextRenderer.TextLayerType.NORMAL, 0, 15728880);
            height += 8;
        }
    }

    default void toolTipDrawItem(FadenTooltipComponent component, TextRenderer textRenderer, int x, int y, DrawContext context) {
        context.getMatrices().push();
        context.getMatrices().scale(0.5f, 0.5f,0.5f);
        int height = 0;
        for (Item item : getValues(component.data.itemStack).keySet()) {
            context.drawItem(item.getDefaultStack(), x * 2, (y + height) * 2);
            height += 8;
        }
        context.getMatrices().pop();
    }


}
