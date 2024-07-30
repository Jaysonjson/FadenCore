package net.fuchsia.race.types;

import net.fuchsia.race.Race;
import net.fuchsia.race.RaceModelType;
import net.fuchsia.race.cosmetic.RaceCosmeticPalette;
import net.fuchsia.util.FadenIdentifier;
import org.joml.Vector3f;

public class HumanRace extends Race {

    public HumanRace() {
        super(FadenIdentifier.create("human"), new String[]{"default"}, new Vector3f(1, 1, 1));
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
