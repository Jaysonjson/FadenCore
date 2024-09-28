package json.jayson.faden.core.common.init;

import json.jayson.faden.core.common.objects.block.NPCSpawnerMarkerBlock;
import json.jayson.faden.core.common.objects.block.item.NPCSpawnerMarkerBlockItem;
import json.jayson.faden.core.datagen.DataBlockType;
import json.jayson.faden.core.datagen.DataToolTier;
import json.jayson.faden.core.datagen.DataToolType;
import json.jayson.faden.core.datagen.FadenCoreDataGen;
import json.jayson.faden.core.datagen.holders.BuildingBlockDataEntry;
import json.jayson.faden.core.util.FadenCoreIdentifier;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class FadenCoreBlocks {

    //public static Block NPC_SPAWNER_MARKER = register("npc_spawner_marker", new NPCSpawnerMarkerBlock(AbstractBlock.Settings.create()));
    //public static BlockItem NPC_SPAWNER_MARKER_ITEM = registerBlockItem("npc_spawner_marker", new NPCSpawnerMarkerBlockItem(NPC_SPAWNER_MARKER, new Item.Settings()));

    private static Block register(String name, Block block) {
        return register(name, block, new BlockItem(block, new Item.Settings()));
    }

    private static Block register(String name, Block block, BlockItem blockItem) {
        Block registeredBlock = Registry.register(Registries.BLOCK, FadenCoreIdentifier.create(name), block);
        Registry.register(Registries.ITEM, FadenCoreIdentifier.create(name), blockItem);
        FadenCoreDataGen.BLOCKS.add(new BuildingBlockDataEntry(block, block, DataToolType.NONE, DataToolTier.NONE, DataBlockType.CUBE, true, true));
        return registeredBlock;
    }

    private static BlockItem registerBlockItem(String name, BlockItem blockItem) {
        return Registry.register(Registries.ITEM, FadenCoreIdentifier.create(name), blockItem);
    }

    public static void init() {}

}
