package json.jayson.faden.core.common.objects.item.gear.necklace;

import java.util.Collection;

import json.jayson.faden.core.common.objects.item.gear.FadenGearItem;
import json.jayson.faden.core.common.slot.GearSlot;
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
