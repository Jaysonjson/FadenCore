package net.fuchsia.common.cape;

import com.google.gson.reflect.TypeToken;
import net.fuchsia.Faden;
import net.fuchsia.util.FadenOnlineUtil;

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

    private static FadenCape register(String id) {
        FadenCape cape = new FadenCape(id);
        CAPES.add(cape);
        return cape;
    }

    public static ArrayList<FadenCape> getCapes() {
        return CAPES;
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
    }
}
