package net.fuchsia.common.init.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fuchsia.datagen.DataBlockType;
import net.fuchsia.datagen.DataToolTier;
import net.fuchsia.datagen.DataToolType;
import net.fuchsia.datagen.holders.BuildingBlockDataEntry;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.block.*;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class FadenBuildingBlocks {

    public static List<BuildingBlockDataEntry> BUILDING_BLOCKS  = new ArrayList<>();

    public static Block GRANITE_BRICKS = registerBlock("granite_bricks", new Block(AbstractBlock.Settings.create()), null, DataToolType.PICKAXE, DataToolTier.WOOD, DataBlockType.CUBE, true, true);
    public static Block GRANITE_BRICK_SLAB = registerBlock("granite_brick_slab", new SlabBlock(AbstractBlock.Settings.create()), GRANITE_BRICKS, DataToolType.PICKAXE, DataToolTier.WOOD, DataBlockType.SLAB, true, true);
    public static Block GRANITE_BRICK_WALL = registerBlock("granite_brick_wall", new WallBlock(AbstractBlock.Settings.create()), GRANITE_BRICKS, DataToolType.PICKAXE, DataToolTier.WOOD, DataBlockType.WALL, true, true);
    public static Block GRANITE_BRICK_STAIRS = registerBlock("granite_brick_stairs", new StairsBlock(GRANITE_BRICKS.getDefaultState(), AbstractBlock.Settings.create()), GRANITE_BRICKS, DataToolType.PICKAXE, DataToolTier.WOOD, DataBlockType.STAIR, true, true);
    public static Block GRANITE_BRICK_BUTTON = registerBlock("granite_brick_button", new ButtonBlock(BlockSetType.STONE, 20, AbstractBlock.Settings.create()), GRANITE_BRICKS, DataToolType.PICKAXE, DataToolTier.WOOD, DataBlockType.BUTTON, true, true);
    public static Block GRANITE_BRICK_PRESSURE_PLATE = registerBlock("granite_brick_pressure_plate", new PressurePlateBlock(BlockSetType.STONE, AbstractBlock.Settings.create()), GRANITE_BRICKS, DataToolType.PICKAXE, DataToolTier.WOOD, DataBlockType.PRESSURE_PLATE, true, true);

    public static Block GRANUD_TILES = registerBlock("granud_tiles", new Block(AbstractBlock.Settings.create()), null, DataToolType.PICKAXE, DataToolTier.WOOD, DataBlockType.CUBE, true, true);
    public static Block GRANUD_BRICK_SLAB = registerBlock("granud_brick_slab", new SlabBlock(AbstractBlock.Settings.create()), GRANUD_TILES, DataToolType.PICKAXE, DataToolTier.WOOD, DataBlockType.SLAB, true, true);
    public static Block GRANUD_BRICK_WALL = registerBlock("granud_brick_wall", new WallBlock(AbstractBlock.Settings.create()), GRANUD_TILES, DataToolType.PICKAXE, DataToolTier.WOOD, DataBlockType.WALL, true, true);
    public static Block GRANUD_BRICK_STAIRS = registerBlock("granud_brick_stairs", new StairsBlock(GRANUD_TILES.getDefaultState(), AbstractBlock.Settings.create()), GRANUD_TILES, DataToolType.PICKAXE, DataToolTier.WOOD, DataBlockType.STAIR, true, true);
    public static Block GRANUD_BRICK_BUTTON = registerBlock("granud_brick_button", new ButtonBlock(BlockSetType.STONE, 20, AbstractBlock.Settings.create()), GRANITE_BRICKS, DataToolType.PICKAXE, DataToolTier.WOOD, DataBlockType.BUTTON, true, true);
    public static Block GRANUD_BRICK_PRESSURE_PLATE = registerBlock("granud_brick_pressure_plate", new PressurePlateBlock(BlockSetType.STONE, AbstractBlock.Settings.create()), GRANITE_BRICKS, DataToolType.PICKAXE, DataToolTier.WOOD, DataBlockType.PRESSURE_PLATE, true, true);


    protected static Block registerBlock(String name, Block block, @Nullable Block base, DataToolType toolType, DataToolTier tier, DataBlockType blockType, boolean dropSelf, boolean language) {
        Block registeredBlock = Registry.register(Registries.BLOCK, FadenIdentifier.create(name), block);
        Registry.register(Registries.ITEM, FadenIdentifier.create(name), new BlockItem(registeredBlock, new Item.Settings()));
        BUILDING_BLOCKS.add(new BuildingBlockDataEntry(block, base, toolType, tier, blockType, dropSelf, language));
        return registeredBlock;
    }


    protected static Block registerBlock(String name, Block block, DataToolType toolType, DataToolTier tier, DataBlockType blockType) {
        return registerBlock(name, block, null, toolType, tier, blockType, true, true);
    }

    protected static Block registerBlock(String name, Block block, DataBlockType blockType) {
        return registerBlock(name, block, null, DataToolType.NONE, DataToolTier.NONE,  blockType, true, true);
    }

    protected static Block registerBlock(String name, Block block, DataToolType toolType, DataBlockType blockType) {
        return registerBlock(name, block, null, toolType, DataToolTier.NONE,  blockType, true, true);
    }

    protected static Block registerBlock(String name, Block block, DataBlockType blockType, boolean dropSelf) {
        return registerBlock(name, block, null, DataToolType.NONE, DataToolTier.NONE,  blockType, dropSelf, true);
    }

    protected static Block registerBlock(String name, Block block, DataToolType toolType, DataBlockType blockType, boolean dropSelf) {
        return registerBlock(name, block, null, toolType, DataToolTier.NONE,  blockType, dropSelf, true);
    }

    public static void register() {}


}
