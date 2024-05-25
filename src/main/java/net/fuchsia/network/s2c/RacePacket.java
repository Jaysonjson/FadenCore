package net.fuchsia.network.s2c;

import net.fuchsia.common.race.data.RaceData;

import java.util.UUID;

public record RacePacket(UUID uuid, RaceData data) {
    
}
