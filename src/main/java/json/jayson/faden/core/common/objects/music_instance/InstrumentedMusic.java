package json.jayson.faden.core.common.objects.music_instance;

import json.jayson.faden.core.FadenCore;
import json.jayson.faden.core.common.objects.item.instrument.InstrumentType;
import json.jayson.faden.core.registry.FadenCoreRegistry;
import json.jayson.faden.core.util.FadenCoreIdentifier;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

import java.util.HashMap;

public class InstrumentedMusic {

    private HashMap<InstrumentType, SoundEvent> instrumentTypes = new HashMap<>();
    protected String author = "";
    protected String name = "";
    protected String id = "";

    public InstrumentedMusic() {

    }

    public InstrumentedMusic(String author, String name, HashMap<String, String> map) {
        this.author = author;
        this.name = name;
        for (String value : map.keySet()) {
            InstrumentType type = FadenCoreRegistry.INSTRUMENT.get(Identifier.of(value));
            SoundEvent event = Registries.SOUND_EVENT.get(Identifier.of(map.get(value)));
            if (type != null && event != null) {
                instrumentTypes.put(type, event);
            } else {
                FadenCore.LOGGER.error("Could not register InstrumentedMusic " + name + "due to false map");
            }
        }
    }

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
