package net.fuchsia.common.objects.item.gear.necklace;

import net.fuchsia.common.objects.item.gear.Gear;
import net.fuchsia.common.slot.GearSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Collection;

public class NecklaceItem extends Item implements Gear {
    public NecklaceItem(Settings settings) {
        super(settings.maxCount(1));
    }

    @Override
    public GearSlot getGearType() {
        return GearSlot.NECKLACE;
    }

    @Override
    public ItemStack randomize(ItemStack itemStack) {
        return itemStack;
    }

    @Override
    public ItemStack upgrade(Collection<ItemStack> inputs, ItemStack itemStack) {
        return itemStack;
    }
}
