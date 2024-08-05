package net.fuchsia.datagen.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.fuchsia.common.init.blocks.FadenBuildingBlocks;
import net.fuchsia.datagen.holders.BuildingBlockDataEntry;
import net.fuchsia.util.FadenIdentifier;
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
        for (BuildingBlockDataEntry buildingBlock : FadenBuildingBlocks.BUILDING_BLOCKS) {
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
            }
        }
    }

    public void slabRecipe(Block out, Block in, RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, out, 8)
                .input('#', in)
                .pattern("RRR")
                .criterion(hasItem(in), conditionsFromItem(in)).offerTo(exporter, FadenIdentifier.create(Registries.BLOCK.getId(out).getPath()));
    }

    public void stairRecipe(Block out, Block in, RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, out, 8)
                .input('#', in)
                .pattern("R")
                .pattern("RR")
                .pattern("RRR")
                .criterion(hasItem(in), conditionsFromItem(in)).offerTo(exporter, FadenIdentifier.create(Registries.BLOCK.getId(out).getPath()));
    }

    public ShapedRecipeJsonBuilder stairAltRecipe(Block out, Block in, RecipeExporter exporter) {
         ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, out, 8)
                .input('#', in)
                .pattern("  R")
                .pattern(" RR")
                .pattern("RRR")
                .criterion(hasItem(in), conditionsFromItem(in)).offerTo(exporter, FadenIdentifier.create(Registries.BLOCK.getId(out).getPath() + "_alt")));
    }

    public void doorRecipe(Block out, Block in, RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, out, 8)
                .input('#', in)
                .pattern("RR")
                .pattern("RR")
                .pattern("RR")
                .criterion(hasItem(in), conditionsFromItem(in)).offerTo(exporter, FadenIdentifier.create(Registries.BLOCK.getId(out).getPath()));
    }

    public void wallRecipe(Block out, Block in, RecipeExporter exporter) {
        ShapedRecipeJsonBuilder.create(RecipeCategory.BUILDING_BLOCKS, out, 8)
                .input('#', in)
                .pattern("RRR")
                .pattern("RRR")
                .criterion(hasItem(in), conditionsFromItem(in)).offerTo(exporter, FadenIdentifier.create(Registries.BLOCK.getId(out).getPath()));
    }

}
