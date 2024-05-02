package net.fuchsia.common.objects.item.gear;

import net.fuchsia.common.slot.GearSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

public interface Gear {

    GearSlot getGearType();
    default boolean freeWaterMovement(PlayerEntity player, ItemStack itemStack, boolean inWater) {
        return false;
    }

}
