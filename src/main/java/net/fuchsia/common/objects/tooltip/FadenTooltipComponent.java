package net.fuchsia.common.objects.tooltip;

import java.util.LinkedHashMap;

import net.fuchsia.common.data.ItemValues;
import net.fuchsia.common.init.FadenDataComponents;
import net.fuchsia.common.objects.item.ItemToolTipRenderer;
import net.fuchsia.common.objects.item.ItemValueToolTipRenderer;
import net.minecraft.item.Items;
import org.joml.Matrix4f;

import net.fuchsia.common.init.FadenItems;
import net.fuchsia.common.objects.CoinMap;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.item.Item;

/*
* TODO: FIX HEIGHT CALULCATIONS
* */
public class FadenTooltipComponent implements TooltipComponent {

    public int extraHeight = 0;
    public int tooltipHeight = 0;
    public FadenTooltipData data;
    public FadenTooltipComponent(FadenTooltipData data) {
        this.data = data;
    }

    @Override
    public void drawItems(TextRenderer textRenderer, int x, int y, DrawContext context) {
        extraHeight = 0;
        tooltipHeight = 2;
        if(data.itemStack.contains(FadenDataComponents.ITEM_TIER)) {
            context.getMatrices().push();
            context.getMatrices().scale(0.5f, 0.5f,0.5f);
            context.drawItem(Items.DIAMOND.getDefaultStack(), x * 2, y * 2);
            context.getMatrices().pop();
            extraHeight = 5;
            tooltipHeight = 11;
        }
        if(data.itemStack.getItem() instanceof ItemValueToolTipRenderer itemToolTipRenderer) {
            itemToolTipRenderer.toolTipDrawCoinItems(this, textRenderer, x, y + extraHeight, context);
        }
        if(data.itemStack.getItem() instanceof ItemToolTipRenderer itemToolTipRenderer) {
            itemToolTipRenderer.toolTipDrawItem(this, textRenderer, x, y + extraHeight, context);
        }
    }

    @Override
    public void drawText(TextRenderer textRenderer, int x, int y, Matrix4f matrix, VertexConsumerProvider.Immediate vertexConsumers) {
        extraHeight = 0;
        tooltipHeight = 2;
        if(data.itemStack.contains(FadenDataComponents.ITEM_TIER)) {
            textRenderer.draw(data.itemStack.get(FadenDataComponents.ITEM_TIER), x + 10, y, 0xAFFFFFFF, true, matrix, vertexConsumers, TextRenderer.TextLayerType.NORMAL, 0, 15728880);
            extraHeight = 5;
            tooltipHeight = 11;
        }
        if(data.itemStack.getItem() instanceof ItemValueToolTipRenderer itemToolTipRenderer) {
            itemToolTipRenderer.toolTipDrawCoinText(this, textRenderer, x, y + extraHeight, matrix, vertexConsumers);
        }
        if(data.itemStack.getItem() instanceof ItemToolTipRenderer itemToolTipRenderer) {
            itemToolTipRenderer.toolTipDrawText(this, textRenderer, x, y + extraHeight, matrix, vertexConsumers);
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
        int height = extraHeight;
        if(data.itemStack.getItem() instanceof ItemValueToolTipRenderer itemToolTipRenderer) {
            height += itemToolTipRenderer.toolTipHeight(0, this);
        }

        if(data.itemStack.getItem() instanceof ItemToolTipRenderer itemToolTipRenderer) {
            height += itemToolTipRenderer.toolTipHeight(this, height);
        }
        return height;
    }

    public int heightAfterCoinAndTier() {
        return tooltipHeight;
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
