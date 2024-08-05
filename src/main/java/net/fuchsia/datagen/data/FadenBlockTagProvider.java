package net.fuchsia.datagen.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fuchsia.common.init.blocks.FadenBuildingBlocks;
import net.fuchsia.datagen.DataToolTier;
import net.fuchsia.datagen.DataToolType;
import net.fuchsia.datagen.holders.BuildingBlockDataEntry;
import net.minecraft.block.Block;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;

import java.util.concurrent.CompletableFuture;

public class FadenBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public FadenBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        for (BuildingBlockDataEntry roundel : FadenBuildingBlocks.BUILDING_BLOCKS) {
            doTags(roundel.block(), roundel.toolType(), roundel.toolTier());
        }
    }

    public void doTags(Block block, DataToolType toolType, DataToolTier toolTier) {
        switch (toolType) {
            case PICKAXE -> getOrCreateTagBuilder(BlockTags.PICKAXE_MINEABLE).add(block);
            case AXE -> getOrCreateTagBuilder(BlockTags.AXE_MINEABLE).add(block);
            case SHOVEL -> getOrCreateTagBuilder(BlockTags.SHOVEL_MINEABLE).add(block);
            case HOE -> getOrCreateTagBuilder(BlockTags.HOE_MINEABLE).add(block);
            case NONE -> {break;}
        }

        switch (toolTier) {
            case STONE, WOOD -> getOrCreateTagBuilder(BlockTags.NEEDS_STONE_TOOL).add(block);
            case IRON, GOLD -> getOrCreateTagBuilder(BlockTags.NEEDS_IRON_TOOL).add(block);
            case DIAMOND, NETHERITE -> getOrCreateTagBuilder(BlockTags.NEEDS_DIAMOND_TOOL).add(block);
        }
    }
}