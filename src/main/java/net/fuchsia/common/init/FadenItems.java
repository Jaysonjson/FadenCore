package net.fuchsia.common.init;

import net.fuchsia.common.objects.item.TooltipTestItem;
import net.fuchsia.common.objects.item.coin.CoinItem;
import net.fuchsia.common.objects.item.TestItem;
import net.fuchsia.common.objects.item.instrument.InstrumentItem;
import net.fuchsia.common.objects.item.instrument.InstrumentType;
import net.fuchsia.datagen.DataItemModel;
import net.fuchsia.datagen.FadenDataItem;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.item.DiscFragmentItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Rarity;

import java.util.ArrayList;
import java.util.List;

public class FadenItems {

    public static List<FadenDataItem> ITEMS = new ArrayList<>();

    public static CoinItem COPPER_COIN = registerItem("copper_coin", new CoinItem(new Item.Settings(), 1), "coins/copper_coin");
    public static CoinItem IRON_COIN = registerItem("iron_coin", new CoinItem(new Item.Settings(), 5), "coins/iron_coin");
    public static CoinItem ENDER_COIN = registerItem("ender_coin", new CoinItem(new Item.Settings(), 10), "coins/ender_coin");
    public static CoinItem SILVER_COIN = registerItem("silver_coin", new CoinItem(new Item.Settings(), 50), "coins/silver_coin");
    public static CoinItem GOLD_COIN = registerItem("gold_coin", new CoinItem(new Item.Settings(), 100), "coins/gold_coin");
    public static CoinItem AMETHYST_COIN = registerItem("amethyst_coin", new CoinItem(new Item.Settings(), 500), "coins/amethyst_coin");
    public static CoinItem NETHERITE_COIN = registerItem("netherite_coin", new CoinItem(new Item.Settings(), 1000), "coins/netherite_coin");
    public static CoinItem STAR_COIN = registerItem("star_coin", new CoinItem(new Item.Settings(), 64000), "coins/star_coin");
    public static Item SILVER_INGOT = registerItem("silver_ingot", new Item(new Item.Settings()), "ingots/silver_ingot");
    public static Item TEST = registerItem("test_item", new TestItem(new Item.Settings()), "ingots/silver_ingot");
    public static Item TOOL_TIP_TEST = registerItem("tooltip_test_item", new TooltipTestItem(new Item.Settings()), "ingots/silver_ingot");

    public static Item SCROLL = registerItem("scroll", new Item(new Item.Settings()), "scroll/scroll");

    public static Item BLACK_CLOTH = registerItem("black_cloth", new Item(new Item.Settings()), "crafting/cloth/black");
    public static Item BLUE_CLOTH = registerItem("blue_cloth", new Item(new Item.Settings()), "crafting/cloth/blue");
    public static Item BROWN_CLOTH = registerItem("brown_cloth", new Item(new Item.Settings()), "crafting/cloth/brown");
    public static Item CYAN_CLOTH = registerItem("cyan_cloth", new Item(new Item.Settings()), "crafting/cloth/cyan");
    public static Item GRAY_CLOTH = registerItem("gray_cloth", new Item(new Item.Settings()), "crafting/cloth/gray");
    public static Item GREEN_CLOTH = registerItem("green_cloth", new Item(new Item.Settings()), "crafting/cloth/green");
    public static Item LIGHT_BLUE_CLOTH = registerItem("light_blue_cloth", new Item(new Item.Settings()), "crafting/cloth/light_blue");
    public static Item LIGHT_GRAY_CLOTH = registerItem("light_gray_cloth", new Item(new Item.Settings()), "crafting/cloth/light_gray");
    public static Item LIME_CLOTH = registerItem("lime_cloth", new Item(new Item.Settings()), "crafting/cloth/lime");
    public static Item MAGENTA_CLOTH = registerItem("magenta_cloth", new Item(new Item.Settings()), "crafting/cloth/magenta");
    public static Item ORANGE_CLOTH = registerItem("orange_cloth", new Item(new Item.Settings()), "crafting/cloth/orange");
    public static Item PINK_CLOTH = registerItem("pink_cloth", new Item(new Item.Settings()), "crafting/cloth/pink");
    public static Item PURPLE_CLOTH = registerItem("purple_cloth", new Item(new Item.Settings()), "crafting/cloth/purple");
    public static Item RED_CLOTH = registerItem("red_cloth", new Item(new Item.Settings()), "crafting/cloth/red");
    public static Item WHITE_CLOTH = registerItem("white_cloth", new Item(new Item.Settings()), "crafting/cloth/white");
    public static Item YELLOW_CLOTH = registerItem("yellow_cloth", new Item(new Item.Settings()), "crafting/cloth/yellow");

    public static Item TEST_LUTE = registerItem("test_lute", new InstrumentItem(new Item.Settings(), InstrumentType.LUTE), "test");
    public static Item TEST_PANFLUTE = registerItem("test_panflute", new InstrumentItem(new Item.Settings(), InstrumentType.PAN_FLUTE), "test");
    public static Item TEST_HURTY = registerItem("test_hurty", new InstrumentItem(new Item.Settings(), InstrumentType.HURDY), "test");
    public static Item TEST_DRUM = registerItem("test_drum", new InstrumentItem(new Item.Settings(), InstrumentType.DRUM), "test");

    //public static Item FADEN_MUSIC_DISC = registerItem("music_disc_faden", new DiscFragmentItem(14, FadenSoundEvents.FADEN, new Item.Settings().maxCount(1).rarity(Rarity.RARE), 140), "discs/faden");

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
