package json.jayson.faden.core.common.data.listeners;

import json.jayson.faden.core.FadenCore;
import json.jayson.faden.core.common.objects.item.instrument.InstrumentType;
import json.jayson.faden.core.common.objects.music_instance.InstrumentedMusic;
import json.jayson.faden.core.registry.FadenCoreRegistry;
import json.jayson.faden.core.util.FadenCoreIdentifier;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.minecraft.registry.Registry;
import net.minecraft.resource.Resource;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import org.apache.commons.io.IOUtils;

import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class InstrumentedMusicDataListener implements SimpleSynchronousResourceReloadListener {
    @Override
    public Identifier getFabricId() {
        return FadenCoreIdentifier.create("instrumented_music_data");
    }

    //TODO, maybe DONT unfreeze the registry
    @Override
    public void reload(ResourceManager manager) {
        Map<Identifier, Resource> map =  manager.findResources("instruments", id -> id.getPath().endsWith(".json"));
        for (Identifier id : map.keySet()) {
            try(InputStream stream = manager.getResource(id).get().getInputStream()) {
                InstrumentTypeData typeData = FadenCore.GSON.fromJson(IOUtils.toString(stream, StandardCharsets.UTF_8), InstrumentTypeData.class);
                if(!FadenCoreRegistry.INSTRUMENT.containsId(FadenCoreIdentifier.create(typeData.getType()))) {
                    FadenCoreRegistry.INSTRUMENT.frozen = false;
                    Registry.register(FadenCoreRegistry.INSTRUMENT, FadenCoreIdentifier.create(typeData.getType()), new InstrumentType(typeData.getType()));
                    FadenCoreRegistry.INSTRUMENT.freeze();
                }
            } catch(Exception e) {
                FadenCore.LOGGER.error("Could not load instrumented music data from {}", id, e);
            }
        }


        map =  manager.findResources("instrumented_music", id -> id.getPath().endsWith(".json"));
        for (Identifier id : map.keySet()) {
            try(InputStream stream = manager.getResource(id).get().getInputStream()) {
                InstrumentedMusicData typeData = FadenCore.GSON.fromJson(IOUtils.toString(stream, StandardCharsets.UTF_8), InstrumentedMusicData.class);
                if(!FadenCoreRegistry.INSTRUMENTED_MUSIC.containsId(FadenCoreIdentifier.create(typeData.getId()))) {
                    FadenCoreRegistry.INSTRUMENTED_MUSIC.frozen = false;
                    Registry.register(FadenCoreRegistry.INSTRUMENTED_MUSIC, FadenCoreIdentifier.create(typeData.getId()), new InstrumentedMusic(typeData.getName(), typeData.getAuthor(), typeData.getInstruments()));
                    FadenCoreRegistry.INSTRUMENTED_MUSIC.freeze();
                }
            } catch(Exception e) {
                FadenCore.LOGGER.error("Could not load instrumented music data from {}", id, e);
            }
        }
    }
}
