package net.fuchsia.network.s2c;

import java.util.UUID;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fuchsia.network.PacketUtils;
import net.fuchsia.server.PlayerData;
import net.fuchsia.server.client.ClientPlayerDatas;
import net.fuchsia.util.FadenCoreIdentifier;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record SendSinglePlayerDataS2CPacket(UUID playerUuid, PlayerData playerData) implements CustomPayload {

    public static final CustomPayload.Id<SendSinglePlayerDataS2CPacket> ID = new CustomPayload.Id<>(FadenCoreIdentifier.create("send_player_data"));
    public static final PacketCodec<RegistryByteBuf, SendSinglePlayerDataS2CPacket> CODEC = new PacketCodec<>() {
        @Override
        public SendSinglePlayerDataS2CPacket decode(RegistryByteBuf buf) {
            return new SendSinglePlayerDataS2CPacket(buf.readUuid(),  PacketUtils.readByteData(buf));
        }

        @Override
        public void encode(RegistryByteBuf buf, SendSinglePlayerDataS2CPacket value) {
            buf.writeUuid(value.playerUuid);
            PacketUtils.writeByteData(value.playerData, buf);
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