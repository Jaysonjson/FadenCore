package json.jayson.faden.core.common.objects.tooltip;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;

import org.joml.Matrix4f;

import json.jayson.faden.core.common.objects.CoinMap;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.item.Item;

public class FadenTooltipComponent implements TooltipComponent {

    public FadenTooltipData data;
    public FadenTooltipComponent(FadenTooltipData data) {
        this.data = data;
    }

    @Override
    public void drawItems(TextRenderer textRenderer, int x, int y, DrawContext context) {
        if(data.itemStack.getItem() instanceof ItemToolTipEntryRenderer entryRenderer) {
            entryRenderer.toolTipDrawItem(this, textRenderer, x, y, context);
        }

        if(data.itemStack.getItem() instanceof ItemToolTipRenderer itemToolTipRenderer) {
            itemToolTipRenderer.toolTipDrawItem(this, textRenderer, x, y, context);
        }
    }

    @Override
    public void drawText(TextRenderer textRenderer, int x, int y, Matrix4f matrix, VertexConsumerProvider.Immediate vertexConsumers) {
        if(data.itemStack.getItem() instanceof ItemToolTipEntryRenderer entryRenderer) {
            entryRenderer.toolTipDrawText(this, textRenderer, x, y, matrix, vertexConsumers);
        }

        if(data.itemStack.getItem() instanceof ItemToolTipRenderer itemToolTipRenderer) {
            itemToolTipRenderer.toolTipDrawText(this, textRenderer, x, y, matrix, vertexConsumers);
        }
    }

    public static LinkedHashMap<Item, Integer> generateMap(int amount) {
        int toAdd = amount;
        LinkedHashMap<Item, Integer> itemStacks = new LinkedHashMap<>();
        while(toAdd > 0) {
            for (Integer i : CoinMap.getCoinMap().keySet()) {
                if(i <= toAdd) {
                    itemStacks.put(CoinMap.getCoinMap().get(i), itemStacks.getOrDefault(CoinMap.getCoinMap().get(i), 0) + 1);
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
        boolean changesHeight = false;
        if(data.itemStack.getItem() instanceof ItemToolTipRenderer itemToolTipRenderer) {
            height += itemToolTipRenderer.toolTipHeight(this, height);
            changesHeight = true;
        }

        int entrySize = 0;
        if(data.itemStack.getItem() instanceof ItemToolTipEntryRenderer entryRenderer) {
            entrySize = entryRenderer.getToolTipEntries(this).size();
            height += entrySize * 10;
            if(entrySize != 0) changesHeight = true;
        }

        //the + 1 killed the vanilla stuff, so this is the guard for it
        if(!changesHeight) return -1;

        return height - entrySize + 1;
    }

    @Override
    public int getWidth(TextRenderer textRenderer) {
        int width = 0;
        if(data.itemStack.getItem() instanceof ItemToolTipRenderer itemToolTipRenderer) {
            width += itemToolTipRenderer.toolTipWidth(textRenderer, width);
        }
        if(data.itemStack.getItem() instanceof ItemToolTipEntryRenderer entryRenderer) {
            Collection<Integer> textSizes = new ArrayList<>();
            for (ToolTipEntry toolTipEntry : entryRenderer.getToolTipEntries(this)) {
                textSizes.add(textRenderer.getWidth(toolTipEntry.getText(this).getString()));
            }
            if(!textSizes.isEmpty()) {
                width += Collections.max(textSizes) + 10; //due to icon size
            }
        }
        return width;
    }
}
