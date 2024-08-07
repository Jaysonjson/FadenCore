package net.fuchsia.common.objects.music_instance;

import net.fuchsia.common.objects.item.instrument.InstrumentType;
import net.minecraft.sound.SoundEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class InstrumentedMusic {

    private HashMap<InstrumentType, SoundEvent> instrumentTypes = new HashMap<>();
    protected String author = "";
    protected String name = "";
    protected String id = "";

    public String getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public String getName() {
        return name;
    }

    public HashMap<InstrumentType, SoundEvent> getInstrumentTypes() {
        return instrumentTypes;
    }
}
