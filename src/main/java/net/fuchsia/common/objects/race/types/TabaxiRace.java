package net.fuchsia.common.objects.race.types;

import net.fuchsia.common.objects.race.Race;
import net.fuchsia.common.objects.race.RaceModelType;
import net.fuchsia.common.objects.race.cosmetic.RaceCosmeticPalette;
import net.fuchsia.util.FadenIdentifier;
import org.joml.Vector3f;

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

}

