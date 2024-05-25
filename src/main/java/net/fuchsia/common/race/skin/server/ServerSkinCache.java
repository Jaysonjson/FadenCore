package net.fuchsia.common.race.skin.server;

import java.util.HashMap;
import java.util.UUID;

public class ServerSkinCache {

    private static final HashMap<UUID, String> PLAYER_SKINS = new HashMap<>();

    public static HashMap<UUID, String> getPlayerSkins() {
        return PLAYER_SKINS;
    }

    public static void setSkin(UUID playerUuid, String skinId) {
        getPlayerSkins().put(playerUuid, skinId);
    }

    public static void removeSkin(UUID playerUuid) {
        getPlayerSkins().remove(playerUuid);
    }

    public static boolean hasSkin(UUID playerUuid) {
        return getPlayerSkins().containsKey(playerUuid);
    }
}
