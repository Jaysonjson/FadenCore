package net.fuchsia.datagen.holders;

import net.fuchsia.datagen.DataItemModel;
import net.minecraft.item.Item;

public record FadenDataItem(Item item, String texture, DataItemModel itemModel) {
}
