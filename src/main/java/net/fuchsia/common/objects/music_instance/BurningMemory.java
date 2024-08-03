package net.fuchsia.common.objects.music_instance;

import net.fuchsia.common.init.FadenSoundEvents;
import net.fuchsia.common.objects.item.instrument.InstrumentType;

public class BurningMemory extends InstrumentedMusic {

    public BurningMemory() {
        getInstrumentTypes().put(InstrumentType.LUTE, FadenSoundEvents.BURNING_MEMORY_LUTE);
        getInstrumentTypes().put(InstrumentType.HURDY, FadenSoundEvents.BURNING_MEMORY_HURTY);
        getInstrumentTypes().put(InstrumentType.PAN_FLUTE, FadenSoundEvents.BURNING_MEMORY_PANFLUTE);
        getInstrumentTypes().put(InstrumentType.DRUM, FadenSoundEvents.BURNING_MEMORY_DRUM);
    }

}
