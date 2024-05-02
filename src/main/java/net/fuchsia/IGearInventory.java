package net.fuchsia;

import net.minecraft.item.ItemStack;

import java.util.Collection;

public interface IGearInventory {

     ItemStack getGearStack(int slot);
     Collection<ItemStack> getBracelets();
     ItemStack getNecklace();
     ItemStack getBelt();

}
