package net.fuchsia.common.init;

import net.fuchsia.common.objects.music_instance.InstrumentedMusic;
import net.fuchsia.common.objects.music_instance.MusicInstance;
import net.fuchsia.common.objects.music_instance.songs.BurningMemoryMusic;
import net.fuchsia.common.objects.music_instance.songs.TheMistMusic;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.UUID;

public class FadenMusicInstances {
    private static HashMap<Identifier, InstrumentedMusic> INSTRUMENTED = new HashMap<>();
    private static HashMap<UUID, MusicInstance> INSTANCES = new HashMap<>();

    public static InstrumentedMusic BURNING_MEMORY = register(FadenIdentifier.create("burning_memory"), new BurningMemoryMusic());
    public static InstrumentedMusic THE_MIST = register(FadenIdentifier.create("the_mist"), new TheMistMusic());

    private static InstrumentedMusic register(Identifier id, InstrumentedMusic musicInstance) {
        INSTRUMENTED.put(id, musicInstance);
        return INSTRUMENTED.get(id);
    }

    public static HashMap<UUID, MusicInstance> getInstances() {
        return INSTANCES;
    }

    public static HashMap<Identifier, InstrumentedMusic> getRegistry() {
        return INSTRUMENTED;
    }

    @Nullable
    public static InstrumentedMusic getMusic(Identifier id) {
        return INSTRUMENTED.getOrDefault(id, null);
    }

    @Nullable
    public static InstrumentedMusic getMusic(String id) {
        return getMusic(Identifier.of(id));
    }

    @Nullable
    public static MusicInstance getInstance(UUID uuid) {
        return INSTANCES.getOrDefault(uuid, null);
    }


}
