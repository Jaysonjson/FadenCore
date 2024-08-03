package net.fuchsia.common.objects.music_instance;

import net.fuchsia.common.objects.item.instrument.InstrumentType;

import java.util.ArrayList;

public class InstrumentedMusic {

    private ArrayList<InstrumentType> instrumentTypes = new ArrayList<>();

    public ArrayList<InstrumentType> getInstrumentTypes() {
        return instrumentTypes;
    }
}
