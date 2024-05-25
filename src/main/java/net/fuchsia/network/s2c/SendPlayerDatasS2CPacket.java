package net.fuchsia.network.s2c;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fuchsia.server.PlayerData;
import net.fuchsia.server.ServerPlayerDatas;
import net.fuchsia.server.client.ClientPlayerDatas;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record SendPlayerDatasS2CPacket(HashMap<UUID, PlayerData> map) implements CustomPayload {

    public static final CustomPayload.Id<SendPlayerDatasS2CPacket> ID = new CustomPayload.Id<>(FadenIdentifier.create("send_player_datas"));
    public static final PacketCodec<RegistryByteBuf, SendPlayerDatasS2CPacket> CODEC = new PacketCodec<>() {
        @Override
        public SendPlayerDatasS2CPacket decode(RegistryByteBuf buf) {
            HashMap<UUID, PlayerData> map = null;
            try {
                ByteArrayInputStream byteOut = new ByteArrayInputStream(Base64.getDecoder().decode(buf.readString()));
                ObjectInputStream out = new ObjectInputStream(byteOut);
                map = (HashMap<UUID, PlayerData>) out.readObject();
                byteOut.close();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new SendPlayerDatasS2CPacket(map);
        }

        @Override
        public void encode(RegistryByteBuf buf, SendPlayerDatasS2CPacket value) {
            try {
                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(byteOut);
                out.writeObject(ServerPlayerDatas.getPlayerDatas());
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
        ClientPlayerDatas.setPlayerDatas(map);
    }
}