package net.fuchsia.common.race.cosmetic;

import net.fuchsia.util.FadenIdentifier;
import net.minecraft.client.util.ModelIdentifier;

public class RaceCosmetics {

    public static RaceCosmeticPalette HARENGON = new RaceCosmeticPalette();

    public static void add() {
        HARENGON.addCosmetic("brown", new RaceCosmetic(new ModelIdentifier(FadenIdentifier.create("harengon_ears"), "inventory"), RaceCosmeticType.HEAD));
    }

}
