package net.fuchsia.server;


import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;

import org.jetbrains.annotations.Nullable;

import net.fuchsia.common.cape.FadenCape;
import net.fuchsia.common.cape.FadenCapes;

public class PlayerData implements Serializable {

    @Serial
    private static final long serialVersionUID = 0L;


    private String selectedCape = "";
    private ArrayList<String> capes = new ArrayList<>();


    public String getSelectedCapeId() {
        return selectedCape;
    }

    public ArrayList<String> getCapes() {
        return capes;
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
