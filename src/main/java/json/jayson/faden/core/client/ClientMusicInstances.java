package json.jayson.faden.core.client;

import json.jayson.faden.core.common.objects.music_instance.ClientMusicInstance;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.UUID;

public class ClientMusicInstances {

    private static HashMap<UUID, ClientMusicInstance> INSTANCES = new HashMap<>();

    public static HashMap<UUID, ClientMusicInstance> getInstances() {
        return INSTANCES;
    }

    @Nullable
    public static ClientMusicInstance getInstance(UUID uuid) {
        return INSTANCES.getOrDefault(uuid, null);
    }

}
