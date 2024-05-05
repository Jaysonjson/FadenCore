package net.fuchsia.common.objects.item;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
        return 16 + 4 + textRenderer.getWidth(String.valueOf(getValues(component.data.itemStack).getOrDefault(FadenItems.COPPER_COIN, 64)));
    }

    default int toolTipHeight(int height, FadenTooltipComponent component) {
        return (8 * getValues(component.data.itemStack).size());
    }

    @Environment(EnvType.CLIENT)
    default void toolTipDrawCoinText(FadenTooltipComponent component, TextRenderer textRenderer, int x, int y, Matrix4f matrix, VertexConsumerProvider.Immediate vertexConsumers) {
        int height = 0;
        for (Item item : getValues(component.data.itemStack).keySet()) {
            textRenderer.draw(String.valueOf(getValues(component.data.itemStack).get(item)), x + 10, y + component.extraHeight + height, 0xAFFFFFFF, true, matrix, vertexConsumers, TextRenderer.TextLayerType.NORMAL, 0, 15728880);
            height += 8;
        }
        component.extraHeight = height;
    }

    default void toolTipDrawCoinItems(FadenTooltipComponent component, TextRenderer textRenderer, int x, int y, DrawContext context) {
        context.getMatrices().push();
        context.getMatrices().scale(0.5f, 0.5f,0.5f);
        int height = 0;
        for (Item item : getValues(component.data.itemStack).keySet()) {
            context.drawItem(item.getDefaultStack(), x * 2, (y + component.extraHeight + height) * 2);
            height += 8;
        }
        component.extraHeight = height;
        context.getMatrices().pop();
    }


}
