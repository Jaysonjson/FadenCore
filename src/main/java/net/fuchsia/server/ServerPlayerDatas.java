package net.fuchsia.server;

import com.google.gson.reflect.TypeToken;
import net.fabricmc.loader.api.FabricLoader;
import net.fuchsia.Faden;
import net.fuchsia.common.quest.data.PlayerQuests;
import net.fuchsia.network.FadenNetwork;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtSizeTracker;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.UUID;

public class ServerPlayerDatas {

    private static HashMap<UUID, PlayerData> playerDatas = new HashMap<>();
    public static MinecraftServer SERVER = null;

    public static void load() {
        try {
            playerDatas = Faden.GSON.fromJson(new FileReader(new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/" + Faden.MC_VERSION + "/player_data.json")), new TypeToken<HashMap<UUID, PlayerData>>() {}.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/" + Faden.MC_VERSION + "/").mkdirs();
        try {
            FileUtils.writeStringToFile(new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/" + Faden.MC_VERSION + "/player_data.json"), Faden.GSON.toJson(getPlayerDatas()), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static HashMap<UUID, PlayerData> getPlayerDatas() {
        return playerDatas;
    }

    public static void sync(UUID playerUuid, PlayerData playerData) {
        if(SERVER != null) {
            for (ServerPlayerEntity player : SERVER.getPlayerManager().getPlayerList()) {
                FadenNetwork.Server.syncPlayerData(player, playerUuid, playerData);
            }
        }
    }
}
