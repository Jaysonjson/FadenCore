package net.fuchsia.server;


import net.fuchsia.common.cape.FadenCape;
import net.fuchsia.common.cape.FadenCapes;
import net.minecraft.entity.player.PlayerEntity;
import org.jetbrains.annotations.Nullable;

import java.io.Serial;
import java.io.Serializable;

public class PlayerData implements Serializable {

    @Serial
    private static final long serialVersionUID = 0L;


    private String selectedCape = "";


    public String getSelectedCapeId() {
        return selectedCape;
    }

    @Nullable
    public FadenCape getSelectedCape() {
        for (FadenCape cape : FadenCapes.getCapes()) {
            if(cape.getId().equalsIgnoreCase(selectedCape)) {
                return cape;
            }
        }
        return null;
    }

    public void setSelectedCape(FadenCape selectedCape) {
        this.selectedCape = selectedCape.getId();
    }

    public void setSelectedCape(String selectedCape) {
        this.selectedCape = selectedCape;
    }
}
