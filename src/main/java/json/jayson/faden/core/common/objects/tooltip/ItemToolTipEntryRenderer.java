package json.jayson.faden.core.common.objects.tooltip;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.VertexConsumerProvider;
import org.joml.Matrix4f;

import java.util.Collection;

public interface ItemToolTipEntryRenderer {

    Collection<ToolTipEntry> getToolTipEntries(FadenTooltipComponent component);

    @Environment(EnvType.CLIENT)
    default void toolTipDrawText(FadenTooltipComponent component, TextRenderer textRenderer, int x, int y, Matrix4f matrix, VertexConsumerProvider.Immediate vertexConsumers) {
        int yHeight = 0;
        for (ToolTipEntry toolTipEntry : getToolTipEntries(component)) {
            toolTipEntry.renderText(component, textRenderer, x, y + yHeight, matrix, vertexConsumers);
            yHeight += 9;
        }
    }

    @Environment(EnvType.CLIENT)
    default void toolTipDrawItem(FadenTooltipComponent component, TextRenderer textRenderer, int x, int y, DrawContext context) {
        int yHeight = 0;
        for (ToolTipEntry toolTipEntry : getToolTipEntries(component)) {
            toolTipEntry.renderItem(this, component, textRenderer, x, y + yHeight, context);
            yHeight += 9;
        }
    }


}
