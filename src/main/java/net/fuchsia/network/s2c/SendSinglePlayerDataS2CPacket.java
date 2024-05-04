package net.fuchsia.network.s2c;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fuchsia.Faden;
import net.fuchsia.server.PlayerData;
import net.fuchsia.server.ServerPlayerDatas;
import net.fuchsia.server.client.ClientPlayerDatas;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

public record SendSinglePlayerDataS2CPacket(UUID playerUuid, PlayerData playerData) implements CustomPayload {

    public static final CustomPayload.Id<SendSinglePlayerDataS2CPacket> ID = new CustomPayload.Id<>(FadenIdentifier.create("send_player_data"));
    public static final PacketCodec<RegistryByteBuf, SendSinglePlayerDataS2CPacket> CODEC = new PacketCodec<>() {
        @Override
        public SendSinglePlayerDataS2CPacket decode(RegistryByteBuf buf) {
            PlayerData data = null;
            try {
                ByteArrayInputStream byteOut = new ByteArrayInputStream(Base64.getDecoder().decode(buf.readString()));
                ObjectInputStream out = new ObjectInputStream(byteOut);
                data = (PlayerData) out.readObject();
                byteOut.close();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new SendSinglePlayerDataS2CPacket(buf.readUuid(), data);
        }

        @Override
        public void encode(RegistryByteBuf buf, SendSinglePlayerDataS2CPacket value) {
            try {
                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(byteOut);
                out.writeObject(value.playerData);
                byte[] data = byteOut.toByteArray();
                byteOut.close();
                out.close();
                buf.writeString(new String(Base64.getEncoder().encode(data)));
            } catch (Exception e) {
                Faden.LOGGER.warn(e.getMessage());
            }
            buf.writeUuid(value.playerUuid);
        }
    };

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public void receive(ClientPlayNetworking.Context context) {
        ClientPlayerDatas.getPlayerDatas().put(playerUuid, playerData);
    }
}