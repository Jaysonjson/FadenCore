package net.fuchsia.common.cape;

import com.google.gson.reflect.TypeToken;
import net.fabricmc.loader.api.FabricLoader;
import net.fuchsia.Faden;
import net.fuchsia.common.cape.online.OnlineCape;
import net.fuchsia.common.cape.online.OnlineCapeCache;
import net.fuchsia.config.FadenOptions;
import net.fuchsia.server.PlayerData;
import net.fuchsia.server.client.ClientPlayerDatas;
import net.fuchsia.util.FadenCheckSum;
import net.fuchsia.util.FadenOnlineUtil;
import net.minecraft.text.Text;

import java.io.*;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class FadenCapes {

    private static ArrayList<FadenCape> CAPES = new ArrayList<>();
    private static HashMap<UUID, ArrayList<String>> PLAYER_CAPES = new HashMap<>();
    private static final Path CACHE_PATH = new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/client/capes.json").toPath();

    public static final FadenCape FUCHSIA = register("fuchsia");
    public static final FadenCape DEVELOPER = register("developer");
    public static final FadenCape PIXEL_ARTIST = register("pixel_artist");
    public static final FadenCape MUSIC_ARTIST = register("music_artist");
    public static final FadenCape TRANSLATOR = register("translator");
    public static final FadenCape STAFF = register("staff");
    public static final FadenCape DONATOR = register("donator");
    public static final FadenCape DATA = register("data");
    public static final FadenCape BUILDER = register("builder");
    public static final FadenCape DT10 = register("dt10");
    public static final FadenCape SAKURA = register("sakura");

    public static void setPlayerCapes(HashMap<UUID, ArrayList<String>> playerCapes) {
        PLAYER_CAPES = playerCapes;
    }

    private static FadenCape register(String id) {
        FadenCape cape = new FadenCape(id, Text.translatable("cape.faden." + id));
        CAPES.add(cape);
        return cape;
    }

    public static FadenCape registerOnlineCape(String id) {
        FadenCape cape = new FadenCape(id, Text.literal(id));
        CAPES.add(cape);
        return cape;
    }

    public static ArrayList<FadenCape> getCapes() {
        return CAPES;
    }

    public static HashMap<UUID, ArrayList<String>> getPlayerCapes() {
        return PLAYER_CAPES;
    }

    public static FadenCape getCapeForPlayer(UUID uuid) {
        return ClientPlayerDatas.getPlayerDatas().getOrDefault(uuid, new PlayerData()).getSelectedCape();
    }

    public static FadenCape getCapeById(String name) {
        for (FadenCape cape : getCapes()) {
            if(cape.getId().equalsIgnoreCase(name)) {
                return cape;
            }
        }
        return null;
    }

    public static boolean playerHasCape(UUID uuid) {
        return PLAYER_CAPES.containsKey(uuid);
    }

    public static void register() {
        if(FadenOptions.getConfig().CUSTOM_CAPES) {
            try {
                File cache = CACHE_PATH.toFile();
                String json = "{}";
                if(!cache.exists()) {
                    //USE GITHUB JSON INSTEAD OF GSON, SO THE CHECKSUM MATCHES
                    json = FadenOnlineUtil.getJSONData("https://raw.githubusercontent.com/FuchsiaTeam/FadenData/main/capes.json");
                    PLAYER_CAPES = Faden.GSON.fromJson(json, new TypeToken<HashMap<UUID, ArrayList<String>>>(){}.getType());
                    FileWriter writer = new FileWriter(cache);
                    writer.write(json);
                    writer.close();
                } else {
                    InputStream inputStream = new FileInputStream(cache);
                    PLAYER_CAPES = Faden.GSON.fromJson(new FileReader(cache), new TypeToken<HashMap<UUID, ArrayList<String>>>(){}.getType());
                    if(!FadenCheckSum.checkSum(inputStream).equals(Faden.CHECKSUMS.capes)) {
                        Faden.LOGGER.warn("Mismatched Checksum for " + cache + " - retrieving data again");
                        json = FadenOnlineUtil.getJSONData("https://raw.githubusercontent.com/FuchsiaTeam/FadenData/main/capes.json");
                        PLAYER_CAPES = Faden.GSON.fromJson(json, new TypeToken<HashMap<UUID, ArrayList<String>>>(){}.getType());
                    }
                    FileWriter writer = new FileWriter(cache);
                    writer.write(json);
                    writer.close();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
