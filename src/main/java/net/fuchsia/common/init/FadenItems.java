package net.fuchsia.common.init;

import net.fuchsia.common.objects.item.CoinItem;
import net.fuchsia.common.objects.item.TestItem;
import net.fuchsia.datagen.DataItemModel;
import net.fuchsia.datagen.FadenDataItem;
import net.fuchsia.util.FadenIdentifier;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class FadenItems {

    public static List<FadenDataItem> ITEMS = new ArrayList<>();

    public static CoinItem COPPER_COIN = registerItem("copper_coin", new CoinItem(new FabricItemSettings(), 1), "coins/copper_coin");
    public static CoinItem IRON_COIN = registerItem("iron_coin", new CoinItem(new FabricItemSettings(), 5), "coins/iron_coin");
    public static CoinItem ENDER_COIN = registerItem("ender_coin", new CoinItem(new FabricItemSettings(), 10), "coins/ender_coin");
    public static CoinItem SILVER_COIN = registerItem("silver_coin", new CoinItem(new FabricItemSettings(), 50), "coins/silver_coin");
    public static CoinItem GOLD_COIN = registerItem("gold_coin", new CoinItem(new FabricItemSettings(), 100), "coins/gold_coin");
    public static CoinItem AMETHYST_COIN = registerItem("amethyst_coin", new CoinItem(new FabricItemSettings(), 500), "coins/amethyst_coin");
    public static CoinItem NETHERITE_COIN = registerItem("netherite_coin", new CoinItem(new FabricItemSettings(), 1000), "coins/netherite_coin");
    public static Item SILVER_INGOT = registerItem("silver_ingot", new Item(new FabricItemSettings()), "ingots/silver_ingot");
    public static Item TEST = registerItem("test_item", new TestItem(new FabricItemSettings()), "ingots/silver_ingot");

    private static <T extends Item> T registerItem(String name, T item, String texture, DataItemModel itemModel) {
        T i = Registry.register(Registries.ITEM, FadenIdentifier.create(name), item);
        ITEMS.add(new FadenDataItem(i, texture, itemModel));
        return i;
    }

    private static <T extends Item> T registerItem(String name, T item, String texture) {
        return registerItem(name, item, texture, DataItemModel.GENERATED);
    }

    public static void register() {}

}
