package json.jayson.faden.core.common.race.skin.client;

import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import json.jayson.faden.core.common.race.RaceSkinMap;
import json.jayson.faden.core.config.FadenCoreOptions;
import json.jayson.faden.core.server.PlayerData;
import json.jayson.faden.core.server.client.ClientPlayerDatas;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

public class ClientRaceSkinCache {
    private static ConcurrentHashMap<String, Identifier> SKINS = new ConcurrentHashMap<>();
    private static boolean added = false;

    /*
     * I keep it with the added bool for now, and just put it into client world load,
     * TODO Remove added boolean and hook this into the TextureLoader instead
     *  ~Jayson
     * */
    public static void add() {
        if (!added && FadenCoreOptions.getConfig().ENABLE_PLAYER_RACE_SKINS) {
            HashMap<Identifier, byte[]> maps = RaceSkinMap.getAllMaps();
            for (Identifier s : maps.keySet()) {
                byte[] data = maps.get(s);
                SkinTexture skinTexture = new SkinTexture(s);
                skinTexture.setSkinData(data);
                MinecraftClient.getInstance().getTextureManager().registerTexture(s, skinTexture);
                MinecraftClient.getInstance().getTextureManager().bindTexture(s);
                SKINS.put(s.toString(), s);
            }
            added = true;
        }
    }

    public static Identifier getSkin(UUID playerUuid) {
        PlayerData data = ClientPlayerDatas.getPlayerData(playerUuid);
        return Identifier.of(data.getRaceSaveData().getSkin());
    }

    public static ConcurrentHashMap<String, Identifier> getSkins() {
        return SKINS;
    }

    public static boolean hasSkin(UUID playerUuid) {
        PlayerData data = ClientPlayerDatas.getPlayerData(playerUuid);
        if (data != null) {
            return !data.getRaceSaveData().getSkin().isEmpty();
        }
        return false;
    }
}
