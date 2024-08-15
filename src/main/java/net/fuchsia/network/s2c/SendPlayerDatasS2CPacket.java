package net.fuchsia.network.s2c;

import java.util.HashMap;
import java.util.UUID;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fuchsia.network.PacketUtils;
import net.fuchsia.server.PlayerData;
import net.fuchsia.server.ServerPlayerDatas;
import net.fuchsia.server.client.ClientPlayerDatas;
import net.fuchsia.util.FadenCoreIdentifier;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record SendPlayerDatasS2CPacket(HashMap<UUID, PlayerData> map) implements CustomPayload {

    public static final CustomPayload.Id<SendPlayerDatasS2CPacket> ID = new CustomPayload.Id<>(FadenCoreIdentifier.create("send_player_datas"));
    public static final PacketCodec<RegistryByteBuf, SendPlayerDatasS2CPacket> CODEC = new PacketCodec<>() {
        @Override
        public SendPlayerDatasS2CPacket decode(RegistryByteBuf buf) {
            return new SendPlayerDatasS2CPacket(PacketUtils.readByteData(buf));
        }

        @Override
        public void encode(RegistryByteBuf buf, SendPlayerDatasS2CPacket value) {
            PacketUtils.writeByteData(ServerPlayerDatas.getPlayerDatas(), buf);
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