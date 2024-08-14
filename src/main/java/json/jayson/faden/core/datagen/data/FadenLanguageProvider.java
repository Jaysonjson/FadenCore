package json.jayson.faden.core.datagen.data;

import json.jayson.faden.core.datagen.FadenCoreDataGen;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import json.jayson.faden.core.datagen.holders.BuildingBlockDataEntry;
import json.jayson.faden.core.datagen.holders.FadenDataItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;
import net.minecraft.util.Language;
import org.apache.commons.lang3.text.WordUtils;

import java.util.concurrent.CompletableFuture;

public class FadenLanguageProvider extends FabricLanguageProvider {
    public FadenLanguageProvider(FabricDataOutput dataOutput, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, registryLookup);
    }

    public FadenLanguageProvider(FabricDataOutput dataOutput, String languageCode, CompletableFuture<RegistryWrapper.WrapperLookup> registryLookup) {
        super(dataOutput, languageCode, registryLookup);
    }

    @Override
    public void generateTranslations(RegistryWrapper.WrapperLookup registryLookup, TranslationBuilder translationBuilder) {
        for (BuildingBlockDataEntry buildingBlock : FadenCoreDataGen.BLOCKS) {
            if(!buildingBlock.language()) continue;
            Identifier id = Registries.BLOCK.getId(buildingBlock.block());
            if(Language.getInstance().hasTranslation(Registries.BLOCK.get(id).getTranslationKey())) continue;
            translationBuilder.add(buildingBlock.block(), capitaliseUnderscores(id.getPath()));
        }

        for (FadenDataItem item : FadenCoreDataGen.ITEMS) {
            Identifier id = Registries.ITEM.getId(item.item());
            if(Language.getInstance().hasTranslation(Registries.ITEM.get(id).getTranslationKey())) continue;
            translationBuilder.add(item.item(), capitaliseUnderscores(id.getPath()));
        }
    }

    public String capitaliseUnderscores(String id) {
        String str = id.replaceAll("_", " ");
        return WordUtils.capitalizeFully(str);
    }
}
