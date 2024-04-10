package net.fuchsia.common.data;

import net.minecraft.item.Item;
import net.minecraft.item.Items;

import java.util.HashMap;

public class ItemValues {

    //TODO: PACK THIS INTO JSON DATA
    public static HashMap<Item, Integer> VALUES = new HashMap<>();


    /*
    * TEMPORARY UNTIL JSON DATA IS DONE, BUT TBH, WE DONT NEED TO EXPECT ADDONS, SO WE COULD KJUST KEEP IT HERE
    * */
    public static void add() {
        VALUES.put(Items.DIAMOND, 750);
    }
}


