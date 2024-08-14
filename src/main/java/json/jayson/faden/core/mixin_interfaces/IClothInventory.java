package json.jayson.faden.core.mixin_interfaces;

import json.jayson.faden.core.common.slot.ClothSlot;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.collection.DefaultedList;

public interface IClothInventory {

    DefaultedList<ItemStack> getClothes();
    ItemStack getClothOrArmor(EquipmentSlot slot, ClothSlot clothSlot);

}
