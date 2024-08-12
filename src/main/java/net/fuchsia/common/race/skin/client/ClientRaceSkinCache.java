package net.fuchsia.common.race.skin.client;

import java.util.HashMap;
import java.util.UUID;

import net.fuchsia.common.race.RaceSkinMap;
import net.fuchsia.common.race.skin.provider.SkinProvider;
import net.fuchsia.config.FadenOptions;
import net.fuchsia.server.PlayerData;
import net.fuchsia.server.client.ClientPlayerDatas;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

public class ClientRaceSkinCache {
    private static HashMap<String, Identifier> SKINS = new HashMap<>();
    private static boolean added = false;


    /*
     * I keep it with the added bool for now, and just put it into client world load,
     * TODO Remove added boolean and hook this into the TextureLoader instead
     *  ~Jayson
     * */
    public static void add() {
        if(!added && FadenOptions.getConfig().ENABLE_PLAYER_RACE_SKINS) {
            HashMap<String, byte[]> maps = RaceSkinMap.getAllMaps();
            for (String s : maps.keySet()) {
                byte[] data = maps.get(s);
                Identifier id = SkinProvider.getSkinIdentifier(s);
                SkinTexture skinTexture = new SkinTexture(id);
                skinTexture.setSkinData(data);
                MinecraftClient.getInstance().getTextureManager().registerTexture(id, skinTexture);
                MinecraftClient.getInstance().getTextureManager().bindTexture(id);
                SKINS.put(s, id);
            }
            added = true;
        }
    }

    public static Identifier getSkin(UUID playerUuid) {
        PlayerData data = ClientPlayerDatas.getPlayerData(playerUuid);
        if(data != null) {
            return Identifier.of(data.getRaceSaveData().getSkin());
        }
        return Identifier.of("missing");
    }

    public static HashMap<String, Identifier> getSkins() {
        return SKINS;
    }

    public static boolean hasSkin(UUID playerUuid) {
        PlayerData data = ClientPlayerDatas.getPlayerData(playerUuid);
        if(data != null) {
            return !data.getRaceSaveData().getSkin().isEmpty();
        }
        return false;
    }
}
