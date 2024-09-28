package json.jayson.faden.core.common.objects.item;

import json.jayson.faden.core.common.cloth.FadenCoreCloth;
import json.jayson.faden.core.common.slot.ClothSlot;

public interface IClothItem {

    FadenCoreCloth getCloth();
    ClothSlot getClothType();

}
