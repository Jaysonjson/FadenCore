package net.fuchsia.common.init.blocks;

import net.fuchsia.datagen.DataBlockType;
import net.fuchsia.datagen.DataToolTier;
import net.fuchsia.datagen.DataToolType;
import net.fuchsia.datagen.holders.BuildingBlockDataEntry;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FadenBuildingBlocks {

    public static List<BuildingBlockDataEntry> BUILDING_BLOCKS  = new ArrayList<>();


    protected static Block registerBlock(String name, Block block, @Nullable Block base, DataToolType toolType, DataToolTier tier, DataBlockType blockType, boolean dropSelf) {
        Block registeredBlock = Registry.register(Registries.BLOCK, FadenIdentifier.create(name), block);
        Registry.register(Registries.ITEM, FadenIdentifier.create(name), new BlockItem(registeredBlock, new Item.Settings()));
        BUILDING_BLOCKS.add(new BuildingBlockDataEntry(block, base, toolType, tier, blockType, dropSelf));
        return registeredBlock;
    }


    protected static Block registerBlock(String name, Block block, DataToolType toolType, DataToolTier tier, DataBlockType blockType) {
        return registerBlock(name, block, null, toolType, tier, blockType, true);
    }

    protected static Block registerBlock(String name, Block block, DataBlockType blockType) {
        return registerBlock(name, block, null, DataToolType.NONE, DataToolTier.NONE,  blockType, true);
    }

    protected static Block registerBlock(String name, Block block, DataToolType toolType, DataBlockType blockType) {
        return registerBlock(name, block, null, toolType, DataToolTier.NONE,  blockType, true);
    }

    protected static Block registerBlock(String name, Block block, DataBlockType blockType, boolean dropSelf) {
        return registerBlock(name, block, null, DataToolType.NONE, DataToolTier.NONE,  blockType, dropSelf);
    }

    protected static Block registerBlock(String name, Block block, DataToolType toolType, DataBlockType blockType, boolean dropSelf) {
        return registerBlock(name, block, null, toolType, DataToolTier.NONE,  blockType, dropSelf);
    }

    public static void register() {}


}
