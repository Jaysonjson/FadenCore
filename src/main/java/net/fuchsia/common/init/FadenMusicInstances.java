package net.fuchsia.common.init;

import net.fuchsia.common.objects.music_instance.InstrumentedMusic;
import net.fuchsia.common.objects.music_instance.MusicInstance;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

public class FadenMusicInstances {
    private static HashMap<Identifier, InstrumentedMusic> INSTRUMENTED = new HashMap<>();
    private static HashMap<UUID, MusicInstance> INSTANCES = new HashMap<>();

    private static InstrumentedMusic register(InstrumentedMusic musicInstance) {
        INSTRUMENTED.put(Identifier.of(""), musicInstance);
        return INSTRUMENTED.get(Identifier.of(""));
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

}
