package net.fuchsia.common.objects.race.types;

import net.fuchsia.common.objects.race.Race;
import net.fuchsia.common.objects.race.RaceCosmetics;
import net.fuchsia.common.objects.race.RaceModelType;
import net.fuchsia.common.objects.race.cosmetic.RaceCosmeticPalette;
import net.fuchsia.util.FadenIdentifier;
import org.joml.Vector3f;

public class ElfRace extends Race {

    public ElfRace() {
        super(FadenIdentifier.create("elf"), new String[]{"pale", "drow"}, new Vector3f(0.95f, 0.95f, 0.95f));
    }

    @Override
    public RaceCosmeticPalette getCosmeticPalette() {
        return RaceCosmetics.ELF;
    }

    @Override
    public RaceModelType model() {
        return RaceModelType.SLIM;
    }

}
