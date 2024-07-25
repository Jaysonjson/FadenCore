package net.fuchsia.datagen.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricTagProvider;
import net.fuchsia.common.init.FadenItems;
import net.fuchsia.datagen.FadenDataItem;
import net.minecraft.item.DiscFragmentItem;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.registry.tag.ItemTags;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class FadenItemTagProvider extends FabricTagProvider.ItemTagProvider {
    public FadenItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture, @Nullable FabricTagProvider.BlockTagProvider blockTagProvider) {
        super(output, completableFuture, blockTagProvider);
    }

    public FadenItemTagProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> completableFuture) {
        super(output, completableFuture);
    }

    @Override
    protected void configure(RegistryWrapper.WrapperLookup arg) {
        /*for (FadenDataItem item : FadenItems.ITEMS) {
            if(item.item() instanceof DiscFragmentItem) {
                getOrCreateTagBuilder(ItemTags.DIS)
                        .add(item.item());
            }
        }*/
    }
}
