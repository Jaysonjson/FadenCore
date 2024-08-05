package net.fuchsia.datagen.holders;

import net.fuchsia.datagen.DataBlockType;
import net.fuchsia.datagen.DataToolTier;
import net.fuchsia.datagen.DataToolType;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public record BuildingBlockDataEntry(Block block, Block base, DataToolType toolType, DataToolTier toolTier, DataBlockType blockType, boolean dropSelf, boolean language) {
    public Item item() {return block().asItem();}
}
