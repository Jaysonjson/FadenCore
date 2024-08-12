package net.fuchsia.common.race.types;

import net.fuchsia.common.race.Race;
import net.fuchsia.common.race.RaceCosmetics;
import net.fuchsia.common.race.RaceModelType;
import net.fuchsia.common.race.cosmetic.RaceCosmeticPalette;
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
