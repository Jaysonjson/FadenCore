package net.fuchsia.common.cape;

import java.io.File;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import net.fabricmc.loader.api.FabricLoader;
import net.fuchsia.config.FadenOptions;
import net.fuchsia.server.PlayerData;
import net.fuchsia.server.client.ClientPlayerDatas;
import net.fuchsia.util.PlayerDataUtil;
import net.minecraft.text.Text;
import org.jetbrains.annotations.Nullable;

public class FadenCapes {

    private static ArrayList<FadenCape> CAPES = new ArrayList<>();

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
    public static final FadenCape BLACKJACK = register("blackjack");

    private static FadenCape register(String id) {
        FadenCape cape = new FadenCape(id, Text.translatable("cape.faden." + id), Text.translatable("cape.faden." + id + ".description"));
        CAPES.add(cape);
        return cape;
    }

    public static FadenCape registerOnlineCape(String id) {
        FadenCape cape = new FadenCape(id, Text.literal(id), Text.literal(id));
        CAPES.add(cape);
        return cape;
    }

    public static ArrayList<FadenCape> getCapes() {
        return CAPES;
    }


    @Nullable
    public static FadenCape getCapeForPlayer(UUID uuid) {
        PlayerData data = PlayerDataUtil.getClientOrServer(uuid);
        if(data != null) {
            return data.getSelectedCape();
        }
        return null;
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
        PlayerData data = PlayerDataUtil.getClientOrServer(uuid);
        return data != null && !data.getSelectedCapeId().isEmpty();
    }

    @Deprecated
    public static void register() {
        if(FadenOptions.getConfig().CUSTOM_CAPES) {
            //PLAYER_CAPES = Faden.GSON.fromJson(FadenOnlineUtil.getJSONDataOrCache("https://raw.githubusercontent.com/FuchsiaTeam/FadenData/main/capes.json", CACHE_PATH.toFile(), Faden.CHECKSUMS.capes), new TypeToken<HashMap<UUID, ArrayList<String>>>(){}.getType());
        }
    }
}
