package json.jayson.faden.core.common.objects.item;

import json.jayson.faden.core.common.objects.cloth.FadenCoreCloth;
import json.jayson.faden.core.common.slot.ClothSlot;
import net.minecraft.item.Item;

public class FadenCoreClothItem extends Item implements IClothItem {

    FadenCoreCloth cloth;
    ClothSlot slot;

    public FadenCoreClothItem(Settings settings, FadenCoreCloth cloth, ClothSlot slot) {
        super(settings);
        this.cloth = cloth;
        this.slot = slot;
    }

    @Override
    public FadenCoreCloth getCloth() {
        return cloth;
    }

    @Override
    public ClothSlot getClothType() {
        return slot;
    }
}
