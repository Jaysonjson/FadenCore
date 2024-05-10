package net.fuchsia.common.data;

import java.io.*;
import java.nio.file.Path;
import java.util.HashMap;

import com.google.gson.reflect.TypeToken;

import net.fabricmc.loader.api.FabricLoader;
import net.fuchsia.Faden;
import net.fuchsia.common.cape.FadenCapes;
import net.fuchsia.common.cape.online.OnlineCape;
import net.fuchsia.common.cape.online.OnlineCapeCache;
import net.fuchsia.util.FadenCheckSum;
import net.fuchsia.util.FadenOnlineUtil;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class ItemValues {

    public static HashMap<Item, Integer> VALUES = new HashMap<>();
    private static final Path CACHE_PATH = new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/client/item_values.json").toPath();


    public static void add() {
        HashMap<String, Integer> map = new HashMap<>();
        String json = "{}";
        try {
            File cache = CACHE_PATH.toFile();
            if(!cache.exists()) {
                //USE GITHUB JSON INSTEAD OF GSON, SO THE CHECKSUM MATCHES
                json = FadenOnlineUtil.getJSONData("https://raw.githubusercontent.com/FuchsiaTeam/FadenData/main/item_values.json");
                map = Faden.GSON.fromJson(json, new TypeToken<HashMap<String, Integer>>(){}.getType());
            } else {
                InputStream inputStream = new FileInputStream(cache);
                map = Faden.GSON.fromJson(new FileReader(cache), new TypeToken<HashMap<String, Integer>>(){}.getType());
                if(!FadenCheckSum.checkSum(inputStream).equals(Faden.CHECKSUMS.item_values)) {
                    Faden.LOGGER.warn("Mismatched Checksum for " + cache + " - retrieving data again");
                    json = FadenOnlineUtil.getJSONData("https://raw.githubusercontent.com/FuchsiaTeam/FadenData/main/item_values.json");
                    map = Faden.GSON.fromJson(json, new TypeToken<HashMap<String, Integer>>(){}.getType());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        reload(map, json);
    }

    public static void reload(HashMap<String, Integer> map, String json) {
        VALUES.clear();
        for (String s : map.keySet()) {
            Identifier id = new Identifier(s);
            if(Registries.ITEM.containsId(id)) {
                Item item = Registries.ITEM.get(id);
                VALUES.put(item, map.get(s));
            }
        }
        try {
            FileWriter writer = new FileWriter(CACHE_PATH.toFile());
            writer.write(json);
            writer.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}


