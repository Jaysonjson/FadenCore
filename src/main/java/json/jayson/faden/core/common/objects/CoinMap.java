package json.jayson.faden.core.common.objects;

import java.util.Collections;
import java.util.TreeMap;

import json.jayson.faden.core.common.objects.item.coin.CoinItem;
import json.jayson.faden.core.datagen.FadenCoreDataGen;
import json.jayson.faden.core.datagen.holders.FadenDataItem;
import net.minecraft.item.Item;

public class CoinMap {

	private static TreeMap<Integer, Item> COINS = new TreeMap<>(Collections.reverseOrder());

	public static void reloadCoins() {
		COINS.clear();
		for (FadenDataItem item : FadenCoreDataGen.ITEMS) {
			if(item.item() instanceof CoinItem coinItem) {
				COINS.put(coinItem.getValue(), item.item());
			}
		}
	}

	public static TreeMap<Integer, Item> getCoinMap() {
		return COINS;
	}

	public static Item getLowestCoin() {
		return getCoinMap().get(0);
	}


}
