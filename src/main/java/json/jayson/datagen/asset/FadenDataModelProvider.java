package json.jayson.datagen.asset;

import json.jayson.common.init.FadenItems;
import json.jayson.datagen.FadenDataItem;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class FadenDataModelProvider extends FabricModelProvider {
    public FadenDataModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        for (FadenDataItem item : FadenItems.ITEMS) {
            switch (item.itemModel()) {
                case GENERATED -> addTexturedItem(item.item(), item.texture(), itemModelGenerator);
                case HANDHELD -> addTexturedItemHandheld(item.item(), item.texture(), itemModelGenerator);
            }
        }
    }

    public final void addTexturedItem(Item item, String texture, ItemModelGenerator modelGenerator) {
        Identifier itemId = Registries.ITEM.getId(item);
        Models.GENERATED.upload(ModelIds.getItemModelId(item), TextureMap.layer0(new Identifier(itemId.getNamespace(), "item/" + texture)), modelGenerator.writer);
    }

    public final void addTexturedItemHandheld(Item item, String texture, ItemModelGenerator modelGenerator) {
        Identifier itemId = Registries.ITEM.getId(item);
        Models.HANDHELD.upload(ModelIds.getItemModelId(item), TextureMap.layer0(new Identifier(itemId.getNamespace(), "item/" + texture)), modelGenerator.writer);
    }

}
