package net.fuchsia.common.race.data;

import net.fuchsia.common.race.IRace;
import net.fuchsia.common.race.Race;

import java.util.HashMap;
import java.util.UUID;

public class ClientRaceCache {

    private static HashMap<UUID, RaceData> CACHE = new HashMap<>();

    public static HashMap<UUID, RaceData> getCache() {
        return CACHE;
    }

    public static RaceData get(UUID uuid) {
        return getCache().getOrDefault(uuid, new RaceData(null, ""));
    }
}
