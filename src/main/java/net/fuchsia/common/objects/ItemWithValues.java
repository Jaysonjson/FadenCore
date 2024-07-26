package net.fuchsia.common.objects;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.LinkedHashMap;

public interface ItemWithValues {

    LinkedHashMap<Item, Integer> getValues(ItemStack itemStack);
    void resetItemMap();

}
