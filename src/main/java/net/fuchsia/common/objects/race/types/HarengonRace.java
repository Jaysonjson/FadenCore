package net.fuchsia.common.objects.race.types;

import net.fuchsia.common.objects.race.Race;
import net.fuchsia.common.objects.race.RaceCosmetics;
import net.fuchsia.common.objects.race.RaceModelType;
import net.fuchsia.common.objects.race.cosmetic.RaceCosmeticPalette;
import net.fuchsia.util.FadenIdentifier;
import org.joml.Vector3f;

public class HarengonRace extends Race {

    public HarengonRace() {
        super(FadenIdentifier.create("harengon"), new String[]{"brown", "black", "gold", "salt", "toast", "white", "white_splotched"}, new Vector3f(0.80f, 0.78f, 0.80f));
    }

    @Override
    public RaceCosmeticPalette getCosmeticPalette() {
        return RaceCosmetics.HARENGON;
    }

    @Override
    public RaceModelType model() {
        return RaceModelType.SLIM;
    }
}
