package net.fuchsia.common.cape;

import com.google.gson.reflect.TypeToken;
import net.fuchsia.Faden;
import net.fuchsia.config.FadenOptions;
import net.fuchsia.server.PlayerData;
import net.fuchsia.server.client.ClientPlayerDatas;
import net.fuchsia.util.FadenOnlineUtil;
import net.minecraft.text.Text;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class FadenCapes {

    private static ArrayList<FadenCape> CAPES = new ArrayList<>();
    private static HashMap<UUID, ArrayList<String>> PLAYER_CAPES = new HashMap<>();

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
            //JUST SO THE GAME DOESNT CRASH
            try {
                PLAYER_CAPES = Faden.GSON.fromJson(FadenOnlineUtil.getJSONData("https://raw.githubusercontent.com/FuchsiaTeam/FadenData/main/capes.json"), new TypeToken<HashMap<UUID, ArrayList<String>>>() {
                }.getType());
            } catch (Exception e) {

            }
        }
    }
}
