package json.jayson.common.objects;

import json.jayson.common.init.FadenItems;
import json.jayson.common.objects.item.CoinItem;
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
        }
        else {
            for (int i = 0; i < inventory.size(); i++) {
                if (inventory.getStack(i).getItem() instanceof CoinItem coinItem) {
                    ItemStack itemStack = inventory.getStack(i);
                    int value = coinItem.value;
                    int count = itemStack.getCount();
                    int total = value * count;

                    if(amount > total){
                        inventory.setStack(i, Items.AIR.getDefaultStack());
                        amount -= total;
                    }
                    else if(amount == total){
                        inventory.setStack(i, Items.AIR.getDefaultStack());
                        break;
                    }
                    else{
                        int take = Math.floorDiv(amount, value);
                        int refund = amount % value;
                        itemStack.setCount(itemStack.getCount()-(take+1));
                        inventory.setStack(i, itemStack);
                        // One more coin is taken than is calculated, the line below gives back the change.
                        // If not run the player will overpay :P
                        addCurrency(inventory, refund);
                        break;
                    }
                }
            }
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
        boolean drop = false;
        for (Item item : itemStacks.keySet()) {
            ItemStack itemStack = item.getDefaultStack();
            itemStack.setCount(itemStacks.get(item));
            if(inventory instanceof PlayerInventory playerInventory) {
                //TODO: CHECK IF PLAYER INVENTORY IS FULL AND THEN SET DROP TO TRUE
                playerInventory.insertStack(itemStack);
            } else {
                for (int i = 0; i < inventory.size(); i++) {
                    if(i >= inventory.size()) {
                        drop = true;
                    }
                    if (inventory.getStack(i) == null || inventory.getStack(i).getItem() == Items.AIR) {
                        inventory.setStack(i, itemStack);
                        break;
                    }
                }
            }

            if(drop) {
                //TODO DROP CODE
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

    public static LinkedHashMap<Item, Integer> countCoins(Inventory inventory){
        LinkedHashMap<Item, Integer> coinCounts = new LinkedHashMap<>();
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack itemStack = inventory.getStack(i);
            Item item = itemStack.getItem();
            if (item instanceof CoinItem) {
                if(coinCounts.containsKey(item)){
                    coinCounts.put(item, coinCounts.get(item)+itemStack.getCount());
                }
                else{
                    coinCounts.put(item, itemStack.getCount());
                }
            }
        }
        return coinCounts;
    }
}
