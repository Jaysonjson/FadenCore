package net.fuchsia.datagen;

import net.fuchsia.datagen.asset.FadenDataModelProvider;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fuchsia.datagen.data.FadenBlockTagProvider;
import net.fuchsia.datagen.data.FadenItemTagProvider;
import net.fuchsia.datagen.data.FadenLootTableProvider;

public class FadenDataGen implements DataGeneratorEntrypoint {
    @Override
    public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
        FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
        pack.addProvider(FadenDataModelProvider::new);
        pack.addProvider(FadenItemTagProvider::new);
        pack.addProvider(FadenBlockTagProvider::new);
        pack.addProvider(FadenLootTableProvider::new);
    }
}
