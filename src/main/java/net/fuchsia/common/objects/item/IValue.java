package net.fuchsia.common.objects.item;

import net.minecraft.item.ItemStack;

public interface IValue {
	
	int getValue(ItemStack stack);
	int getBuyValue(ItemStack stack);
}
