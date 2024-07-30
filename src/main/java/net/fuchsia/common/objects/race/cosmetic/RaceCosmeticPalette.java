package net.fuchsia.common.objects.race.cosmetic;

import java.util.ArrayList;
import java.util.HashMap;

public class RaceCosmeticPalette {

    private HashMap<String, ArrayList<RaceCosmetic>> cosmetics = new HashMap<>();

    public RaceCosmeticPalette() {
        cosmetics = new HashMap<>();
    }


    public void addCosmetic(String id, RaceCosmetic cosmetic) {
        ArrayList<RaceCosmetic> cAr = cosmetics.getOrDefault(id, new ArrayList<>());
        cAr.add(cosmetic);
        cosmetics.put(id, cAr);
    }

    public void addCosmetic(String id, String modelId, RaceCosmeticType type, String cosmeticId) {
        addCosmetic(id, new RaceCosmetic(modelId, type, cosmeticId));
    }

    public HashMap<String, ArrayList<RaceCosmetic>> getCosmetics() {
        return cosmetics;
    }

    public ArrayList<RaceCosmetic> getCosmetics(String id) {
        if(getCosmetics().containsKey(id)) {
            return getCosmetics().get(id);
        }
        return new ArrayList<>();
    }
}
