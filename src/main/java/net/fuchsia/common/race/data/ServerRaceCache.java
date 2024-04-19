package net.fuchsia.common.race.data;

import java.util.HashMap;
import java.util.UUID;

public class ServerRaceCache {

    private static HashMap<UUID, RaceData> CACHE = new HashMap<>();

    public static HashMap<UUID, RaceData> getCache() {
        return CACHE;
    }
}
