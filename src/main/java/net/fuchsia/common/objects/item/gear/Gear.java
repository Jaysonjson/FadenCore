package net.fuchsia.common.objects.item.gear;

import net.fuchsia.common.slot.GearSlot;
import net.minecraft.item.ItemStack;

public interface Gear {

    GearSlot getGearType();
    default boolean freeWaterMovement(ItemStack itemStack) {
        return false;
    }

}
