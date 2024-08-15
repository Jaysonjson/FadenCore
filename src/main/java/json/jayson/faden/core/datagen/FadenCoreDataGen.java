package json.jayson.faden.core.datagen;

import json.jayson.faden.core.datagen.asset.FadenDataModelProvider;
import json.jayson.faden.core.datagen.data.*;
import json.jayson.faden.core.datagen.holders.BuildingBlockDataEntry;
import json.jayson.faden.core.datagen.holders.FadenDataItem;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;

import java.util.ArrayList;
import java.util.List;

public class FadenCoreDataGen implements DataGeneratorEntrypoint {

    public static List<FadenDataItem> ITEMS = new ArrayList<>();
    public static List<BuildingBlockDataEntry> BLOCKS  = new ArrayList<>();

    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(FadenDataModelProvider::new);
        pack.addProvider(FadenItemTagProvider::new);
        pack.addProvider(FadenBlockTagProvider::new);
        pack.addProvider(FadenLootTableProvider::new);
        pack.addProvider(FadenRecipeProvider::new);
        pack.addProvider(FadenLanguageProvider::new);
    }
}
