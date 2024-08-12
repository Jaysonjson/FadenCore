package net.fuchsia.common.race.types;

import net.fuchsia.common.race.Race;
import net.fuchsia.common.race.RaceModelType;
import net.fuchsia.common.race.cosmetic.RaceCosmetic;
import net.fuchsia.common.race.cosmetic.RaceCosmeticPalette;
import net.fuchsia.common.race.cosmetic.RaceCosmeticSlot;
import net.fuchsia.server.PlayerData;
import net.fuchsia.util.FadenIdentifier;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.Random;

public class TabaxiRace extends Race {

    public TabaxiRace() {
        super(FadenIdentifier.create("tabaxi"), new String[]{""}, new Vector3f(1, 1, 1));
    }

    @Override
    public RaceCosmeticPalette getCosmeticPalette() {
        return new RaceCosmeticPalette();
    }

    @Override
    public RaceModelType model() {
        return RaceModelType.BOTH;
    }

    @Override
    public PlayerData.RaceDataCosmetics randomizeCosmetics(String subId) {
        ArrayList<RaceCosmetic> ears = new ArrayList<>();
        ArrayList<RaceCosmetic> mouths = new ArrayList<>();
        for (RaceCosmetic cosmetic : getCosmeticPalette().getCosmetics(subId)) {
            if(cosmetic.getSlot() == RaceCosmeticSlot.HEAD) {
                switch (cosmetic.getType()) {
                    case "ear" -> ears.add(cosmetic);
                    case "mouth" -> mouths.add(cosmetic);
                }
            }
        }
        Random random = new Random();
        PlayerData.RaceDataCosmetics dataCosmetics = new PlayerData.RaceDataCosmetics();
        if(!ears.isEmpty()) dataCosmetics.getHead().add(ears.get(random.nextInt(ears.size())).getId());
        if(!mouths.isEmpty()) dataCosmetics.getHead().add(mouths.get(random.nextInt(mouths.size())).getId());
        return dataCosmetics;
    }
}

