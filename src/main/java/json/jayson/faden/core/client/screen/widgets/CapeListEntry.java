package json.jayson.faden.core.client.screen.widgets;

import json.jayson.faden.core.client.screen.ScreenUtil;
import json.jayson.faden.core.common.cape.FadenCoreCape;
import json.jayson.faden.core.server.PlayerData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import net.minecraft.text.Text;

public class CapeListEntry extends AlwaysSelectedEntryListWidget.Entry<CapeListEntry> {

    public FadenCoreCape cape;
    public PlayerData playerData;
    public CapeListEntry(FadenCoreCape cape, PlayerData playerData) {
        this.cape = cape;
    }

    @Override
    public Text getNarration() {
        return Text.literal("");
    }

    @Override
    public void render(DrawContext context, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta) {
        int color = 0xFFFFFFFF;
        if(playerData != null && playerData.getCapes().contains(cape.getIdentifier().toString())) {
            color = 0xFFFFAAAA;
        }
        context.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, cape.getName(), x, y, color);
        ScreenUtil.drawWrappedString(context, cape.getDescription().getString(), x, y + 9, 210, 3, color);
    }


}
