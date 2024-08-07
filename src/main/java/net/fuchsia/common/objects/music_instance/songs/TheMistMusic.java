package net.fuchsia.common.objects.music_instance.songs;

import net.fuchsia.common.init.FadenSoundEvents;
import net.fuchsia.common.objects.item.instrument.InstrumentType;
import net.fuchsia.common.objects.music_instance.InstrumentedMusic;
import net.fuchsia.util.FadenIdentifier;

public class TheMistMusic extends InstrumentedMusic {

    public TheMistMusic() {
        getInstrumentTypes().put(InstrumentType.LUTE, FadenSoundEvents.THE_MIST_LUTE);
        getInstrumentTypes().put(InstrumentType.HURDY, FadenSoundEvents.THE_MIST_HURTY);
        getInstrumentTypes().put(InstrumentType.PAN_FLUTE, FadenSoundEvents.THE_MIST_PANFLUTE);
        getInstrumentTypes().put(InstrumentType.DRUM, FadenSoundEvents.THE_MIST_DRUM);
        author = "Lucas Burnett";
        name = "The Mist";
    }

    @Override
    public String getId() {
        return FadenIdentifier.create("the_mist").toString();
    }

}
