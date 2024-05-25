package net.fuchsia.network.s2c;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;
import java.util.UUID;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fuchsia.common.race.data.ClientRaceCache;
import net.fuchsia.common.race.data.RaceData;
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
            RaceData data1 = null;
            try {
                ByteArrayInputStream byteOut = new ByteArrayInputStream(Base64.getDecoder().decode(buf.readString()));
                ObjectInputStream out = new ObjectInputStream(byteOut);
                data1 = (RaceData) out.readObject();
                byteOut.close();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }

            return new SendRaceUpdateS2CPacket(uuid1, data1, buf.readBoolean());
        }

        @Override
        public void encode(RegistryByteBuf buf, SendRaceUpdateS2CPacket value) {
            try {
                buf.writeUuid(value.uuid);
                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(byteOut);
                out.writeObject(value.data);
                byte[] data = byteOut.toByteArray();
                byteOut.close();
                out.close();
                buf.writeString(new String(Base64.getEncoder().encode(data)));
                buf.writeBoolean(value.remove);
            } catch (Exception e) {
                e.printStackTrace();
            }
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
