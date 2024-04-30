package net.fuchsia.network.s2c;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fuchsia.common.race.IRace;
import net.fuchsia.common.race.Race;
import net.fuchsia.common.race.data.ClientRaceCache;
import net.fuchsia.common.race.data.RaceData;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

import java.util.UUID;

public record SendRaceUpdateS2CPacket(UUID uuid, String id, String sub_id, String head_cosmetic, boolean remove) implements CustomPayload {

    public static final CustomPayload.Id<SendRaceUpdateS2CPacket> ID = new CustomPayload.Id<>(FadenIdentifier.create("send_race"));
    public static final PacketCodec<RegistryByteBuf, SendRaceUpdateS2CPacket> CODEC =new PacketCodec<RegistryByteBuf, SendRaceUpdateS2CPacket>() {
        @Override
        public SendRaceUpdateS2CPacket decode(RegistryByteBuf buf) {
            return new SendRaceUpdateS2CPacket(buf.readUuid(), buf.readString(), buf.readString(), buf.readString(), buf.readBoolean());
        }

        @Override
        public void encode(RegistryByteBuf buf, SendRaceUpdateS2CPacket value) {
            buf.writeUuid(value.uuid);
            buf.writeString(value.id);
            buf.writeString(value.sub_id);
            buf.writeString(value.head_cosmetic);
            buf.writeBoolean(value.remove);
        }
    };

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public void receive(ClientPlayNetworking.Context context) {
        if(!remove) {
            IRace race1 = Race.valueOf(id);
            ClientRaceCache.getCache().put(uuid, new RaceData(race1, sub_id, head_cosmetic));
        } else {
            ClientRaceCache.getCache().remove(uuid);
        }
    }
}
