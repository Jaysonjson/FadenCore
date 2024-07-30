package net.fuchsia.common.objects.race.types;

import net.fuchsia.common.objects.race.Race;
import net.fuchsia.common.objects.race.RaceModelType;
import net.fuchsia.common.objects.race.cosmetic.RaceCosmeticPalette;
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
