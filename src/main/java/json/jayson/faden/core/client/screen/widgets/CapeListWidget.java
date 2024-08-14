package json.jayson.faden.core.client.screen.widgets;

import json.jayson.faden.core.registry.FadenCoreRegistry;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import json.jayson.faden.core.client.screen.CapeSelectScreen;
import json.jayson.faden.core.common.cape.FadenCoreCape;
import json.jayson.faden.core.server.PlayerData;
import json.jayson.faden.core.server.client.ClientPlayerDatas;
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
                for (FadenCoreCape cape : FadenCoreRegistry.CAPE) {
                    if(cape.getName().getString().toLowerCase().contains(searchBox.toLowerCase())) {
                        if(!entryExists(cape.getIdentifier().toString())) addEntry(new CapeListEntry(cape, data));
                        if (data.getSelectedCapeId().equalsIgnoreCase(cape.getIdentifier().toString())) {
                            setSelected(getEntry(f));
                        }
                        ++f;
                    }
                }
            } else {
                for (String s : data.getCapes()) {
                    FadenCoreCape cape = FadenCoreRegistry.CAPE.get(Identifier.of(s));
                    if (cape != null && cape.getName().getString().toLowerCase().contains(searchBox.toLowerCase())) {
                        if(!entryExists(cape.getIdentifier().toString())) addEntry(new CapeListEntry(cape, data));
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
            if(child.cape != null && child.cape.getIdentifier().toString().equalsIgnoreCase(id)) {
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
