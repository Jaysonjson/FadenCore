package json.jayson.faden.core.datagen.holders;

import json.jayson.faden.core.datagen.DataItemModel;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;

public record FadenDataItem(Item item, String texture, DataItemModel itemModel, ItemGroup group) {
}
