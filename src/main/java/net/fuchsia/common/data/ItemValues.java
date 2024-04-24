package net.fuchsia.common.data;

import com.google.gson.reflect.TypeToken;
import net.fuchsia.Faden;
import net.fuchsia.util.FadenOnlineUtil;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.HashMap;
import java.util.UUID;

public class ItemValues {

    //TODO: PACK THIS INTO JSON DATA
    public static HashMap<Item, Integer> VALUES = new HashMap<>();


    /*
    * TEMPORARY UNTIL JSON DATA IS DONE, BUT TBH, WE DONT NEED TO EXPECT ADDONS, SO WE COULD KJUST KEEP IT HERE
    * */
    public static void add() {
        VALUES.clear();
        HashMap<String, Integer> map =  Faden.GSON.fromJson(FadenOnlineUtil.getJSONData("https://raw.githubusercontent.com/FuchsiaTeam/FadenData/main/item_values.json"), new TypeToken<HashMap<String, Integer>>(){}.getType());
        for (String s : map.keySet()) {
            Identifier id = new Identifier(s);
            if(Registries.ITEM.containsId(id)) {
                Item item = Registries.ITEM.get(id);
                VALUES.put(item, map.get(s));
            }
        }
    }
}


