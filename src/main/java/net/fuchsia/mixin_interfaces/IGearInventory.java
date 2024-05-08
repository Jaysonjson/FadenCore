package net.fuchsia.mixin_interfaces;

import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collection;

public interface IGearInventory {

     ItemStack getGearStack(int slot);
     Collection<ItemStack> getBracelets();
     ItemStack getNecklace();
     ItemStack getBelt();

     default Collection<ItemStack> getGears() {
          ArrayList<ItemStack> items = new ArrayList<>(getBracelets());
          items.add(getNecklace());
          items.add(getBelt());
          return items;
     }

}
