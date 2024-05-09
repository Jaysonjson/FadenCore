package net.fuchsia.common.objects.item.gear;

import net.fuchsia.common.objects.item.ItemTier;
import net.fuchsia.common.slot.GearSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;

public interface Gear {

    GearSlot getGearType();
    ItemStack randomize(ItemStack itemStack, PlayerEntity player, ItemTier itemTier);
    ItemStack upgrade(Collection<ItemStack> inputs, ItemStack itemStack);
}
