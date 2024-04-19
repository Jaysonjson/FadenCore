package net.fuchsia.network.s2c;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fuchsia.common.race.IRace;
import net.fuchsia.common.race.Race;
import net.fuchsia.common.race.data.ClientRaceCache;
import net.fuchsia.common.race.data.RaceData;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

import java.util.UUID;

public class SendRaceUpdateS2C {

    public static void receive(MinecraftClient client, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf packetByteBuf, PacketSender packetSender) {
        UUID uuid = packetByteBuf.readUuid();
        String race = packetByteBuf.readString();
        String sub_id = packetByteBuf.readString();
        boolean rem = packetByteBuf.readBoolean();
        if(!rem) {
            IRace race1 = Race.valueOf(race);
            ClientRaceCache.getCache().put(uuid, new RaceData(race1, sub_id));
        } else {
            ClientRaceCache.getCache().remove(uuid);
        }
    }


}
