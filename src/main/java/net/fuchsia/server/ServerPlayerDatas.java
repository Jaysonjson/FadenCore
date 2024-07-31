package net.fuchsia.server;

import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.UUID;

import net.fuchsia.util.NetworkUtils;
import org.apache.commons.io.FileUtils;

import com.google.gson.reflect.TypeToken;

import net.fabricmc.loader.api.FabricLoader;
import net.fuchsia.Faden;
import net.fuchsia.network.FadenNetwork;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ServerPlayerDatas {

    private static HashMap<UUID, PlayerData> playerDatas = new HashMap<>();
    public static MinecraftServer SERVER = null;

    public static void save() {
        for (UUID uuid : getPlayerDatas().keySet()) {
            try {
                FileUtils.writeStringToFile(new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/" + Faden.MC_VERSION + "/player_datas/" + uuid + ".json"), Faden.GSON.toJson(getPlayerDatas().get(uuid)), StandardCharsets.UTF_8);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void save(UUID uuid) {
        if(playerDatas.containsKey(uuid)) {
            try {
                FileUtils.writeStringToFile(new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/" + Faden.MC_VERSION + "/player_datas/" + uuid + ".json"), Faden.GSON.toJson(getPlayerDatas().get(uuid)), StandardCharsets.UTF_8);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static PlayerData load(UUID uuid) {
        File dataFile = new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/" + Faden.MC_VERSION + "/player_datas/" + uuid.toString() + ".json");
        if(dataFile.exists()) {
            try {
                return Faden.GSON.fromJson(new FileReader(dataFile), PlayerData.class);
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
        }
        data.setUuid(uuid);
        return data;
    }

    public static HashMap<UUID, PlayerData> getPlayerDatas() {
        return playerDatas;
    }
}
