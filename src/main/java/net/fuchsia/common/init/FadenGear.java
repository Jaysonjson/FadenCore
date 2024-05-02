package net.fuchsia.common.init;

import net.fuchsia.common.objects.item.gear.bracelet.BraceletItem;
import net.fuchsia.common.objects.item.gear.necklace.KingOFWaterNecklaceItem;
import net.fuchsia.common.objects.item.gear.necklace.NecklaceItem;
import net.fuchsia.datagen.DataItemModel;
import net.fuchsia.datagen.FadenDataItem;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.util.ArrayList;
import java.util.List;

public class FadenGear {

    public static List<BraceletItem> BRACELETS = new ArrayList<>();
    public static List<NecklaceItem> NECKLACES = new ArrayList<>();

    public static BraceletItem TEST_BRACELET = registerBracelet("test_bracelet", new BraceletItem(new Item.Settings()), "ingots/silver_ingot");
    public static KingOFWaterNecklaceItem KING_OF_WATER_NECKLACE = registerNecklace("king_of_water_necklace", new KingOFWaterNecklaceItem(new Item.Settings()), "ingots/silver_ingot");

    private static <T extends BraceletItem> T registerBracelet(String name, T item, String texture, DataItemModel itemModel) {
        T i = Registry.register(Registries.ITEM, FadenIdentifier.create(name), item);
        FadenItems.ITEMS.add(new FadenDataItem(i, texture, itemModel));
        BRACELETS.add(i);
        return i;
    }

    private static <T extends BraceletItem> T registerBracelet(String name, T item, String texture) {
        return registerBracelet(name, item, texture, DataItemModel.GENERATED);
    }



    private static <T extends NecklaceItem> T registerNecklace(String name, T item, String texture, DataItemModel itemModel) {
        T i = Registry.register(Registries.ITEM, FadenIdentifier.create(name), item);
        FadenItems.ITEMS.add(new FadenDataItem(i, texture, itemModel));
        NECKLACES.add(i);
        return i;
    }

    private static <T extends NecklaceItem> T registerNecklace(String name, T item, String texture) {
        return registerNecklace(name, item, texture, DataItemModel.GENERATED);
    }

    public static void register() {}

}
