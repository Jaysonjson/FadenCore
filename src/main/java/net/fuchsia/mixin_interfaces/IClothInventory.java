package net.fuchsia.mixin_interfaces;

import net.fuchsia.common.slot.ClothSlot;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public interface IClothInventory {

    DefaultedList<ItemStack> getClothes();
    ItemStack getClothOrArmor(EquipmentSlot slot, ClothSlot clothSlot);

}
