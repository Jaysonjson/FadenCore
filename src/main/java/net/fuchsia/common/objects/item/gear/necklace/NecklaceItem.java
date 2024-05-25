package net.fuchsia.common.objects.item.gear.necklace;

import java.util.Collection;

import net.fuchsia.common.objects.item.gear.FadenGearItem;
import net.fuchsia.common.slot.GearSlot;
import net.minecraft.item.ItemStack;

public abstract class NecklaceItem extends FadenGearItem {
    public NecklaceItem(Settings settings) {
        super(settings.maxCount(1));
    }

    @Override
    public GearSlot getGearType() {
        return GearSlot.NECKLACE;
    }

    @Override
    public ItemStack upgrade(Collection<ItemStack> inputs, ItemStack itemStack) {
        return null;
    }
}
