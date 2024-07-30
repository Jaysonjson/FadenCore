package net.fuchsia.network.s2c;

import net.fuchsia.common.objects.race.cache.RaceData;

import java.io.Serializable;
import java.util.UUID;

public record RacePacket(UUID uuid, RaceData data) implements Serializable {
    
}
