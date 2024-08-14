package json.jayson.faden.core.common.init;

import json.jayson.faden.core.common.objects.music_instance.InstrumentedMusic;
import json.jayson.faden.core.common.objects.music_instance.MusicInstance;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.UUID;

public class FadenCoreMusicInstances {
    private static HashMap<UUID, MusicInstance> INSTANCES = new HashMap<>();

    public static HashMap<UUID, MusicInstance> getInstances() {
        return INSTANCES;
    }

    @Nullable
    public static MusicInstance getInstance(UUID uuid) {
        return INSTANCES.getOrDefault(uuid, null);
    }
}
