package json.jayson.faden.core.common.init;

import json.jayson.faden.core.common.objects.block.item.NPCSpawnerMarkerBlockItem;
import json.jayson.faden.core.datagen.FadenCoreDataGen;
import json.jayson.faden.core.datagen.holders.FadenDataItem;
import json.jayson.faden.core.registry.FadenCoreRegistry;
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
                FadenCoreRegistry.NPC.forEach((npc) -> {
                    ItemStack stack = FadenCoreBlocks.NPC_SPAWNER_MARKER_ITEM.getDefaultStack();
                    stack.set(FadenCoreDataComponents.NPC_ID, npc.getIdentifier().toString());
                    entries.add(stack);
                });
            }).build());


    public static void init() {}

}
