package net.fuchsia.client.screen.widgets;

import org.jetbrains.annotations.Nullable;

import net.fuchsia.client.screen.CapeSelectScreen;
import net.fuchsia.common.cape.FadenCape;
import net.fuchsia.common.cape.FadenCapes;
import net.fuchsia.server.PlayerData;
import net.fuchsia.server.client.ClientPlayerDatas;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;

public class CapeListWidget extends AlwaysSelectedEntryListWidget<CapeListEntry> implements AutoCloseable{
    public CapeListWidget(MinecraftClient minecraftClient, int i, int j, int k, int l, CapeSelectScreen screen) {
        super(minecraftClient, i, j, k, l);
        reload(false, "");
    }

    public void reload(boolean showAll, String searchBox) {
        clearEntries();
        PlayerData data = ClientPlayerDatas.getPlayerData(MinecraftClient.getInstance().player.getUuid());
        int f = 0;
        if(data != null) {
            if(showAll) {
                for (FadenCape cape : FadenCapes.getCapes()) {
                    if(cape.getName().getString().toLowerCase().contains(searchBox.toLowerCase())) {
                        if(!entryExists(cape.getId())) addEntry(new CapeListEntry(cape, data));
                        if (data.getSelectedCapeId().equalsIgnoreCase(cape.getId())) {
                            setSelected(getEntry(f));
                        }
                        ++f;
                    }
                }
            } else {
                for (String s : data.getCapes()) {
                    FadenCape cape = FadenCapes.getCapeById(s);
                    if (cape != null && cape.getName().getString().toLowerCase().contains(searchBox.toLowerCase())) {
                        if(!entryExists(cape.getId())) addEntry(new CapeListEntry(cape, data));
                        if (data.getSelectedCapeId().equalsIgnoreCase(s)) {
                            setSelected(getEntry(f));
                        }
                        ++f;
                    }
                }
            }
        }
    }

    public boolean entryExists(String id) {
        for (CapeListEntry child : children()) {
            if(child.cape != null && child.cape.getId().equalsIgnoreCase(id)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public void setSelected(@Nullable CapeListEntry entry) {
        super.setSelected(entry);
    }

}
