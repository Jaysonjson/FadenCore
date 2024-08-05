package net.fuchsia.common.init.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fuchsia.common.objects.block.CapeStandBlock;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class FadenBlocks {

    public static final Block CAPE_STAND = registerBlock("cape_stand", new CapeStandBlock(FabricBlockSettings.create().nonOpaque()));


    private static Block registerBlock(String name, Block block) {
        Block registeredBlock = Registry.register(Registries.BLOCK, FadenIdentifier.create(name), block);
        Registry.register(Registries.ITEM, FadenIdentifier.create(name), new BlockItem(registeredBlock, new Item.Settings()));
        return registeredBlock;
    }

    private static Block registerBlock(String name, Block block, BlockItem item) {
        Block registeredBlock = Registry.register(Registries.BLOCK, FadenIdentifier.create(name), block);
        Registry.register(Registries.ITEM, FadenIdentifier.create(name), item);
        return registeredBlock;
    }

    public static void register() {}

}
