package json.jayson.faden.core.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import json.jayson.faden.core.server.PlayerData;
import json.jayson.faden.core.server.ServerPlayerDatas;
import json.jayson.faden.core.server.client.ClientPlayerDatas;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class PlayerDataUtil {

    public static @NotNull PlayerData getClientOrServer(UUID uuid) {
        PlayerData data = ClientPlayerDatas.getPlayerData(uuid);
        if(FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) {
            data = ServerPlayerDatas.getOrLoadPlayerData(uuid);
        }
        return data;
    }

}
