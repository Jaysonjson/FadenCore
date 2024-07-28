package net.fuchsia.common.objects.race.skin.client;

import java.util.HashMap;
import java.util.UUID;

import net.fuchsia.common.objects.race.RaceSkinMap;
import net.fuchsia.common.objects.race.skin.provider.SkinProvider;
import net.fuchsia.config.FadenOptions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

public class ClientRaceSkinCache {
    private static HashMap<UUID, Identifier> PLAYER_SKINS = new HashMap<>();
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

    public static HashMap<UUID, Identifier> getPlayerSkins() {
        return PLAYER_SKINS;
    }

    public static Identifier getSkin(UUID playerUuid) {
        return getPlayerSkins().getOrDefault(playerUuid, Identifier.of("missing"));
    }

    public static HashMap<String, Identifier> getSKINS() {
        return SKINS;
    }

    public static boolean hasSkin(UUID playerUuid) {
        return getPlayerSkins().containsKey(playerUuid);
    }

    public static void removeSkin(UUID playerUuid) {
        getPlayerSkins().remove(playerUuid);
    }

    public static void setSkin(UUID playerUuid, Identifier skinId) {
        getPlayerSkins().put(playerUuid, skinId);
    }
}
