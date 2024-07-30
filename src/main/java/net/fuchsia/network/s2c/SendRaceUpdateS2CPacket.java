package net.fuchsia.network.s2c;

import java.util.UUID;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fuchsia.common.objects.race.cache.ClientRaceCache;
import net.fuchsia.common.objects.race.cache.RaceData;
import net.fuchsia.network.PacketUtils;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record SendRaceUpdateS2CPacket(UUID uuid, RaceData data, boolean remove) implements CustomPayload {

    public static final CustomPayload.Id<SendRaceUpdateS2CPacket> ID = new CustomPayload.Id<>(FadenIdentifier.create("send_race"));
    public static final PacketCodec<RegistryByteBuf, SendRaceUpdateS2CPacket> CODEC = new PacketCodec<>() {
        @Override
        public SendRaceUpdateS2CPacket decode(RegistryByteBuf buf) {
            UUID uuid1 = buf.readUuid();
            return new SendRaceUpdateS2CPacket(uuid1, PacketUtils.readByteData(buf), buf.readBoolean());
        }

        @Override
        public void encode(RegistryByteBuf buf, SendRaceUpdateS2CPacket value) {
            buf.writeUuid(value.uuid);
            PacketUtils.writeByteData(value.data, buf);
            buf.writeBoolean(value.remove);

        }
    };

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public void receive(ClientPlayNetworking.Context context) {
        if(!remove) {
            ClientRaceCache.getCache().put(uuid, data);
        } else {
            ClientRaceCache.getCache().remove(uuid);
        }
    }
}
