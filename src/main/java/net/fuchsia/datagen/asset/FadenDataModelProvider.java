package net.fuchsia.datagen.asset;

import net.fuchsia.common.init.items.FadenItems;
import net.fuchsia.common.init.blocks.FadenBuildingBlocks;
import net.fuchsia.datagen.holders.BuildingBlockDataEntry;
import net.fuchsia.datagen.holders.FadenDataItem;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.data.client.*;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.state.property.Properties;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.Direction;

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
                    //blockStateModelGenerator.registerDoor(buildingBlock.block());
                    blockStateModelGenerator.blockStateCollector.accept(
                            BlockStateModelGenerator.createDoorBlockState(buildingBlock.block(),
                                    Identifier.of(blockId.getNamespace(), "block/building/door/bot/left/" + blockId.getPath()),
                                    Identifier.of(blockId.getNamespace(), "block/building/door/bot/left/open/" + blockId.getPath()),
                                    Identifier.of(blockId.getNamespace(), "block/building/door/bot/right/" + blockId.getPath()),
                                    Identifier.of(blockId.getNamespace(), "block/building/door/bot/right/open/" + blockId.getPath()),
                                    Identifier.of(blockId.getNamespace(), "block/building/door/top/left/" + blockId.getPath()),
                                    Identifier.of(blockId.getNamespace(), "block/building/door/top/left/open/" + blockId.getPath()),
                                    Identifier.of(blockId.getNamespace(), "block/building/door/top/right/" + blockId.getPath()),
                                    Identifier.of(blockId.getNamespace(), "block/building/door/top/right/open/" + blockId.getPath())));
                    TextureMap textureMap = TextureMap.topBottom(buildingBlock.block());
                    new Model(Optional.of(FadenIdentifier.minecraft("block/door_bottom_left_open")), Optional.empty()).upload(Identifier.of(blockId.getNamespace(), "block/building/door/bot/left/open/" + blockId.getPath()), textureMap, blockStateModelGenerator.modelCollector);
                    new Model(Optional.of(FadenIdentifier.minecraft("block/door_bottom_left")), Optional.empty()).upload(Identifier.of(blockId.getNamespace(), "block/building/door/bot/left/" + blockId.getPath()), textureMap, blockStateModelGenerator.modelCollector);
                    new Model(Optional.of(FadenIdentifier.minecraft("block/door_bottom_right_open")), Optional.empty()).upload(Identifier.of(blockId.getNamespace(), "block/building/door/bot/right/open/" + blockId.getPath()), textureMap, blockStateModelGenerator.modelCollector);
                    new Model(Optional.of(FadenIdentifier.minecraft("block/door_bottom_right")), Optional.empty()).upload(Identifier.of(blockId.getNamespace(), "block/building/door/bot/right/" + blockId.getPath()), textureMap, blockStateModelGenerator.modelCollector);
                    new Model(Optional.of(FadenIdentifier.minecraft("block/door_top_left_open")), Optional.empty()).upload(Identifier.of(blockId.getNamespace(), "block/building/door/top/left/open/" + blockId.getPath()), textureMap, blockStateModelGenerator.modelCollector);
                    new Model(Optional.of(FadenIdentifier.minecraft("block/door_top_left")), Optional.empty()).upload(Identifier.of(blockId.getNamespace(), "block/building/door/top/left/" + blockId.getPath()), textureMap, blockStateModelGenerator.modelCollector);
                    new Model(Optional.of(FadenIdentifier.minecraft("block/door_top_right_open")), Optional.empty()).upload(Identifier.of(blockId.getNamespace(), "block/building/door/top/right/open/" + blockId.getPath()), textureMap, blockStateModelGenerator.modelCollector);
                    new Model(Optional.of(FadenIdentifier.minecraft("block/door_top_right")), Optional.empty()).upload(Identifier.of(blockId.getNamespace(), "block/building/door/tpü/right/" + blockId.getPath()), textureMap, blockStateModelGenerator.modelCollector);
                }

                case SLAB -> {
                    if(buildingBlock.base() == null) {
                        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSlabBlockState(buildingBlock.block(), Identifier.of(blockId.getNamespace(), "block/building/slabs/bot/" + blockId.getPath()), Identifier.of(blockId.getNamespace(), "block/building/slabs/top/" + blockId.getPath()), Identifier.of(blockId.getNamespace(), "block/building/" + blockId.getPath())));
                    } else {
                        Identifier id = Registries.BLOCK.getId(buildingBlock.base());
                        blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createSlabBlockState(buildingBlock.block(), Identifier.of(blockId.getNamespace(), "block/building/slabs/bot/" + blockId.getPath()), Identifier.of(blockId.getNamespace(), "block/building/slabs/top/" + blockId.getPath()), Identifier.of(blockId.getNamespace(), "block/building/" + id.getPath())));
                    }
                    new Model(Optional.of(FadenIdentifier.minecraft("block/slab")), Optional.empty()).upload(Identifier.of(blockId.getNamespace(), "block/building/slabs/bot/" + blockId.getPath()), slabTextureMap(buildingBlock, blockId), blockStateModelGenerator.modelCollector);
                    new Model(Optional.of(FadenIdentifier.minecraft("block/slab_top")), Optional.empty()).upload(Identifier.of(blockId.getNamespace(), "block/building/slabs/top/" + blockId.getPath()), slabTextureMap(buildingBlock, blockId), blockStateModelGenerator.modelCollector);
                    //TexturedModel.CUBE_ALL.upload(buildingBlock.getBlock(), blockStateModelGenerator.modelCollector);
                    if(buildingBlock.base() == null) {
                        new Model(Optional.of(Identifier.of("block/cube_all")), Optional.empty()).upload(Identifier.of(blockId.getNamespace(), "block/building/" + blockId.getPath()), new TextureMap().register(TextureKey.of("all"), Identifier.of(blockId.getNamespace(), "block/building/" + blockId.getPath())), blockStateModelGenerator.modelCollector);
                    }
                }

                case WALL -> {
                    blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createWallBlockState(buildingBlock.block(), Identifier.of(blockId.getNamespace(), "block/building/walls/post/" + blockId.getPath()), Identifier.of(blockId.getNamespace(), "block/building/walls/low/" + blockId.getPath()), Identifier.of(blockId.getNamespace(), "block/building/walls/tall/" + blockId.getPath())));
                    new Model(Optional.of(FadenIdentifier.minecraft("block/template_wall_post")), Optional.empty()).upload(Identifier.of(blockId.getNamespace(), "block/building/walls/post/" + blockId.getPath()), wallTextureMap(buildingBlock, blockId), blockStateModelGenerator.modelCollector);
                    new Model(Optional.of(FadenIdentifier.minecraft("block/template_wall_side")), Optional.empty()).upload(Identifier.of(blockId.getNamespace(), "block/building/walls/low/" + blockId.getPath()), wallTextureMap(buildingBlock, blockId), blockStateModelGenerator.modelCollector);
                    new Model(Optional.of(FadenIdentifier.minecraft("block/template_wall_side_tall")), Optional.empty()).upload(Identifier.of(blockId.getNamespace(), "block/building/walls/tall/" + blockId.getPath()), wallTextureMap(buildingBlock, blockId), blockStateModelGenerator.modelCollector);
                    new Model(Optional.of(FadenIdentifier.minecraft("block/wall_inventory")), Optional.empty()).upload(Identifier.of(blockId.getNamespace(), "block/building/walls/" + blockId.getPath()), wallTextureMap(buildingBlock, blockId), blockStateModelGenerator.modelCollector);

                }

                case STAIR -> {
                    blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createStairsBlockState(buildingBlock.block(), Identifier.of(blockId.getNamespace(), "block/building/stairs/inner/" + blockId.getPath()), Identifier.of(blockId.getNamespace(), "block/building/stairs/base/" + blockId.getPath()), Identifier.of(blockId.getNamespace(), "block/building/stairs/outer/" + blockId.getPath())));
                    new Model(Optional.of(FadenIdentifier.minecraft("block/inner_stairs")), Optional.empty()).upload(Identifier.of(blockId.getNamespace(), "block/building/stairs/inner/" + blockId.getPath()), slabTextureMap(buildingBlock, blockId), blockStateModelGenerator.modelCollector);
                    new Model(Optional.of(FadenIdentifier.minecraft("block/stairs")), Optional.empty()).upload(Identifier.of(blockId.getNamespace(), "block/building/stairs/base/" + blockId.getPath()), slabTextureMap(buildingBlock, blockId), blockStateModelGenerator.modelCollector);
                    new Model(Optional.of(FadenIdentifier.minecraft("block/outer_stairs")), Optional.empty()).upload(Identifier.of(blockId.getNamespace(), "block/building/stairs/outer/" + blockId.getPath()), slabTextureMap(buildingBlock, blockId), blockStateModelGenerator.modelCollector);
                }

                case BUTTON -> {
                    blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createButtonBlockState(buildingBlock.block(), Identifier.of(blockId.getNamespace(), "block/building/button/base/" + blockId.getPath()), Identifier.of(blockId.getNamespace(), "block/building/button/pressed/" + blockId.getPath())));
                    new Model(Optional.of(FadenIdentifier.minecraft("block/button_pressed")), Optional.empty()).upload(Identifier.of(blockId.getNamespace(), "block/building/button/pressed/" + blockId.getPath()), textureMap(buildingBlock, blockId), blockStateModelGenerator.modelCollector);
                    new Model(Optional.of(FadenIdentifier.minecraft("block/button")), Optional.empty()).upload(Identifier.of(blockId.getNamespace(), "block/building/button/base/" + blockId.getPath()), textureMap(buildingBlock, blockId), blockStateModelGenerator.modelCollector);
                    new Model(Optional.of(FadenIdentifier.minecraft("block/button_inventory")), Optional.empty()).upload(Identifier.of(blockId.getNamespace(), "block/building/button/" + blockId.getPath()), textureMap(buildingBlock, blockId), blockStateModelGenerator.modelCollector);
                }

                case PRESSURE_PLATE -> {
                    blockStateModelGenerator.blockStateCollector.accept(BlockStateModelGenerator.createPressurePlateBlockState(buildingBlock.block(), Identifier.of(blockId.getNamespace(), "block/building/pressure_plate/up/" + blockId.getPath()), Identifier.of(blockId.getNamespace(), "block/building/pressure_plate/down/" + blockId.getPath())));
                    new Model(Optional.of(FadenIdentifier.minecraft("block/pressure_plate_up")), Optional.empty()).upload(Identifier.of(blockId.getNamespace(), "block/building/pressure_plate/up/" + blockId.getPath()), textureMap(buildingBlock, blockId), blockStateModelGenerator.modelCollector);
                    new Model(Optional.of(FadenIdentifier.minecraft("block/pressure_plate_down")), Optional.empty()).upload(Identifier.of(blockId.getNamespace(), "block/building/pressure_plate/down/" + blockId.getPath()), textureMap(buildingBlock, blockId), blockStateModelGenerator.modelCollector);
                }

                case CRAFTING_TABLE -> {
                    blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(buildingBlock.block(), BlockStateVariant.create().put(VariantSettings.MODEL, Identifier.of(blockId.getNamespace(), "block/building/crafting_table/" + blockId.getPath()))));
                    new Model(Optional.of(FadenIdentifier.minecraft("block/cube")), Optional.empty()).upload(Identifier.of(blockId.getNamespace(), "block/building/crafting_table/" + blockId.getPath()), craftingTable(buildingBlock, blockId), blockStateModelGenerator.modelCollector);
                }

                case BULB -> {
                    blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(buildingBlock.block()).coordinate(BlockStateVariantMap.create(Properties.LIT, Properties.POWERED)
                            .register(true, true, BlockStateVariant.create().put(VariantSettings.MODEL, Identifier.of(blockId.getNamespace(), "block/building/bulb/lit_powered/" + blockId.getPath())))
                            .register(false, false, BlockStateVariant.create().put(VariantSettings.MODEL, Identifier.of(blockId.getNamespace(), "block/building/bulb/" + blockId.getPath())))
                            .register(true, false, BlockStateVariant.create().put(VariantSettings.MODEL, Identifier.of(blockId.getNamespace(), "block/building/bulb/lit/" + blockId.getPath())))
                            .register(false, true, BlockStateVariant.create().put(VariantSettings.MODEL, Identifier.of(blockId.getNamespace(), "block/building/bulb/powered/" + blockId.getPath())))
                    ));
                    new Model(Optional.of(Identifier.of("block/cube_all")), Optional.empty()).upload(Identifier.of(blockId.getNamespace(), "block/building/bulb/" + blockId.getPath()), new TextureMap().register(TextureKey.of("all"), Identifier.of(blockId.getNamespace(), "block/building/" + blockId.getPath() + "/base")), blockStateModelGenerator.modelCollector);
                    new Model(Optional.of(Identifier.of("block/cube_all")), Optional.empty()).upload(Identifier.of(blockId.getNamespace(), "block/building/bulb/lit_powered/" + blockId.getPath()), new TextureMap().register(TextureKey.of("all"), Identifier.of(blockId.getNamespace(), "block/building/" + blockId.getPath() + "/lit_powered")), blockStateModelGenerator.modelCollector);
                    new Model(Optional.of(Identifier.of("block/cube_all")), Optional.empty()).upload(Identifier.of(blockId.getNamespace(), "block/building/bulb/lit/" + blockId.getPath()), new TextureMap().register(TextureKey.of("all"), Identifier.of(blockId.getNamespace(), "block/building/" + blockId.getPath() + "/lit")), blockStateModelGenerator.modelCollector);
                    new Model(Optional.of(Identifier.of("block/cube_all")), Optional.empty()).upload(Identifier.of(blockId.getNamespace(), "block/building/bulb/powered/" + blockId.getPath()), new TextureMap().register(TextureKey.of("all"), Identifier.of(blockId.getNamespace(), "block/building/" + blockId.getPath() + "/powered")), blockStateModelGenerator.modelCollector);

                }

                case LANTERN -> {
                    blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(buildingBlock.block()).coordinate(BlockStateVariantMap.create(Properties.HANGING)
                            .register(true, BlockStateVariant.create().put(VariantSettings.MODEL, Identifier.of(blockId.getNamespace(), "block/building/lantern/hanging/" + blockId.getPath())))
                            .register(false, BlockStateVariant.create().put(VariantSettings.MODEL, Identifier.of(blockId.getNamespace(), "block/building/lantern/" + blockId.getPath())))
                    ));
                    new Model(Optional.of(Identifier.of("block/template_lantern")), Optional.empty()).upload(Identifier.of(blockId.getNamespace(), "block/building/lantern/" + blockId.getPath()), new TextureMap().register(TextureKey.of("lantern"), Identifier.of(blockId.getNamespace(), "block/building/" + blockId.getPath())), blockStateModelGenerator.modelCollector);
                    new Model(Optional.of(Identifier.of("block/template_hanging_lantern")), Optional.empty()).upload(Identifier.of(blockId.getNamespace(), "block/building/lantern/hanging/" + blockId.getPath()), new TextureMap().register(TextureKey.of("lantern"), Identifier.of(blockId.getNamespace(), "block/building/" + blockId.getPath())), blockStateModelGenerator.modelCollector);
                }

                case ROD -> {
                    blockStateModelGenerator.blockStateCollector.accept(VariantsBlockStateSupplier.create(buildingBlock.block()).coordinate(BlockStateVariantMap.create(Properties.FACING)
                            .register(Direction.DOWN, BlockStateVariant.create().put(VariantSettings.MODEL, Identifier.of(blockId.getNamespace(), "block/building/rod/" + blockId.getPath())).put(VariantSettings.X, VariantSettings.Rotation.R180))
                            .register(Direction.EAST, BlockStateVariant.create().put(VariantSettings.MODEL, Identifier.of(blockId.getNamespace(), "block/building/rod/" + blockId.getPath())).put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R90))
                            .register(Direction.NORTH, BlockStateVariant.create().put(VariantSettings.MODEL, Identifier.of(blockId.getNamespace(), "block/building/rod/" + blockId.getPath())).put(VariantSettings.X, VariantSettings.Rotation.R90))
                            .register(Direction.SOUTH, BlockStateVariant.create().put(VariantSettings.MODEL, Identifier.of(blockId.getNamespace(), "block/building/rod/" + blockId.getPath())).put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R180))
                            .register(Direction.UP, BlockStateVariant.create().put(VariantSettings.MODEL, Identifier.of(blockId.getNamespace(), "block/building/rod/" + blockId.getPath())))
                            .register(Direction.WEST, BlockStateVariant.create().put(VariantSettings.MODEL, Identifier.of(blockId.getNamespace(), "block/building/rod/" + blockId.getPath())).put(VariantSettings.X, VariantSettings.Rotation.R90).put(VariantSettings.Y, VariantSettings.Rotation.R270))

                    ));
                    new Model(Optional.of(Identifier.of("block/end_rod")), Optional.empty()).upload(Identifier.of(blockId.getNamespace(), "block/building/rod/" + blockId.getPath()), new TextureMap().register(TextureKey.of("end_rod"), Identifier.of(blockId.getNamespace(), "block/building/" + blockId.getPath())).register(TextureKey.of("particle"), Identifier.of(blockId.getNamespace(), "block/building/" + blockId.getPath())), blockStateModelGenerator.modelCollector);
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
                case CUBE -> itemModelGenerator.register(buildingBlock.item(), new Model(Optional.of(Identifier.of(blockId.getNamespace(),"block/building/" + blockId.getPath())), Optional.empty()));
                case DOOR -> addTexturedItem(buildingBlock.item(), "building/door/" + blockId.getPath(), itemModelGenerator);
                case SLAB -> itemModelGenerator.register(buildingBlock.item(), new Model(Optional.of(Identifier.of(blockId.getNamespace(),"block/building/slabs/bot/" + blockId.getPath())), Optional.empty()));
                case STAIR -> itemModelGenerator.register(buildingBlock.item(), new Model(Optional.of(Identifier.of(blockId.getNamespace(),"block/building/stairs/base/" + blockId.getPath())), Optional.empty()));
                case WALL -> itemModelGenerator.register(buildingBlock.item(), new Model(Optional.of(Identifier.of(blockId.getNamespace(),"block/building/walls/" + blockId.getPath())), Optional.empty()));
                case BUTTON -> itemModelGenerator.register(buildingBlock.item(), new Model(Optional.of(Identifier.of(blockId.getNamespace(),"block/building/button/" + blockId.getPath())), Optional.empty()));
                case PRESSURE_PLATE -> itemModelGenerator.register(buildingBlock.item(), new Model(Optional.of(Identifier.of(blockId.getNamespace(),"block/building/pressure_plate/up/" + blockId.getPath())), Optional.empty()));
                case CRAFTING_TABLE -> itemModelGenerator.register(buildingBlock.item(), new Model(Optional.of(Identifier.of(blockId.getNamespace(),"block/building/crafting_table/" + blockId.getPath())), Optional.empty()));
                case BULB -> itemModelGenerator.register(buildingBlock.item(), new Model(Optional.of(Identifier.of(blockId.getNamespace(),"block/building/bulb/" + blockId.getPath())), Optional.empty()));
                case LANTERN -> addTexturedItem(buildingBlock.item(), "building/lantern/" + blockId.getPath(), itemModelGenerator);
                case ROD -> itemModelGenerator.register(buildingBlock.item(), new Model(Optional.of(Identifier.of(blockId.getNamespace(),"block/building/rod/" + blockId.getPath())), Optional.empty()));
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

    public TextureMap textureMap(BuildingBlockDataEntry dataEntry, Identifier id) {
        TextureMap map = new TextureMap();
        if(dataEntry.base() != null) {
            id = Registries.BLOCK.getId(dataEntry.base());
        }
        map.register(TextureKey.of("texture"), Identifier.of(id.getNamespace(), "block/building/" + id.getPath()));
        return map;
    }

    public TextureMap slabTextureMap(BuildingBlockDataEntry dataEntry, Identifier id) {
        TextureMap map = new TextureMap();
        if(dataEntry.base() != null) {
            id = Registries.BLOCK.getId(dataEntry.base());
        }
        map.register(TextureKey.of("bottom"), Identifier.of(id.getNamespace(), "block/building/" + id.getPath()));
        map.register(TextureKey.of("side"), Identifier.of(id.getNamespace(), "block/building/" + id.getPath()));
        map.register(TextureKey.of("top"), Identifier.of(id.getNamespace(), "block/building/" + id.getPath()));
        return map;
    }

    public TextureMap wallTextureMap(BuildingBlockDataEntry dataEntry, Identifier id) {
        TextureMap map = new TextureMap();
        if(dataEntry.base() != null) {
            id = Registries.BLOCK.getId(dataEntry.base());
        }
        map.register(TextureKey.of("wall"), Identifier.of(id.getNamespace(), "block/building/" + id.getPath()));
        return map;
    }

    public TextureMap craftingTable(BuildingBlockDataEntry dataEntry, Identifier id) {
        TextureMap map = new TextureMap();
        Identifier otherId = id;
        if(dataEntry.base() != null) {
            otherId = Registries.BLOCK.getId(dataEntry.base());
        }
        if(otherId.getNamespace().equalsIgnoreCase("minecraft")) {
            map.register(TextureKey.of("down"), Identifier.of(otherId.getNamespace(), "block/" + otherId.getPath()));
        } else {
            map.register(TextureKey.of("down"), Identifier.of(id.getNamespace(), "block/building/crafting_table/" + id.getPath().substring(0, id.getPath().indexOf("_")) + "/" + id.getPath()));
        }
        map.register(TextureKey.of("east"), Identifier.of(id.getNamespace(), "block/building/crafting_table/" + id.getPath().substring(0, id.getPath().indexOf("_")) + "/" + "side"));
        map.register(TextureKey.of("north"), Identifier.of(id.getNamespace(), "block/building/crafting_table/" + id.getPath().substring(0, id.getPath().indexOf("_")) + "/" + "front"));
        map.register(TextureKey.of("particle"), Identifier.of(id.getNamespace(), "block/building/crafting_table/" + id.getPath().substring(0, id.getPath().indexOf("_")) + "/" + "front"));
        map.register(TextureKey.of("south"), Identifier.of(id.getNamespace(), "block/building/crafting_table/" + id.getPath().substring(0, id.getPath().indexOf("_")) + "/" + "side"));
        map.register(TextureKey.of("up"), Identifier.of(id.getNamespace(), "block/building/crafting_table/" + id.getPath().substring(0, id.getPath().indexOf("_")) + "/" + "top"));
        map.register(TextureKey.of("west"), Identifier.of(id.getNamespace(), "block/building/crafting_table/" + id.getPath().substring(0, id.getPath().indexOf("_")) + "/" + "front"));

        return map;
    }

}
