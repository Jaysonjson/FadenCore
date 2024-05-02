package net.fuchsia.common.objects.item.coin;

import net.minecraft.item.ItemStack;

public interface IValue {
	
	int getValue(ItemStack stack);
	int getBuyValue(ItemStack stack);
}
