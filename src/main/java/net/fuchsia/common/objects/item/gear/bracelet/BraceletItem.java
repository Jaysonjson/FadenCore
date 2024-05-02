package net.fuchsia.common.objects.item.gear.bracelet;

import net.fuchsia.common.objects.item.gear.Gear;
import net.fuchsia.common.slot.GearSlot;
import net.minecraft.item.Item;

public class BraceletItem extends Item implements Gear {

    public BraceletItem(Settings settings) {
        super(settings.maxCount(1));
    }

    @Override
    public GearSlot getGearType() {
        return GearSlot.BRACELET;
    }
}
