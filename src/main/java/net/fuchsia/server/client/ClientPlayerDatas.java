package net.fuchsia.server.client;

import net.fuchsia.server.PlayerData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.UUID;

public class ClientPlayerDatas {

    private static HashMap<UUID, PlayerData> playerDatas = new HashMap<>();


    @NotNull
    public static PlayerData getPlayerData(UUID uuid) {
        return getPlayerDatas().getOrDefault(uuid, new PlayerData());
    }

    public static void setPlayerDatas(HashMap<UUID, PlayerData> playerDatas) {
        ClientPlayerDatas.playerDatas = playerDatas;
    }

    public static HashMap<UUID, PlayerData> getPlayerDatas() {
        if(playerDatas == null) playerDatas = new HashMap<>();
        return playerDatas;
    }
}
