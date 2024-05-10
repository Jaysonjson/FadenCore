package net.fuchsia.common.data;

import java.util.HashMap;

import com.google.gson.reflect.TypeToken;

import net.fuchsia.Faden;
import net.fuchsia.util.FadenOnlineUtil;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class ItemValues {

    public static HashMap<Item, Integer> VALUES = new HashMap<>();

    public static void add() {
        HashMap<String, Integer> map = Faden.GSON.fromJson(FadenOnlineUtil.getJSONData("https://raw.githubusercontent.com/FuchsiaTeam/FadenData/main/item_values.json"), new TypeToken<HashMap<String, Integer>>(){}.getType());
        reload(map);
    }

    public static void reload(HashMap<String, Integer> map) {
        VALUES.clear();
        for (String s : map.keySet()) {
            Identifier id = new Identifier(s);
            if(Registries.ITEM.containsId(id)) {
                Item item = Registries.ITEM.get(id);
                VALUES.put(item, map.get(s));
            }
        }
    }
}


