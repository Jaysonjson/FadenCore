package json.jayson.faden.core.common.init;

import json.jayson.faden.core.datagen.FadenCoreDataGen;
import json.jayson.faden.core.datagen.holders.FadenDataItem;
import json.jayson.faden.core.util.FadenCoreIdentifier;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

public class FadenCoreTabs {

    public static final ItemGroup FADEN = Registry.register(Registries.ITEM_GROUP, FadenCoreIdentifier.create("dev_tab"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.fadencore.dev")).icon(() -> new ItemStack(FadenCoreBlocks.NPC_SPAWNER_MARKER)).entries((displayContext, entries) -> {
                entries.add(FadenCoreBlocks.NPC_SPAWNER_MARKER);
            }).build());


    public static void init() {}

}
