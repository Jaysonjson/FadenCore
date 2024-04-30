package net.fuchsia.network.s2c;

import java.util.UUID;

public record RacePacket(UUID uuid, String id, String sub_id, String head_cosmetic) {
    
}
