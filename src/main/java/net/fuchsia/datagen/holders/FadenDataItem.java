package net.fuchsia.datagen.holders;

import net.fuchsia.datagen.DataItemModel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public record FadenDataItem(Item item, String texture, DataItemModel itemModel, ItemGroup group) {
}
