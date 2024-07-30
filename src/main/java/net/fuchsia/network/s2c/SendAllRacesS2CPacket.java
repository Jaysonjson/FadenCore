package net.fuchsia.network.s2c;

import java.util.ArrayList;
import java.util.UUID;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fuchsia.common.objects.race.cache.ClientRaceCache;
import net.fuchsia.common.objects.race.cache.ServerRaceCache;
import net.fuchsia.network.PacketUtils;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record SendAllRacesS2CPacket(ArrayList<RacePacket> packets) implements CustomPayload {

    public static final CustomPayload.Id<SendAllRacesS2CPacket> ID = new CustomPayload.Id<>(FadenIdentifier.create("send_all_races"));
    public static final PacketCodec<RegistryByteBuf, SendAllRacesS2CPacket> CODEC = new PacketCodec<>() {
        @Override
        public SendAllRacesS2CPacket decode(RegistryByteBuf buf) {
            return new SendAllRacesS2CPacket(PacketUtils.readByteData(buf));
        }

        @Override
        public void encode(RegistryByteBuf buf, SendAllRacesS2CPacket value) {
            ArrayList<RacePacket> packets1 = new ArrayList<>();
            for (UUID uuid : ServerRaceCache.getCache().keySet()) {
                packets1.add(new RacePacket(uuid, ServerRaceCache.getCache().get(uuid)));
            }
            PacketUtils.writeByteData(packets1, buf);
        }
    };

    @Override
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }

    public void receive(ClientPlayNetworking.Context context) {
        for (RacePacket packet : packets) {
            ClientRaceCache.getCache().put(packet.uuid(), packet.data());
        }
    }
}
