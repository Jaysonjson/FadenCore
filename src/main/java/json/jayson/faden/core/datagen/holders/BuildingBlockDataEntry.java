package json.jayson.faden.core.datagen.holders;

import json.jayson.faden.core.datagen.DataBlockType;
import json.jayson.faden.core.datagen.DataToolTier;
import json.jayson.faden.core.datagen.DataToolType;
import net.minecraft.block.Block;
import net.minecraft.item.Item;

public record BuildingBlockDataEntry(Block block, Block base, DataToolType toolType, DataToolTier toolTier, DataBlockType blockType, boolean dropSelf, boolean language) {
    public Item item() {return block().asItem();}
}
