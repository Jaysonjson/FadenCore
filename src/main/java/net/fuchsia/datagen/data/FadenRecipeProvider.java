package net.fuchsia.datagen.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fuchsia.common.init.blocks.FadenCoreBlocks;
import net.fuchsia.datagen.holders.BuildingBlockDataEntry;
import net.fuchsia.util.FadenCoreIdentifier;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class FadenRecipeProvider extends FabricRecipeProvider {
    public FadenRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture) {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter) {
        for (BuildingBlockDataEntry buildingBlock : FadenCoreBlocks.BUILDING_BLOCKS) {
            if(buildingBlock.base() == null) continue;
            switch (buildingBlock.blockType()) {
                case SLAB -> {
                    slabRecipe(buildingBlock.block(), buildingBlock.base(), exporter);
                }
                case STAIR ->  {
                    stairRecipe(buildingBlock.block(), buildingBlock.base(), exporter);
                    stairAltRecipe(buildingBlock.block(), buildingBlock.base(), exporter);
                }
                case DOOR -> {
                    doorRecipe(buildingBlock.block(), buildingBlock.base(), exporter);
                }
                case WALL -> {
                    wallRecipe(buildingBlock.block(), buildingBlock.base(), exporter);
                }
                case BUTTON -> {
                    buttonRecipe(buildingBlock.block(), buildingBlock.base(), exporter);
                }
                case PRESSURE_PLATE -> {
                    pressurePlateRecipe(buildingBlock.block(), buildingBlock.base(), exporter);
                }
                case CRAFTING_TABLE -> {
                    fourByFour(buildingBlock.block(), buildingBlock.base(), exporter, 1);
                }
            }
        }
    }

    public static void fourByFour(Block out, Block in, RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, out, 4)
                .input('R', in)
                .pattern("RR")
                .pattern("RR")
                .criterion(hasItem(in), conditionsFromItem(in)).offerTo(exporter, FadenCoreIdentifier.create(Registries.BLOCK.getId(out).getPath()));
    }

    public static void fourByFour(Block out, Block in, RecipeExporter exporter, int count) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, out, count)
                .input('R', in)
                .pattern("RR")
                .pattern("RR")
                .criterion(hasItem(in), conditionsFromItem(in)).offerTo(exporter, FadenCoreIdentifier.create(Registries.BLOCK.getId(out).getPath()));
    }

    public static void slabRecipe(Block out, Block in, RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, out, 6)
                .input('R', in)
                .pattern("RRR")
                .criterion(hasItem(in), conditionsFromItem(in)).offerTo(exporter, FadenCoreIdentifier.create(Registries.BLOCK.getId(out).getPath()));
    }

    public static void stairRecipe(Block out, Block in, RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, out, 4)
                .input('R', in)
                .pattern("R  ")
                .pattern("RR ")
                .pattern("RRR")
                .criterion(hasItem(in), conditionsFromItem(in)).offerTo(exporter, FadenCoreIdentifier.create(Registries.BLOCK.getId(out).getPath()));
    }

    public static void stairAltRecipe(Block out, Block in, RecipeExporter exporter) {
         ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, out, 4)
                .input('R', in)
                .pattern("  R")
                .pattern(" RR")
                .pattern("RRR")
                .criterion(hasItem(in), conditionsFromItem(in)).offerTo(exporter, FadenCoreIdentifier.create(Registries.BLOCK.getId(out).getPath() + "_alt"));
    }

    public static void doorRecipe(Block out, Block in, RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, out, 3)
                .input('R', in)
                .pattern("RR")
                .pattern("RR")
                .pattern("RR")
                .criterion(hasItem(in), conditionsFromItem(in)).offerTo(exporter, FadenCoreIdentifier.create(Registries.BLOCK.getId(out).getPath()));
    }

    public static void wallRecipe(Block out, Block in, RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, out, 6)
                .input('R', in)
                .pattern("RRR")
                .pattern("RRR")
                .criterion(hasItem(in), conditionsFromItem(in)).offerTo(exporter, FadenCoreIdentifier.create(Registries.BLOCK.getId(out).getPath()));
    }

    public static void buttonRecipe(Block out, Block in, RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, out, 1)
                .input('R', in)
                .pattern("R")
                .criterion(hasItem(in), conditionsFromItem(in)).offerTo(exporter, FadenCoreIdentifier.create(Registries.BLOCK.getId(out).getPath()));
    }

    public static void pressurePlateRecipe(Block out, Block in, RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, out, 1)
                .input('R', in)
                .pattern("RR")
                .criterion(hasItem(in), conditionsFromItem(in)).offerTo(exporter, FadenCoreIdentifier.create(Registries.BLOCK.getId(out).getPath()));
    }

}
