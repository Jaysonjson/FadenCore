package net.fuchsia.common.race.skin.client;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.UUID;

import net.fuchsia.Faden;
import net.fuchsia.config.FadenConfig;
import net.fuchsia.common.race.RaceSkinMap;
import net.fuchsia.common.race.skin.provider.SkinProvider;
import net.fuchsia.config.FadenOptions;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

public class ClientRaceSkinCache {
    private static HashMap<UUID, Identifier> PLAYER_SKINS = new HashMap<>();
    private static HashMap<String, Identifier> SKINS = new HashMap<>();
    private static boolean added = false;
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
}
