package net.fuchsia.datagen.asset;

import net.fuchsia.common.init.FadenItems;
import net.fuchsia.common.init.blocks.FadenBuildingBlocks;
import net.fuchsia.datagen.holders.BuildingBlockDataEntry;
import net.fuchsia.datagen.holders.FadenDataItem;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.Optional;

public class FadenDataModelProvider extends FabricModelProvider {
    public FadenDataModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        for (BuildingBlockDataEntry buildingBlock : FadenBuildingBlocks.BUILDING_BLOCKS) {
            Identifier blockId = Registries.BLOCK.getId(buildingBlock.block());
            switch (buildingBlock.blockType()) {
                case CUBE -> {
                    new Model(Optional.of(Identifier.of("block/cube_all")), Optional.empty()).upload(Identifier.of(blockId.getNamespace(), "block/building/" + blockId.getPath()), new TextureMap().register(TextureKey.of("all"), Identifier.of(blockId.getNamespace(), "block/building/" + blockId.getPath())), blockStateModelGenerator.modelCollector);
                    blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(buildingBlock.block(), BlockStateVariant.create().put(VariantSettings.MODEL, Identifier.of(blockId.getNamespace(), "block/building/" + blockId.getPath()))));
                }

                case DOOR -> {
                    blockStateModelGenerator.registerDoor(buildingBlock.block());
                }

                case SLAB -> {
                    blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSlabBlockState(buildingBlock.block(), Identifier.of(blockId.getNamespace(), "block/building/slabs/bot/" + blockId.getPath()), Identifier.of(blockId.getNamespace(), "block/building/slabs/top/" + blockId.getPath()), Identifier.of(blockId.getNamespace(), "block/building/" + blockId.getPath())));
                    new Model(Optional.of(FadenIdentifier.minecraft("block/slab")), Optional.empty()).upload(Identifier.of(blockId.getNamespace(), "block/building/slabs/bot/" + blockId.getPath()), slabTextureMap(blockId), blockStateModelGenerator.modelCollector);
                    new Model(Optional.of(FadenIdentifier.minecraft("block/slab_top")), Optional.empty()).upload(Identifier.of(blockId.getNamespace(), "block/building/slabs/top/" + blockId.getPath()), slabTextureMap(blockId), blockStateModelGenerator.modelCollector);
                    //TexturedModel.CUBE_ALL.upload(buildingBlock.getBlock(), blockStateModelGenerator.modelCollector);
                    new Model(Optional.of(Identifier.of("block/cube_all")), Optional.empty()).upload(Identifier.of(blockId.getNamespace(), "block/building/" + blockId.getPath()), new TextureMap().register(TextureKey.of("all"), Identifier.of(blockId.getNamespace(), "block/building/" + blockId.getPath())), blockStateModelGenerator.modelCollector);
                }

                case WALL -> {
                }
            }
        }
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        for (FadenDataItem item : FadenItems.ITEMS) {
            switch (item.itemModel()) {
                case GENERATED -> addTexturedItem(item.item(), item.texture(), itemModelGenerator);
                case HANDHELD -> addTexturedItemHandheld(item.item(), item.texture(), itemModelGenerator);
            }
        }

        for (BuildingBlockDataEntry buildingBlock : FadenBuildingBlocks.BUILDING_BLOCKS) {
            Identifier blockId = Registries.BLOCK.getId(buildingBlock.block());
            switch (buildingBlock.blockType()) {
                case CUBE -> {
                    itemModelGenerator.register(buildingBlock.item(), new Model(Optional.of(Identifier.of(blockId.getNamespace(),"block/building/" + blockId.getPath())), Optional.empty()));
                }

                case DOOR -> {
                }

                case SLAB -> {
                    itemModelGenerator.register(buildingBlock.item(), new Model(Optional.of(Identifier.of(blockId.getNamespace(),"block/building/slabs/bot/" + blockId.getPath())), Optional.empty()));
                }

                case STAIR -> {
                }
            }
        }

    }

    public final void addTexturedItem(Item item, String texture, ItemModelGenerator modelGenerator) {
        Identifier itemId = Registries.ITEM.getId(item);
        Models.GENERATED.upload(ModelIds.getItemModelId(item), TextureMap.layer0(Identifier.of(itemId.getNamespace(), "item/" + texture)), modelGenerator.writer);
    }

    public final void addTexturedItemHandheld(Item item, String texture, ItemModelGenerator modelGenerator) {
        Identifier itemId = Registries.ITEM.getId(item);
        Models.HANDHELD.upload(ModelIds.getItemModelId(item), TextureMap.layer0(Identifier.of(itemId.getNamespace(), "item/" + texture)), modelGenerator.writer);
    }

    public TextureMap slabTextureMap(Identifier id) {
        TextureMap map = new TextureMap();
        map.register( TextureKey.of("bottom"), Identifier.of(id.getNamespace(),"block/building/" + id.getPath()));
        map.register( TextureKey.of("side"), Identifier.of(id.getNamespace(),"block/building/" + id.getPath()));
        map.register( TextureKey.of("top"), Identifier.of(id.getNamespace(),"block/building/" + id.getPath()));
        return map;
    }

}
