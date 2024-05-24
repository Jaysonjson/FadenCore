package net.fuchsia.network.s2c;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fuchsia.common.cape.FadenCapes;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record SendCapesS2CPacket(HashMap<UUID, ArrayList<String>> map) implements CustomPayload {

    public static final CustomPayload.Id<SendCapesS2CPacket> ID = new CustomPayload.Id<>(FadenIdentifier.create("send_capes"));
    public static final PacketCodec<RegistryByteBuf, SendCapesS2CPacket> CODEC = new PacketCodec<>() {
        @Override
        public SendCapesS2CPacket decode(RegistryByteBuf buf) {
            HashMap<UUID, ArrayList<String>> map = null;
            try {
                ByteArrayInputStream byteOut = new ByteArrayInputStream(Base64.getDecoder().decode(buf.readString()));
                ObjectInputStream out = new ObjectInputStream(byteOut);
                map = (HashMap<UUID, ArrayList<String>>) out.readObject();
                byteOut.close();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new SendCapesS2CPacket(map);
        }

        @Override
        public void encode(RegistryByteBuf buf, SendCapesS2CPacket value) {
            try {
                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(byteOut);
                out.writeObject(FadenCapes.getPlayerCapes());
                byte[] data = byteOut.toByteArray();
                byteOut.close();
                out.close();
                buf.writeString(new String(Base64.getEncoder().encode(data)));
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
        FadenCapes.setPlayerCapes(map);
    }
}