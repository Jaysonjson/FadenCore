package net.fuchsia.common.init.blocks;

import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.fuchsia.common.objects.block.FadenCraftingTable;
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

    public static Block GRANITE_TILES = registerBlock("granite_tiles", new Block(AbstractBlock.Settings.create()), null, DataToolType.PICKAXE, DataToolTier.WOOD, DataBlockType.CUBE, true, true);
    public static Block GRANITE_TILES_SLAB = registerBlock("granite_tiles_slab", new SlabBlock(AbstractBlock.Settings.create()), GRANITE_TILES, DataToolType.PICKAXE, DataToolTier.WOOD, DataBlockType.SLAB, true, true);
    public static Block GRANITE_TILES_WALL = registerBlock("granite_tiles_wall", new WallBlock(AbstractBlock.Settings.create()), GRANITE_TILES, DataToolType.PICKAXE, DataToolTier.WOOD, DataBlockType.WALL, true, true);
    public static Block GRANITE_TILES_STAIRS = registerBlock("granite_tiles_stairs", new StairsBlock(GRANITE_TILES.getDefaultState(), AbstractBlock.Settings.create()), GRANITE_TILES, DataToolType.PICKAXE, DataToolTier.WOOD, DataBlockType.STAIR, true, true);
    public static Block GRANITE_TILES_BUTTON = registerBlock("granite_tiles_button", new ButtonBlock(BlockSetType.STONE, 20, AbstractBlock.Settings.create()), GRANITE_TILES, DataToolType.PICKAXE, DataToolTier.WOOD, DataBlockType.BUTTON, true, true);
    public static Block GRANITE_TILES_PRESSURE_PLATE = registerBlock("granite_tiles_pressure_plate", new PressurePlateBlock(BlockSetType.STONE, AbstractBlock.Settings.create()), GRANITE_TILES, DataToolType.PICKAXE, DataToolTier.WOOD, DataBlockType.PRESSURE_PLATE, true, true);


    public static Block GRANUD_TILES = registerBlock("granud_tiles", new Block(AbstractBlock.Settings.create()), null, DataToolType.PICKAXE, DataToolTier.WOOD, DataBlockType.CUBE, true, true);
    public static Block GRANUD_BRICK_SLAB = registerBlock("granud_brick_slab", new SlabBlock(AbstractBlock.Settings.create()), GRANUD_TILES, DataToolType.PICKAXE, DataToolTier.WOOD, DataBlockType.SLAB, true, true);
    public static Block GRANUD_BRICK_WALL = registerBlock("granud_brick_wall", new WallBlock(AbstractBlock.Settings.create()), GRANUD_TILES, DataToolType.PICKAXE, DataToolTier.WOOD, DataBlockType.WALL, true, true);
    public static Block GRANUD_BRICK_STAIRS = registerBlock("granud_brick_stairs", new StairsBlock(GRANUD_TILES.getDefaultState(), AbstractBlock.Settings.create()), GRANUD_TILES, DataToolType.PICKAXE, DataToolTier.WOOD, DataBlockType.STAIR, true, true);
    public static Block GRANUD_BRICK_BUTTON = registerBlock("granud_brick_button", new ButtonBlock(BlockSetType.STONE, 20, AbstractBlock.Settings.create()), GRANUD_TILES, DataToolType.PICKAXE, DataToolTier.WOOD, DataBlockType.BUTTON, true, true);
    public static Block GRANUD_BRICK_PRESSURE_PLATE = registerBlock("granud_brick_pressure_plate", new PressurePlateBlock(BlockSetType.STONE, AbstractBlock.Settings.create()), GRANUD_TILES, DataToolType.PICKAXE, DataToolTier.WOOD, DataBlockType.PRESSURE_PLATE, true, true);


    public static Block TITANITOL_BLOCK = registerBlock("titanitol_block", new Block(AbstractBlock.Settings.create()), null, DataToolType.PICKAXE, DataToolTier.IRON, DataBlockType.CUBE, true, true);
    public static Block TITANITOL_SLAB = registerBlock("titanitol_slab", new SlabBlock(AbstractBlock.Settings.create()), TITANITOL_BLOCK, DataToolType.PICKAXE, DataToolTier.IRON, DataBlockType.SLAB, true, true);
    public static Block TITANITOL_WALL = registerBlock("titanitol_wall", new WallBlock(AbstractBlock.Settings.create()), TITANITOL_BLOCK, DataToolType.PICKAXE, DataToolTier.IRON, DataBlockType.WALL, true, true);
    public static Block TITANITOL_STAIRS = registerBlock("titanitol_stairs", new StairsBlock(TITANITOL_BLOCK.getDefaultState(), AbstractBlock.Settings.create()), TITANITOL_BLOCK, DataToolType.PICKAXE, DataToolTier.IRON, DataBlockType.STAIR, true, true);
    public static Block TITANITOL_BUTTON = registerBlock("titanitol_button", new ButtonBlock(BlockSetType.STONE, 20, AbstractBlock.Settings.create()), TITANITOL_BLOCK, DataToolType.PICKAXE, DataToolTier.IRON, DataBlockType.BUTTON, true, true);
    public static Block TITANITOL_PRESSURE_PLATE = registerBlock("titanitol_pressure_plate", new PressurePlateBlock(BlockSetType.STONE, AbstractBlock.Settings.create()), TITANITOL_BLOCK, DataToolType.PICKAXE, DataToolTier.IRON, DataBlockType.PRESSURE_PLATE, true, true);


    public static Block CHERRY_CRAFTING_TABLE = registerBlock("cherry_crafting_table", new FadenCraftingTable(AbstractBlock.Settings.create()), Blocks.CHERRY_PLANKS, DataToolType.AXE, DataToolTier.NONE, DataBlockType.CRAFTING_TABLE, true, true);
    public static Block WARPED_CRAFTING_TABLE = registerBlock("warped_crafting_table", new FadenCraftingTable(AbstractBlock.Settings.create()), Blocks.WARPED_PLANKS, DataToolType.AXE, DataToolTier.NONE, DataBlockType.CRAFTING_TABLE, true, true);
    public static Block SPRUCE_CRAFTING_TABLE = registerBlock("spruce_crafting_table", new FadenCraftingTable(AbstractBlock.Settings.create()), Blocks.SPRUCE_PLANKS, DataToolType.AXE, DataToolTier.NONE, DataBlockType.CRAFTING_TABLE, true, true);
    public static Block MANGROVE_CRAFTING_TABLE = registerBlock("mangrove_crafting_table", new FadenCraftingTable(AbstractBlock.Settings.create()), Blocks.MANGROVE_PLANKS, DataToolType.AXE, DataToolTier.NONE, DataBlockType.CRAFTING_TABLE, true, true);
    public static Block JUNGLE_CRAFTING_TABLE = registerBlock("jungle_crafting_table", new FadenCraftingTable(AbstractBlock.Settings.create()), Blocks.JUNGLE_PLANKS, DataToolType.AXE, DataToolTier.NONE, DataBlockType.CRAFTING_TABLE, true, true);
    public static Block DARK_OAK_CRAFTING_TABLE = registerBlock("dark_oak_crafting_table", new FadenCraftingTable(AbstractBlock.Settings.create()), Blocks.DARK_OAK_PLANKS, DataToolType.AXE, DataToolTier.NONE, DataBlockType.CRAFTING_TABLE, true, true);
    public static Block CRIMSON_CRAFTING_TABLE = registerBlock("crimson_crafting_table", new FadenCraftingTable(AbstractBlock.Settings.create()), Blocks.CRIMSON_PLANKS, DataToolType.AXE, DataToolTier.NONE, DataBlockType.CRAFTING_TABLE, true, true);
    public static Block BAMBOO_CRAFTING_TABLE = registerBlock("bamboo_crafting_table", new FadenCraftingTable(AbstractBlock.Settings.create()), Blocks.BAMBOO_PLANKS, DataToolType.AXE, DataToolTier.NONE, DataBlockType.CRAFTING_TABLE, true, true);
    public static Block BIRCH_CRAFTING_TABLE = registerBlock("birch_crafting_table", new FadenCraftingTable(AbstractBlock.Settings.create()), Blocks.BIRCH_PLANKS, DataToolType.AXE, DataToolTier.NONE, DataBlockType.CRAFTING_TABLE, true, true);
    public static Block ACACIA_CRAFTING_TABLE = registerBlock("acacia_crafting_table", new FadenCraftingTable(AbstractBlock.Settings.create()), Blocks.ACACIA_PLANKS, DataToolType.AXE, DataToolTier.NONE, DataBlockType.CRAFTING_TABLE, true, true);


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
