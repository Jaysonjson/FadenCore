package json.jayson.faden.core.common.data.listeners;

import json.jayson.faden.core.common.objects.item.instrument.InstrumentType;
import net.minecraft.sound.SoundEvent;

import java.util.ArrayList;
import java.util.HashMap;

public class InstrumentedMusicData {
    private String id;
    private String name;
    private String author;
    private HashMap<String, String> instruments = new HashMap<>();

    public String getName() {
        return name;
    }

    public String getAuthor() {
        return author;
    }

    public HashMap<String, String> getInstruments() {
        return instruments;
    }

    public String getId() {
        return id;
    }
}
