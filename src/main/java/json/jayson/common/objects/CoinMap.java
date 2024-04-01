package json.jayson.common.objects;

import json.jayson.common.init.FadenItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.*;

public class CoinMap {

    public static Map<Integer, Item> COINS = new LinkedHashMap<>();


    public static void addCoins() {
        /*
        * COINS OF MORE VALUE FIRST!
        * */
        COINS.put(500, FadenItems.AMETHYST_COIN);
        COINS.put(100, FadenItems.GOLD_COIN);
        COINS.put(50, FadenItems.SILVER_COIN);
        COINS.put(5, FadenItems.IRON_COIN);
        COINS.put(1, FadenItems.COPPER_COIN);
    }


    public static void addCurrency(PlayerEntity player, int amount) {
        int toAdd = amount;
        Map<Item, Integer> itemStacks = new HashMap<>();
        while(toAdd > 0) {
            for (Integer i : COINS.keySet()) {
                if(i <= toAdd) {
                    itemStacks.put(COINS.get(i), itemStacks.getOrDefault(COINS.get(i), 0) + 1);
                    toAdd -= i;
                    break;
                }
            }
        }
        for (Item item : itemStacks.keySet()) {
            ItemStack itemStack = item.getDefaultStack();
            itemStack.setCount(itemStacks.get(item));
            player.getInventory().insertStack(itemStack);

        }
    }
}
