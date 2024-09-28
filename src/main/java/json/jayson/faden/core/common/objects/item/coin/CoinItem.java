package json.jayson.faden.core.common.objects.item.coin;

import java.util.Optional;

import json.jayson.faden.core.common.objects.CoinMap;
import net.minecraft.item.tooltip.TooltipData;
import org.joml.Matrix4f;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import json.jayson.faden.core.common.objects.tooltip.ItemToolTipRenderer;
import json.jayson.faden.core.common.objects.tooltip.FadenTooltipComponent;
import json.jayson.faden.core.common.objects.tooltip.FadenTooltipData;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CoinItem extends Item implements ItemToolTipRenderer {
    private final int value;
    public CoinItem(Settings settings, int value) {
        super(settings);
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        return Optional.of(new FadenTooltipData(stack));
    }

    @Environment(EnvType.CLIENT)
    @Override
    public void toolTipDrawText(FadenTooltipComponent component, TextRenderer textRenderer, int x, int y, Matrix4f matrix, VertexConsumerProvider.Immediate vertexConsumers) {
        textRenderer.draw(String.valueOf(value * component.data.itemStack.getCount()), x + 10, y, 0xAFFFFFFF, true, matrix, vertexConsumers, TextRenderer.TextLayerType.NORMAL, 0, 15728880);
    }

    @Override
    public void toolTipDrawItem(FadenTooltipComponent component, TextRenderer textRenderer, int x, int y, DrawContext context) {
        context.getMatrices().push();
        context.getMatrices().scale(0.5f, 0.5f,0.5f);
        context.drawItem(CoinMap.getLowestCoin().getDefaultStack(), x * 2, y * 2);
        context.getMatrices().pop();
    }

    @Override
    public int toolTipHeight(FadenTooltipComponent component,int height) {
        return 10;
    }

    @Override
    public int toolTipWidth(TextRenderer renderer, int width) {
        return renderer.getWidth(String.valueOf(value));
    }
}
