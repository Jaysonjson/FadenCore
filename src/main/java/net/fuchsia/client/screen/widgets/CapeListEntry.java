package net.fuchsia.client.screen.widgets;

import net.fuchsia.common.cape.FadenCape;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.text.Text;

public class CapeListEntry extends AlwaysSelectedEntryListWidget.Entry<CapeListEntry> {

    public FadenCape cape;
    public CapeListEntry(FadenCape cape) {
        this.cape = cape;
    }

    @Override
    public Text getNarration() {
        return Text.literal("");
    }

    @Override
    public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, cape.getName(), x, y, 0xFFFFFFFF);
    }


}
