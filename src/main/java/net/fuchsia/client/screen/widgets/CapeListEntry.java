package net.fuchsia.client.screen.widgets;

import net.fuchsia.client.screen.ScreenUtil;
import net.fuchsia.common.cape.FadenCape;
import net.fuchsia.common.cape.FadenCapes;
import net.fuchsia.server.PlayerData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.text.Text;

import java.util.ArrayList;

public class CapeListEntry extends AlwaysSelectedEntryListWidget.Entry<CapeListEntry> {

    public FadenCape cape;
    public PlayerData playerData;
    public CapeListEntry(FadenCape cape, PlayerData playerData) {
        this.cape = cape;
    }

    @Override
    public Text getNarration() {
        return Text.literal("");
    }

    @Override
    public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        int color = 0xFFFFFFFF;
        if(playerData != null && playerData.getCapes().contains(cape.getId())) {
            color = 0xFFFFAAAA;
        }
        context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, cape.getName(), x, y, color);
        ScreenUtil.drawWrappedString(context, cape.getDescription().getString(), x, y + 9, 210, 3, color);
    }


}
