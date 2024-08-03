package net.fuchsia.client;

import net.fuchsia.common.objects.music_instance.ClientMusicInstance;
import net.fuchsia.common.objects.music_instance.InstrumentedMusic;
import net.fuchsia.common.objects.music_instance.MusicInstance;
import net.minecraft.util.Identifier;
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
