package net.fuchsia.common.init.items;

import net.fuchsia.common.init.FadenTabs;
import net.fuchsia.common.objects.item.instrument.InstrumentItem;
import net.fuchsia.common.objects.item.instrument.InstrumentType;
import net.minecraft.item.Item;

public class FadenInstruments {

    public static Item LUTE = FadenItems.registerItem("lute", new InstrumentItem(new Item.Settings(), InstrumentType.LUTE), "instruments/lute/default", FadenTabs.FADEN_INSTRUMENTS);
    public static Item PANFLUTE = FadenItems.registerItem("panflute", new InstrumentItem(new Item.Settings(), InstrumentType.PAN_FLUTE), "instruments/panflute/default", FadenTabs.FADEN_INSTRUMENTS);
    public static Item HURDY = FadenItems.registerItem("hurdy_gurdy", new InstrumentItem(new Item.Settings(), InstrumentType.HURDY), "test", FadenTabs.FADEN_INSTRUMENTS);
    public static Item DRUM = FadenItems.registerItem("drum", new InstrumentItem(new Item.Settings(), InstrumentType.DRUM), "test", FadenTabs.FADEN_INSTRUMENTS);


    public static void register() {}

}
