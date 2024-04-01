package json.jayson.common.objects;

import json.jayson.common.init.FadenItems;
import json.jayson.common.objects.item.CoinItem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.*;

public class CoinMap {

    public static LinkedHashMap<Integer, Item> COINS = new LinkedHashMap<>();


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

    /*
    * This is so stupid, but I couldnt care to make an actual remove currency
    * */
    public static void removeCurrency(Inventory inventory, int amount, boolean order) {
        if(order) {
            int currentAmount = countCurrency(inventory);
            for (int i = 0; i < inventory.size(); i++) {
                if (inventory.getStack(i).getItem() instanceof CoinItem) {
                    inventory.setStack(i, Items.AIR.getDefaultStack());
                }
            }
            addCurrency(inventory, currentAmount - amount);
        } else {
            //DO THE UNORDERED PART HERE LATER
        }
    }


    public static void addCurrency(Inventory inventory, int amount) {
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
            if(inventory instanceof PlayerInventory playerInventory) {
                playerInventory.insertStack(itemStack);
            } else {
                for (int i = 0; i < inventory.size(); i++) {
                    if (inventory.getStack(i) == null || inventory.getStack(i).getItem() == Items.AIR) {
                        inventory.setStack(i, itemStack);
                    }
                }
            }
        }
    }

    public static int countCurrency(Inventory inventory) {
        int amount = 0;
        for (Item value : COINS.values()) {
            amount += inventory.count(value) * getCoinValue(value);
        }
        return amount;
    }

    public static Integer getCoinValue(Item value) {
        for (Map.Entry<Integer, Item> entry : COINS.entrySet()) {
                if(entry.getValue().equals(value)) {
                    return entry.getKey();
                }
        }
        return 0;
    }
}
