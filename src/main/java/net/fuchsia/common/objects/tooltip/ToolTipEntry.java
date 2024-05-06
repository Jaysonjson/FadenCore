package net.fuchsia.common.objects.tooltip;

import net.fuchsia.common.init.FadenDataComponents;
import net.fuchsia.common.objects.item.ItemToolTipEntryRenderer;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.joml.Matrix4f;

public interface ToolTipEntry {

    @Nullable
    Item getItem(FadenTooltipComponent component);
    @Nullable
    Identifier getTexture(FadenTooltipComponent component);
    default int getTextureScale() {
        return 16;
    }
    Text getText(FadenTooltipComponent component);

    default int getTextColor(FadenTooltipComponent component) {
        return 0xAFFFFFFF;
    }

    default int getTextBackgroundColor(FadenTooltipComponent component) {
        return 0;
    }

    default void renderItem(ItemToolTipEntryRenderer entryRenderer, FadenTooltipComponent component, TextRenderer textRenderer, int x, int y, DrawContext context) {
        context.getMatrices().push();
        context.getMatrices().scale(0.5f, 0.5f,0.5f);
        Item item = getItem(component);
        Identifier identifier = getTexture(component);

        if(item != null) {
            context.drawItem(item.getDefaultStack(), x * 2, y * 2);
        }

        if(identifier != null) {
            context.drawTexture(identifier, x * 2, y * 2, 0, 0, 16, 16, getTextureScale(), getTextureScale());
        }
        context.getMatrices().pop();
    }

    default void renderText(FadenTooltipComponent component, TextRenderer textRenderer, int x, int y, Matrix4f matrix, VertexConsumerProvider.Immediate vertexConsumers) {
        textRenderer.draw(getText(component), x + 10, y, getTextColor(component), true, matrix, vertexConsumers, TextRenderer.TextLayerType.NORMAL, getTextBackgroundColor(component), 15728880);
    }


}
