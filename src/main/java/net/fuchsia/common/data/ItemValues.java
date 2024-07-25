package net.fuchsia.common.data;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.UUID;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import net.fabricmc.loader.api.FabricLoader;
import net.fuchsia.Faden;
import net.fuchsia.server.PlayerData;
import net.fuchsia.util.FadenOnlineUtil;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.apache.commons.io.FileUtils;

public class ItemValues {

    public static HashMap<Item, Integer> VALUES = new HashMap<>();

    /*
        HashMap<String, Integer> map = new HashMap<>();
        String json = FadenOnlineUtil.getJSONDataOrCache("https://raw.githubusercontent.com/FuchsiaTeam/FadenData/main/item_values.json", CACHE_PATH.toFile(), Faden.CHECKSUMS.item_values);
        map = Faden.GSON.fromJson(json, new TypeToken<HashMap<String, Integer>>(){}.getType());
        reload(map, json);
     */

    public static void load() {
        try {
            HashMap<String, Integer> map  = Faden.GSON.fromJson(new FileReader(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/" + Faden.MC_VERSION + "/item_values.json"), new TypeToken<HashMap<UUID, PlayerData>>() {}.getType());
            reload(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/" + Faden.MC_VERSION + "/").mkdirs();
        try {
            HashMap<String, Integer> map = new HashMap<>();
            for (Item item : VALUES.keySet()) {
                map.put(Registries.ITEM.getId(item).toString(), VALUES.get(item));
            }
            FileUtils.writeStringToFile(new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/" + Faden.MC_VERSION + "/item_values.json"), Faden.GSON.toJson(map), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void reload(HashMap<String, Integer> map) {
        VALUES.clear();
        for (String s : map.keySet()) {
            Identifier id = Identifier.of(s);
            if(Registries.ITEM.containsId(id)) {
                Item item = Registries.ITEM.get(id);
                VALUES.put(item, map.get(s));
            }
        }
    }
}


