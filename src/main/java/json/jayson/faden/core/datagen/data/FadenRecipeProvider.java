package json.jayson.faden.core.datagen.data;

import json.jayson.faden.core.datagen.FadenCoreDataGen;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import json.jayson.faden.core.datagen.holders.BuildingBlockDataEntry;
import net.minecraft.block.Block;
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
        for (BuildingBlockDataEntry buildingBlock : FadenCoreDataGen.BLOCKS) {
            if(buildingBlock.base() == null) continue;
            if(!FadenCoreDataGen.MOD_ID.isBlank() && !Registries.BLOCK.getId(buildingBlock.block()).getNamespace().equalsIgnoreCase(FadenCoreDataGen.MOD_ID)) continue;
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
                .criterion(hasItem(in), conditionsFromItem(in)).offerTo(exporter, Registries.BLOCK.getId(out));
    }

    public static void fourByFour(Block out, Block in, RecipeExporter exporter, int count) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, out, count)
                .input('R', in)
                .pattern("RR")
                .pattern("RR")
                .criterion(hasItem(in), conditionsFromItem(in)).offerTo(exporter, Registries.BLOCK.getId(out));
    }

    public static void slabRecipe(Block out, Block in, RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, out, 6)
                .input('R', in)
                .pattern("RRR")
                .criterion(hasItem(in), conditionsFromItem(in)).offerTo(exporter, Registries.BLOCK.getId(out));
    }

    public static void stairRecipe(Block out, Block in, RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, out, 4)
                .input('R', in)
                .pattern("R  ")
                .pattern("RR ")
                .pattern("RRR")
                .criterion(hasItem(in), conditionsFromItem(in)).offerTo(exporter, Registries.BLOCK.getId(out));
    }

    public static void stairAltRecipe(Block out, Block in, RecipeExporter exporter) {
         ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, out, 4)
                .input('R', in)
                .pattern("  R")
                .pattern(" RR")
                .pattern("RRR")
                .criterion(hasItem(in), conditionsFromItem(in)).offerTo(exporter, Registries.BLOCK.getId(out) + "_alt");
    }

    public static void doorRecipe(Block out, Block in, RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, out, 3)
                .input('R', in)
                .pattern("RR")
                .pattern("RR")
                .pattern("RR")
                .criterion(hasItem(in), conditionsFromItem(in)).offerTo(exporter, Registries.BLOCK.getId(out));
    }

    public static void wallRecipe(Block out, Block in, RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, out, 6)
                .input('R', in)
                .pattern("RRR")
                .pattern("RRR")
                .criterion(hasItem(in), conditionsFromItem(in)).offerTo(exporter, Registries.BLOCK.getId(out));
    }

    public static void buttonRecipe(Block out, Block in, RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, out, 1)
                .input('R', in)
                .pattern("R")
                .criterion(hasItem(in), conditionsFromItem(in)).offerTo(exporter, Registries.BLOCK.getId(out));
    }

    public static void pressurePlateRecipe(Block out, Block in, RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, out, 1)
                .input('R', in)
                .pattern("RR")
                .criterion(hasItem(in), conditionsFromItem(in)).offerTo(exporter, Registries.BLOCK.getId(out));
    }

}
