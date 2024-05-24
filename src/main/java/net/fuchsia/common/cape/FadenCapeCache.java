package net.fuchsia.common.cape;

import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import com.google.gson.reflect.TypeToken;

import net.fabricmc.loader.api.FabricLoader;
import net.fuchsia.Faden;

public class FadenCapeCache {

    private static final Path CACHE_PATH = new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/" + Faden.MC_VERSION + "/capes.json").toPath();


    public static void load() {
        String json = "{}";
        if(CACHE_PATH.toFile().exists()) {
            try {
                json = Files.readString(CACHE_PATH);
                FadenCapes.setPlayerCapes(Faden.GSON.fromJson(json, new TypeToken<HashMap<UUID, ArrayList<String>>>(){}.getType()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            FadenCapes.setPlayerCapes(new HashMap<>());
        }
    }

    public static void save() {
        try {
            Faden.GSON.toJson(FadenCapes.getPlayerCapes(), new FileWriter(CACHE_PATH.toFile()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
