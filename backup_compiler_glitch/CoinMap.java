package net.fuchsia.common.objects;

import java.util.Collections;
import java.util.TreeMap;

import net.fuchsia.common.init.FadenItems;
import net.fuchsia.common.objects.item.CoinItem;
import net.fuchsia.datagen.FadenDataItem;
import net.minecraft.item.Item;

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
