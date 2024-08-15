package json.jayson.faden.core.datagen.data;

import json.jayson.faden.core.datagen.FadenCoreDataGen;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import json.jayson.faden.core.datagen.holders.BuildingBlockDataEntry;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class FadenLootTableProvider extends FabricBlockLootTableProvider {

    public FadenLootTableProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    @Override
    public void generate() {
        for (BuildingBlockDataEntry buildingBlock : FadenCoreDataGen.BLOCKS) {
            if(buildingBlock.dropSelf()) {
                addDrop(buildingBlock.block());
            }
        }
    }
}
