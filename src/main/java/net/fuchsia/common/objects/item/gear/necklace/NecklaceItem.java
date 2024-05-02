package net.fuchsia.common.objects.item.gear.necklace;

import net.fuchsia.common.objects.item.gear.Gear;
import net.fuchsia.common.slot.GearSlot;
import net.minecraft.item.Item;

public class NecklaceItem extends Item implements Gear {
    public NecklaceItem(Settings settings) {
        super(settings.maxCount(1));
    }

    @Override
    public GearSlot getGearType() {
        return GearSlot.NECKLACE;
    }
}
