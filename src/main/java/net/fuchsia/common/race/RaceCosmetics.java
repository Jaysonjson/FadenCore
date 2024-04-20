package net.fuchsia.common.race;

import net.fuchsia.common.race.cosmetic.RaceCosmetic;
import net.fuchsia.common.race.cosmetic.RaceCosmeticPalette;
import net.fuchsia.common.race.cosmetic.RaceCosmeticType;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.client.util.ModelIdentifier;

public class RaceCosmetics {

    public static RaceCosmeticPalette HARENGON = new RaceCosmeticPalette();

    public static void add() {
        HARENGON.addCosmetic("brown", new RaceCosmetic(FadenIdentifier.modelId("player_cosmetic/harengon/ears/harengon_ears_brown"), RaceCosmeticType.HEAD));
        HARENGON.addCosmetic("black", new RaceCosmetic(FadenIdentifier.modelId("player_cosmetic/harengon/ears/harengon_ears_black"), RaceCosmeticType.HEAD));
        HARENGON.addCosmetic("gold", new RaceCosmetic(FadenIdentifier.modelId("player_cosmetic/harengon/ears/harengon_ears_gold"), RaceCosmeticType.HEAD));
        HARENGON.addCosmetic("salt", new RaceCosmetic(FadenIdentifier.modelId("player_cosmetic/harengon/ears/harengon_ears_salt"), RaceCosmeticType.HEAD));
        HARENGON.addCosmetic("toast", new RaceCosmetic(FadenIdentifier.modelId("player_cosmetic/harengon/ears/harengon_ears_toast"), RaceCosmeticType.HEAD));
        HARENGON.addCosmetic("white", new RaceCosmetic(FadenIdentifier.modelId("player_cosmetic/harengon/ears/harengon_ears_white"), RaceCosmeticType.HEAD));
        HARENGON.addCosmetic("white_splotched", new RaceCosmetic(FadenIdentifier.modelId("player_cosmetic/harengon/ears/harengon_ears_white_splotched"), RaceCosmeticType.HEAD));

        HARENGON.addCosmetic("brown", new RaceCosmetic(FadenIdentifier.modelId("player_cosmetic/harengon/tail/harengon_tail_brown"), RaceCosmeticType.CHEST));
    }

}
