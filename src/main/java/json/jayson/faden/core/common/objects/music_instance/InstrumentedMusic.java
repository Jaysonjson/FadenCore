package json.jayson.faden.core.common.objects.music_instance;

import json.jayson.faden.core.common.objects.item.instrument.InstrumentType;
import net.minecraft.sound.SoundEvent;

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
