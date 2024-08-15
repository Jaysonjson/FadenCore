package json.jayson.faden.core.datagen.data;

import json.jayson.faden.core.datagen.FadenCoreDataGen;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import json.jayson.faden.core.datagen.DataToolTier;
import json.jayson.faden.core.datagen.DataToolType;
import json.jayson.faden.core.datagen.holders.BuildingBlockDataEntry;
import net.minecraft.block.Block;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class FadenBlockTagProvider extends FabricTagProvider.BlockTagProvider {
    public FadenBlockTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        for (BuildingBlockDataEntry block : FadenCoreDataGen.BLOCKS) {
            Identifier blockId = Registries.BLOCK.getId(block.block());
            if(!FadenCoreDataGen.MOD_ID.isBlank() && !blockId.getNamespace().equalsIgnoreCase(FadenCoreDataGen.MOD_ID)) continue;

            doTags(block.block(), block.toolType(), block.toolTier());

            switch (block.blockType()) {
                case WALL -> {
                    getOrCreateTagBuilder(BlockTags.WALLS).add(block.block());
                }

                case SLAB -> {
                    getOrCreateTagBuilder(BlockTags.SLABS).add(block.block());
                }

                case DOOR -> {
                    getOrCreateTagBuilder(BlockTags.DOORS).add(block.block());
                }

                case STAIR -> {
                    getOrCreateTagBuilder(BlockTags.STAIRS).add(block.block());
                }
            }

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