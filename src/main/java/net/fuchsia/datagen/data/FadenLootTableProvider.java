package net.fuchsia.datagen.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.fuchsia.common.init.blocks.FadenCoreBlocks;
import net.fuchsia.datagen.holders.BuildingBlockDataEntry;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class FadenLootTableProvider extends FabricBlockLootTableProvider {

    public FadenLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        for (BuildingBlockDataEntry buildingBlock : FadenCoreBlocks.BUILDING_BLOCKS) {
            if(buildingBlock.dropSelf()) {
                addDrop(buildingBlock.block());
            }
        }
    }
}
