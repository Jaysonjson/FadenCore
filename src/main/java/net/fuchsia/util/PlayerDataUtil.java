package net.fuchsia.util;

import net.fabricmc.api.EnvType;
import net.fabricmc.loader.api.FabricLoader;
import net.fuchsia.server.PlayerData;
import net.fuchsia.server.ServerPlayerDatas;
import net.fuchsia.server.client.ClientPlayerDatas;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public class PlayerDataUtil {

    @Nullable
    public static PlayerData getClientOrServer(UUID uuid) {
        PlayerData data = ClientPlayerDatas.getPlayerData(uuid);
        if(data == null && FabricLoader.getInstance().getEnvironmentType() == EnvType.SERVER) {
            data = ServerPlayerDatas.getOrLoadPlayerData(uuid);
        }
        return data;
    }

}
