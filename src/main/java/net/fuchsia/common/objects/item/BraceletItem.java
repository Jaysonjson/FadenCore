package net.fuchsia.common.objects.item;

import net.fuchsia.common.slot.GearSlot;
import net.minecraft.item.Item;

public class BraceletItem extends Item implements Gear {

    public BraceletItem(Settings settings) {
        super(settings);
    }

    @Override
    public GearSlot getGearType() {
        return GearSlot.BRACELET;
    }
}
