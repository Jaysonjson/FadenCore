package net.fuchsia.race.skin.client;

import net.fuchsia.race.RaceSkinMap;
import net.fuchsia.race.skin.provider.SkinProvider;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.UUID;

public class ClientRaceSkinCache {
    private static HashMap<UUID, Identifier> PLAYER_SKINS = new HashMap<>();
    private static HashMap<String, Identifier> SKINS = new HashMap<>();

    public static void add() {
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
    }

    public static HashMap<UUID, Identifier> getPlayerSkins() {
        return PLAYER_SKINS;
    }
}
