package net.fuchsia.common.objects.tooltip;

import java.util.LinkedHashMap;

import net.fuchsia.common.data.ItemValues;
import net.fuchsia.common.objects.item.ItemToolTipRenderer;
import net.fuchsia.common.objects.item.ItemValueToolTipRenderer;
import org.joml.Matrix4f;

import net.fuchsia.common.init.FadenItems;
import net.fuchsia.common.objects.CoinMap;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.item.Item;

/*
* TODO: REWRITE TO GENERIC TOOLTIPCOMPONENT TO USE ItemToolTipRenderer INTERFACE
* */
public class FadenTooltipComponent implements TooltipComponent {

    public FadenTooltipData data;
    public FadenTooltipComponent(FadenTooltipData data) {
        this.data = data;
    }

    @Override
    public void drawItems(TextRenderer textRenderer, int x, int y, DrawContext context) {
        if(data.itemStack.getItem() instanceof ItemValueToolTipRenderer itemToolTipRenderer) {
            itemToolTipRenderer.toolTipDrawItem(this, textRenderer, x, y, context);
        }
        if(data.itemStack.getItem() instanceof ItemToolTipRenderer itemToolTipRenderer) {
            itemToolTipRenderer.toolTipDrawItem(this, textRenderer, x, y, context);
        }
    }

    @Override
    public void drawText(TextRenderer textRenderer, int x, int y, Matrix4f matrix, VertexConsumerProvider.Immediate vertexConsumers) {
        if(data.itemStack.getItem() instanceof ItemValueToolTipRenderer itemToolTipRenderer) {
            itemToolTipRenderer.toolTipDrawText(this, textRenderer, x, y, matrix, vertexConsumers);
        }
        if(data.itemStack.getItem() instanceof ItemToolTipRenderer itemToolTipRenderer) {
            itemToolTipRenderer.toolTipDrawText(this, textRenderer, x, y, matrix, vertexConsumers);
        }
    }

    public static LinkedHashMap<Item, Integer> generateMap(int amount) {
        int toAdd = amount;
        LinkedHashMap<Item, Integer> itemStacks = new LinkedHashMap<>();
        while(toAdd > 0) {
            for (Integer i : CoinMap.COINS.keySet()) {
                if(i <= toAdd) {
                    itemStacks.put(CoinMap.COINS.get(i), itemStacks.getOrDefault(CoinMap.COINS.get(i), 0) + 1);
                    toAdd -= i;
                    break;
                }
            }
        }
        return itemStacks;
    }

    @Override
    public int getHeight() {
        int height = 0;
        if(data.itemStack.getItem() instanceof ItemValueToolTipRenderer itemToolTipRenderer) {
            height += itemToolTipRenderer.toolTipHeight(0, this);
        }

        if(data.itemStack.getItem() instanceof ItemToolTipRenderer itemToolTipRenderer) {
            height += itemToolTipRenderer.toolTipHeight(height);
        }
        return height;
    }

    @Override
    public int getWidth(TextRenderer textRenderer) {
        int width = 0;
        if(data.itemStack.getItem() instanceof ItemValueToolTipRenderer itemToolTipRenderer) {
            width += itemToolTipRenderer.toolTipWidth(0, this, textRenderer);
        }

        if(data.itemStack.getItem() instanceof ItemToolTipRenderer itemToolTipRenderer) {
            width += itemToolTipRenderer.toolTipWidth(textRenderer, width);
        }

        return width;
    }
}
