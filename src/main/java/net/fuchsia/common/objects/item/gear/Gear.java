package net.fuchsia.common.objects.item.gear;

import net.fuchsia.common.objects.item.ItemTier;
import net.fuchsia.common.slot.GearSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.Collection;

public interface Gear {

    GearSlot getGearType();
    default boolean freeWaterMovement(PlayerEntity player, ItemStack itemStack, boolean inWater) {
        return false;
    }

    ItemStack randomize(ItemStack itemStack, PlayerEntity player, ItemTier itemTier);
    ItemStack upgrade(Collection<ItemStack> inputs, ItemStack itemStack);
}
