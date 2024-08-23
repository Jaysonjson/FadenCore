package json.jayson.faden.core.util;

import java.util.UUID;

import json.jayson.faden.core.common.cape.FadenCoreCape;
import json.jayson.faden.core.server.PlayerData;
import org.jetbrains.annotations.Nullable;

public class FadenCoreCapeUtil {

    @Nullable
    public static FadenCoreCape getCapeForPlayer(UUID uuid) {
        PlayerData data = PlayerDataUtil.getClientOrServer(uuid);
        return data.getSelectedCape();
    }

    public static boolean playerHasCape(UUID uuid) {
        PlayerData data = PlayerDataUtil.getClientOrServer(uuid);
        return !data.getSelectedCapeId().isEmpty();
    }

}
