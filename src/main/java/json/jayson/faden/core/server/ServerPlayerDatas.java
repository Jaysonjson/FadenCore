package json.jayson.faden.core.server;

import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.UUID;

import org.apache.commons.io.FileUtils;

import net.fabricmc.loader.api.FabricLoader;
import json.jayson.faden.core.FadenCore;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;

public class ServerPlayerDatas {

    private static HashMap<UUID, PlayerData> playerDatas = new HashMap<>();
    public static MinecraftServer SERVER = null;

    public static void save() {
        for (UUID uuid : getPlayerDatas().keySet()) {
            try {
                FileUtils.writeStringToFile(new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/" + FadenCore.MC_VERSION + "/player_datas/" + uuid + ".json"), FadenCore.GSON.toJson(getPlayerDatas().get(uuid)), StandardCharsets.UTF_8);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void save(UUID uuid) {
        if(playerDatas.containsKey(uuid)) {
            try {
                FileUtils.writeStringToFile(new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/" + FadenCore.MC_VERSION + "/player_datas/" + uuid + ".json"), FadenCore.GSON.toJson(getPlayerDatas().get(uuid)), StandardCharsets.UTF_8);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static PlayerData load(UUID uuid) {
        File dataFile = new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/" + FadenCore.MC_VERSION + "/player_datas/" + uuid.toString() + ".json");
        if(dataFile.exists()) {
            try {
                return FadenCore.GSON.fromJson(new FileReader(dataFile), PlayerData.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    @NotNull
    public static PlayerData getOrLoadPlayerData(UUID uuid) {
        PlayerData data = null;
        if(playerDatas.containsKey(uuid)) {
            data = getPlayerDatas().get(uuid);
        } else {
            data = load(uuid);
            if (data == null) data = new PlayerData();
            getPlayerDatas().put(uuid, data);
        }
        data.setUuid(uuid);
        return data;
    }

    public static void unloadPlayerData(UUID uuid) {
        save(uuid);
        getPlayerDatas().remove(uuid);
    }

    public static HashMap<UUID, PlayerData> getPlayerDatas() {
        return playerDatas;
    }
}
