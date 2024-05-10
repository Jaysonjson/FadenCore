package net.fuchsia.client.screen.widgets;

import net.fuchsia.common.cape.FadenCape;
import net.fuchsia.common.cape.FadenCapes;
import net.fuchsia.server.PlayerData;
import net.fuchsia.server.client.ClientPlayerDatas;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.widget.AlwaysSelectedEntryListWidget;
import org.jetbrains.annotations.Nullable;

public class CapeListWidget extends AlwaysSelectedEntryListWidget<CapeListEntry> implements AutoCloseable{
    public CapeListWidget(MinecraftClient minecraftClient, int i, int j, int k, int l) {
        super(minecraftClient, i, j, k, l);

        PlayerData data = ClientPlayerDatas.getPlayerData(minecraftClient.player.getUuid());
        int f = 0;
        if(data != null) {
            if(FadenCapes.getPlayerCapes().containsKey(minecraftClient.player.getUuid())) {
                for (String s : FadenCapes.getPlayerCapes().get(minecraftClient.player.getUuid())) {
                    FadenCape cape = FadenCapes.getCapeById(s);
                    if (cape != null) {
                        addEntry(new CapeListEntry(cape));
                        if (data.getSelectedCapeId().equalsIgnoreCase(s)) {
                            setSelected(getEntry(f));
                        }
                        ++f;
                    }
                }
            }
        }
    }

    @Override
    public void close() throws Exception {

    }

    @Override
    public void setSelected(@Nullable CapeListEntry entry) {
        super.setSelected(entry);
    }

}
