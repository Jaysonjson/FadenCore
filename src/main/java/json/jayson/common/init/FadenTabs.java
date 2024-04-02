package json.jayson.common.init;

import json.jayson.datagen.FadenDataItem;
import json.jayson.util.FadenIdentifier;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

public class FadenTabs {

    public static final ItemGroup FADEN = Registry.register(Registries.ITEM_GROUP, FadenIdentifier.create("building_tab"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.faden.misc")).icon(() -> new ItemStack(FadenItems.AMETHYST_COIN)).entries((displayContext, entries) -> {
                for (FadenDataItem item : FadenItems.ITEMS) {
                    entries.add(item.item());
                }
            }).build());

    public static void register() {}

}
