package net.fuchsia.common.race;

import net.fuchsia.common.race.cosmetic.RaceCosmetic;
import net.fuchsia.common.race.cosmetic.RaceCosmeticPalette;
import net.fuchsia.common.race.cosmetic.RaceCosmeticType;

public class RaceCosmetics {

    public static RaceCosmeticPalette HARENGON = new RaceCosmeticPalette();
    public static RaceCosmeticPalette ELF = new RaceCosmeticPalette();

    public static void add() {
        /*
        * HARENGON
        * */
        //EAR
        HARENGON.addCosmetic("brown", "player_cosmetic/harengon/ears/harengon_ears_brown", RaceCosmeticType.HEAD, "ear_0");
        HARENGON.addCosmetic("brown", "player_cosmetic/harengon/ears/floppy/harengon_ears_floopy_brown", RaceCosmeticType.HEAD, "ear_1");

        HARENGON.addCosmetic("black", "player_cosmetic/harengon/ears/harengon_ears_black", RaceCosmeticType.HEAD, "ear_0");
        HARENGON.addCosmetic("black", "player_cosmetic/harengon/ears/floppy/harengon_ears_floopy_black", RaceCosmeticType.HEAD, "ear_1");

        HARENGON.addCosmetic("gold", "player_cosmetic/harengon/ears/harengon_ears_gold", RaceCosmeticType.HEAD, "ear_0");
        HARENGON.addCosmetic("gold", "player_cosmetic/harengon/ears/floppy/harengon_ears_floopy_gold", RaceCosmeticType.HEAD, "ear_1");

        HARENGON.addCosmetic("salt", "player_cosmetic/harengon/ears/harengon_ears_salt", RaceCosmeticType.HEAD, "ear_0");
        HARENGON.addCosmetic("salt", "player_cosmetic/harengon/ears/floppy/harengon_ears_floopy_salt", RaceCosmeticType.HEAD, "ear_1");

        HARENGON.addCosmetic("toast", "player_cosmetic/harengon/ears/harengon_ears_toast", RaceCosmeticType.HEAD, "ear_0");
        HARENGON.addCosmetic("toast", "player_cosmetic/harengon/ears/floppy/harengon_ears_floopy_toast", RaceCosmeticType.HEAD, "ear_1");

        HARENGON.addCosmetic("white", "player_cosmetic/harengon/ears/harengon_ears_white", RaceCosmeticType.HEAD, "ear_0");
        HARENGON.addCosmetic("white", "player_cosmetic/harengon/ears/floppy/harengon_ears_floopy_white", RaceCosmeticType.HEAD, "ear_1");

        HARENGON.addCosmetic("white_splotched", "player_cosmetic/harengon/ears/harengon_ears_white_splotched", RaceCosmeticType.HEAD, "ear_0");
        HARENGON.addCosmetic("white_splotched", "player_cosmetic/harengon/ears/floppy/harengon_ears_floopy_white_splotched", RaceCosmeticType.HEAD, "ear_1");

        //TAIL
        HARENGON.addCosmetic("brown", "player_cosmetic/harengon/tail/harengon_tail_brown", RaceCosmeticType.CHEST, "tail_0");
        HARENGON.addCosmetic("black", "player_cosmetic/harengon/tail/harengon_tail_black", RaceCosmeticType.CHEST, "tail_0");
        HARENGON.addCosmetic("gold", "player_cosmetic/harengon/tail/harengon_tail_gold", RaceCosmeticType.CHEST, "tail_0");
        HARENGON.addCosmetic("salt", "player_cosmetic/harengon/tail/harengon_tail_salt", RaceCosmeticType.CHEST, "tail_0");
        HARENGON.addCosmetic("toast", "player_cosmetic/harengon/tail/harengon_tail_toast", RaceCosmeticType.CHEST, "tail_0");
        HARENGON.addCosmetic("white", "player_cosmetic/harengon/tail/harengon_tail_white", RaceCosmeticType.CHEST, "tail_0");
        HARENGON.addCosmetic("white_splotched", "player_cosmetic/harengon/tail/harengon_tail_white_splotched", RaceCosmeticType.CHEST, "tail_0");

        /*
        * ELF
        * */
        ELF.addCosmetic("pale", "player_cosmetic/elf/ears/elf_ears_0_pale", RaceCosmeticType.HEAD, "ear_0");
        ELF.addCosmetic("pale", "player_cosmetic/elf/ears/elf_ears_1_pale", RaceCosmeticType.HEAD, "ear_1");
        ELF.addCosmetic("pale", "player_cosmetic/elf/ears/elf_ears_2_pale", RaceCosmeticType.HEAD, "ear_2");
        ELF.addCosmetic("pale", "player_cosmetic/elf/ears/elf_ears_3_pale", RaceCosmeticType.HEAD, "ear_3");
        ELF.addCosmetic("pale", "player_cosmetic/elf/ears/elf_ears_4_pale", RaceCosmeticType.HEAD, "ear_4");


    }

}
