package net.fuchsia.common.race;

import net.fuchsia.common.race.cosmetic.RaceCosmetic;
import net.fuchsia.common.race.cosmetic.RaceCosmeticPalette;
import net.fuchsia.common.race.cosmetic.RaceCosmeticType;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.client.util.ModelIdentifier;

public class RaceCosmetics {

    public static RaceCosmeticPalette HARENGON = new RaceCosmeticPalette();

    public static void add() {
        HARENGON.addCosmetic("brown", new RaceCosmetic(new ModelIdentifier(FadenIdentifier.create("player_cosmetic/harengon/harengon_ears_brown"), "inventory"), RaceCosmeticType.HEAD));
        HARENGON.addCosmetic("black", new RaceCosmetic(new ModelIdentifier(FadenIdentifier.create("player_cosmetic/harengon/harengon_ears_black"), "inventory"), RaceCosmeticType.HEAD));
        HARENGON.addCosmetic("gold", new RaceCosmetic(new ModelIdentifier(FadenIdentifier.create("player_cosmetic/harengon/harengon_ears_gold"), "inventory"), RaceCosmeticType.HEAD));
        HARENGON.addCosmetic("salt", new RaceCosmetic(new ModelIdentifier(FadenIdentifier.create("player_cosmetic/harengon/harengon_ears_salt"), "inventory"), RaceCosmeticType.HEAD));
        HARENGON.addCosmetic("toast", new RaceCosmetic(new ModelIdentifier(FadenIdentifier.create("player_cosmetic/harengon/harengon_ears_toast"), "inventory"), RaceCosmeticType.HEAD));
        HARENGON.addCosmetic("white", new RaceCosmetic(new ModelIdentifier(FadenIdentifier.create("player_cosmetic/harengon/harengon_ears_white"), "inventory"), RaceCosmeticType.HEAD));
        HARENGON.addCosmetic("white_splotched", new RaceCosmetic(new ModelIdentifier(FadenIdentifier.create("player_cosmetic/harengon/harengon_ears_white_splotched"), "inventory"), RaceCosmeticType.HEAD));
    }

}
