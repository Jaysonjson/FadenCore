package net.fuchsia.common.init.items;

import net.fuchsia.common.init.FadenTabs;
import net.fuchsia.common.objects.item.gear.bracelet.BraceletItem;
import net.fuchsia.common.objects.item.gear.bracelet.TestBracelet;
import net.fuchsia.common.objects.item.gear.bracelet.WarriorBracelet;
import net.fuchsia.common.objects.item.gear.necklace.FrogPendant;
import net.fuchsia.common.objects.item.gear.necklace.KingOFWaterNecklaceItem;
import net.fuchsia.common.objects.item.gear.necklace.NecklaceItem;
import net.fuchsia.datagen.DataItemModel;
import net.fuchsia.datagen.holders.FadenDataItem;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class FadenGear {

    public static List<BraceletItem> BRACELETS = new ArrayList<>();
    public static List<NecklaceItem> NECKLACES = new ArrayList<>();

    public static BraceletItem WARRIORS_BRACELET = registerBracelet("warriors_bracelet", new WarriorBracelet(new Item.Settings()), "ingots/silver_ingot");
    public static NecklaceItem KING_OF_WATER_NECKLACE = registerNecklace("king_of_water_necklace", new KingOFWaterNecklaceItem(new Item.Settings()), "ingots/silver_ingot");
    public static BraceletItem TEST_BRACELET = registerBracelet("test_bracelet", new TestBracelet(new Item.Settings()), "ingots/silver_ingot");
    public static NecklaceItem FROG_PENDANT = registerNecklace("frog_pendant", new FrogPendant(new Item.Settings()), "ingots/silver_ingot");

    private static <T extends BraceletItem> T registerBracelet(String name, T item, String texture, DataItemModel itemModel) {
        T i = Registry.register(Registries.ITEM, FadenIdentifier.create(name), item);
        FadenItems.ITEMS.add(new FadenDataItem(i, texture, itemModel, FadenTabs.FADEN_GEAR));
        BRACELETS.add(i);
        return i;
    }

    private static <T extends BraceletItem> T registerBracelet(String name, T item, String texture) {
        return registerBracelet(name, item, texture, DataItemModel.GENERATED);
    }



    private static <T extends NecklaceItem> T registerNecklace(String name, T item, String texture, DataItemModel itemModel) {
        T i = Registry.register(Registries.ITEM, FadenIdentifier.create(name), item);
        FadenItems.ITEMS.add(new FadenDataItem(i, texture, itemModel, FadenTabs.FADEN_GEAR));
        NECKLACES.add(i);
        return i;
    }

    private static <T extends NecklaceItem> T registerNecklace(String name, T item, String texture) {
        return registerNecklace(name, item, texture, DataItemModel.GENERATED);
    }

    public static void register() {}

}
