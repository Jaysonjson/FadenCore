package json.jayson.data;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.HashMap;

public class ItemValues {

    //TODO: PACK THIS INTO JSON DATA
    public static HashMap<Item, Integer> VALUES = new HashMap<>();


    public static void add() {
        VALUES.put(Items.DIAMOND, 750);
    }
}


