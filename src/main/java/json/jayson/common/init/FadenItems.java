package json.jayson.common.init;

import json.jayson.common.objects.item.CoinItem;
import json.jayson.datagen.DataItemModel;
import json.jayson.datagen.FadenDataItem;
import json.jayson.util.FadenIdentifier;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.BundleItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class FadenItems {

    public static List<FadenDataItem> ITEMS = new ArrayList<>();

    public static Item COPPER_COIN = registerItem("copper_coin", new Item(new FabricItemSettings()), "coins/copper_coin");
    public static Item IRON_COIN = registerItem("iron_coin", new Item(new FabricItemSettings()), "coins/iron_coin");
    public static Item SILVER_COIN = registerItem("silver_coin", new Item(new FabricItemSettings()), "coins/silver_coin");
    public static Item GOLD_COIN = registerItem("gold_coin", new Item(new FabricItemSettings()), "coins/gold_coin");
    public static Item AMETHYST_COIN = registerItem("amethyst_coin", new Item(new FabricItemSettings()), "coins/amethyst_coin");
    public static Item SILVER_INGOT = registerItem("silver_ingot", new Item(new FabricItemSettings()), "ingots/silver_ingot");
    public static Item RANDOM_COIN_GIVER = registerItem("random_coin_giver", new CoinItem(new FabricItemSettings()), "");

    private static Item registerItem(String name, Item item, String texture, DataItemModel itemModel) {
        Item i = Registry.register(Registries.ITEM, FadenIdentifier.create(name), item);
        ITEMS.add(new FadenDataItem(i, texture, itemModel));
        return i;
    }

    private static Item registerItem(String name, Item item, String texture) {
        return registerItem(name, item, texture, DataItemModel.GENERATED);
    }

    public static void register() {}

}
