package net.fuchsia.common.race.cosmetic;

import java.util.ArrayList;
import java.util.HashMap;

public class RaceCosmeticPalette {

    public HashMap<String, ArrayList<RaceCosmetic>> cosmetics = new HashMap<>();


    public RaceCosmeticPalette() {

    }


    public void addCosmetic(String id, RaceCosmetic cosmetic) {
        ArrayList<RaceCosmetic> cAr = cosmetics.getOrDefault(id, new ArrayList<>());
        cAr.add(cosmetic);
        cosmetics.put(id, cAr);
    }

    public HashMap<String, ArrayList<RaceCosmetic>> getCosmetics() {
        return cosmetics;
    }

    public ArrayList<RaceCosmetic> getCosmetics(String id) {
        return getCosmetics().get(id);
    }
}
