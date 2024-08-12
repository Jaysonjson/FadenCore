package net.fuchsia.datagen.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.fuchsia.common.init.blocks.FadenBuildingBlocks;
import net.fuchsia.common.init.items.FadenItems;
import net.fuchsia.datagen.holders.BuildingBlockDataEntry;
import net.fuchsia.datagen.holders.FadenDataItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.text.Text;
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
        for (BuildingBlockDataEntry buildingBlock : FadenBuildingBlocks.BUILDING_BLOCKS) {
            if(!buildingBlock.language()) continue;
            Identifier id = Registries.BLOCK.getId(buildingBlock.block());
            if(Language.getInstance().hasTranslation(Registries.BLOCK.get(id).getTranslationKey())) continue;
            translationBuilder.add(buildingBlock.block(), capitaliseUnderscores(id.getPath()));
        }

        for (FadenDataItem item : FadenItems.ITEMS) {
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
