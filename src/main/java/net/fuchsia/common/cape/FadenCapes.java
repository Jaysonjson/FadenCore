package net.fuchsia.common.cape;

import com.google.gson.reflect.TypeToken;
import net.fuchsia.Faden;
import net.fuchsia.util.FadenOnlineUtil;
import net.minecraft.client.MinecraftClient;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class FadenCapes {

    private static ArrayList<FadenCape> CAPES = new ArrayList<>();
    private static HashMap<UUID, String> PLAYER_CAPES = new HashMap<>();

    public static final FadenCape FUCHSIA = register("fuchsia");
    public static final FadenCape DEVELOPER = register("developer");
    public static final FadenCape PIXEL_ARTIST = register("pixel_artist");
    public static final FadenCape MUSIC_ARTIST = register("music_artist");
    public static final FadenCape TRANSLATOR = register("translator");
    public static final FadenCape STAFF = register("staff");
    public static final FadenCape DONATOR = register("donator");
    public static final FadenCape DATA = register("data");

    public static void setPlayerCapes(HashMap<UUID, String> playerCapes) {
        PLAYER_CAPES = playerCapes;
    }

    private static FadenCape register(String id) {
        FadenCape cape = new FadenCape(id);
        CAPES.add(cape);
        return cape;
    }

    public static ArrayList<FadenCape> getCapes() {
        return CAPES;
    }

    public static HashMap<UUID, String> getPlayerCapes() {
        return PLAYER_CAPES;
    }

    public static FadenCape getCapeForPlayer(UUID uuid) {
        String id = PLAYER_CAPES.getOrDefault(uuid, "none");
        for (FadenCape cape : getCapes()) {
            if(cape.getId().equalsIgnoreCase(id)) {
                return cape;
            }
        }
        return null;
    }

    public static boolean playerHasCape(UUID uuid) {
        return PLAYER_CAPES.containsKey(uuid);
    }

    public static void init() {
        PLAYER_CAPES = Faden.GSON.fromJson(FadenOnlineUtil.getJSONData("https://raw.githubusercontent.com/FuchsiaTeam/FadenData/main/capes.json"), new TypeToken<HashMap<UUID, String>>(){}.getType());
        PLAYER_CAPES.put(UUID.fromString("bee2920e-f065-4ae6-b00c-3f2c1ed38031"), MUSIC_ARTIST.getId());
    }
}
