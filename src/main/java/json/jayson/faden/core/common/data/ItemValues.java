package json.jayson.faden.core.common.data;

import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import com.google.gson.reflect.TypeToken;

import json.jayson.faden.core.util.SaveUtil;
import net.fabricmc.loader.api.FabricLoader;
import json.jayson.faden.core.FadenCore;
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
        if(!FadenCore.MODULES.itemValues) return;
        if(!new File(SaveUtil.getCurrentSaveFull() + "/item_values.json").exists()) {
            save();
        }
        try {
            HashMap<String, Integer> map  = FadenCore.GSON.fromJson(new FileReader(SaveUtil.getCurrentSaveFull() + "/item_values.json"), new TypeToken<HashMap<String, Integer>>() {}.getType());
            reload(map);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void save() {
        if(!FadenCore.MODULES.itemValues) return;
        try {
            HashMap<String, Integer> map = new HashMap<>();
            for (Item item : VALUES.keySet()) {
                map.put(Registries.ITEM.getId(item).toString(), VALUES.get(item));
            }
            FileUtils.writeStringToFile(new File(SaveUtil.getCurrentSaveFull() + "/item_values.json"), FadenCore.GSON.toJson(map), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void saveClient() {
        if(!FadenCore.MODULES.itemValues) return;
        try {
            HashMap<String, Integer> map = new HashMap<>();
            for (Item item : ItemValues.VALUES.keySet()) {
                map.put(Registries.ITEM.getId(item).toString(), ItemValues.VALUES.get(item));
            }
            FileUtils.writeStringToFile(new File(SaveUtil.getCurrentSaveFullClient() + "/item_values.json"), FadenCore.GSON.toJson(map), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void reload(HashMap<String, Integer> map) {
        VALUES.clear();
        if(!FadenCore.MODULES.itemValues) return;
        for (String s : map.keySet()) {
            Identifier id = Identifier.of(s);
            if(Registries.ITEM.containsId(id)) {
                Item item = Registries.ITEM.get(id);
                VALUES.put(item, map.get(s));
            }
        }
    }
}


