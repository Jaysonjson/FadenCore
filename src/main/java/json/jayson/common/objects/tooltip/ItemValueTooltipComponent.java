package json.jayson.common.objects.tooltip;

import com.sun.source.tree.Tree;
import json.jayson.common.init.FadenItems;
import json.jayson.common.objects.CoinMap;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.BundleTooltipComponent;
import net.minecraft.client.gui.tooltip.TooltipComponent;
import net.minecraft.client.item.BundleTooltipData;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.joml.Matrix4f;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class ItemValueTooltipComponent implements TooltipComponent {

    public ItemValueTooltipData data;
    public ItemValueTooltipComponent(ItemValueTooltipData data) {
        this.data = data;
    }

    @Override
    public void drawItems(TextRenderer textRenderer, int x, int y, DrawContext context) {
        context.getMatrices().push();
        context.getMatrices().scale(0.5f, 0.5f,0.5f);
        int height = 0;
        for (Item item : data.values.keySet()) {
            context.drawItem(item.getDefaultStack(), x * 2, (y + height) * 2);
            height += 8;
        }
        context.getMatrices().pop();
    }

    @Override
    public void drawText(TextRenderer textRenderer, int x, int y, Matrix4f matrix, VertexConsumerProvider.Immediate vertexConsumers) {
        int height = 0;
        for (Item item : data.values.keySet()) {
            textRenderer.draw(String.valueOf(data.values.get(item)), x + 10, y + height, 0xAFFFFFFF, true, matrix, vertexConsumers, TextRenderer.TextLayerType.NORMAL, 0, 15728880);
            height += 8;
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
        return 8 * data.values.size();
    }

    @Override
    public int getWidth(TextRenderer textRenderer) {
        return 16 + 4 + textRenderer.getWidth(String.valueOf(data.values.getOrDefault(FadenItems.COPPER_COIN, 64)));
    }
}
