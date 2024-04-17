package net.fuchsia.common.objects;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import net.fuchsia.common.init.FadenItems;
import net.fuchsia.common.objects.item.CoinItem;
import net.fuchsia.datagen.FadenDataItem;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class CoinMap {

	private static TreeMap<Integer, Item> COINS = new TreeMap<>(Collections.reverseOrder());

	public static void reloadMap() {
		COINS.clear();
		for (FadenDataItem item : FadenItems.ITEMS) {
			if(item.item() instanceof CoinItem coinItem) {
				COINS.put(coinItem.getValue(), item.item());
			}
		}
	}

	public static TreeMap<Integer, Item> getCoinMap() {
		return COINS;
	}
}
