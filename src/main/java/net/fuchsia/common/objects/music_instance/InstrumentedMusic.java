package net.fuchsia.common.objects.music_instance;

import net.fuchsia.common.objects.item.instrument.InstrumentType;
import net.minecraft.sound.SoundEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class InstrumentedMusic {

    private HashMap<InstrumentType, SoundEvent> instrumentTypes = new HashMap<>();

    public HashMap<InstrumentType, SoundEvent> getInstrumentTypes() {
        return instrumentTypes;
    }
}
