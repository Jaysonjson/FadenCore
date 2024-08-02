package net.fuchsia.common.init;

import net.fuchsia.common.objects.race.Race;
import net.fuchsia.common.objects.race.types.*;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class FadenRaces {

    private static HashMap<Identifier, Race> RACES = new HashMap<>();

    public static Race HUMAN = register(new HumanRace());
    public static Race HARENGON = register(new HarengonRace());
    public static Race ELF = register(new ElfRace());
    public static Race TABAXI = register(new TabaxiRace());
    public static Race LOCATHAH = register(new LocathahRace());
    public static Race DWARF = register(new DwarfRace());

    private static Race register(Race race) {
        RACES.put(Identifier.of(race.getId()), race);
        return RACES.get(Identifier.of(race.getId()));
    }

    public static HashMap<Identifier, Race> getRegistry() {
        return RACES;
    }

    @Nullable
    public static Race getRace(Identifier id) {
        return RACES.getOrDefault(id, null);
    }


    @Nullable
    public static Race getRace(String id) {
        return getRace(Identifier.of(id));
    }
}
