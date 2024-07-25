package net.fuchsia.network.c2s;

import java.util.ArrayList;
import java.util.UUID;

import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fuchsia.common.cape.FadenCapes;
import net.fuchsia.network.FadenNetwork;
import net.fuchsia.server.PlayerData;
import net.fuchsia.server.ServerPlayerDatas;
import net.fuchsia.util.FadenIdentifier;
import net.fuchsia.util.NetworkUtils;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.server.network.ServerPlayerEntity;

public record RequestCapeChangeC2SPacket(UUID uuid, String capeId) implements CustomPayload {

    public static final CustomPayload.Id<RequestCapeChangeC2SPacket> ID = new CustomPayload.Id<>(FadenIdentifier.create("request_cape"));
    public static final PacketCodec<RegistryByteBuf, RequestCapeChangeC2SPacket> CODEC = new PacketCodec<>() {
        @Override
        public RequestCapeChangeC2SPacket decode(RegistryByteBuf buf) {
            return new RequestCapeChangeC2SPacket(buf.readUuid(), buf.readString());
        }

        @Override
        public void encode(RegistryByteBuf buf, RequestCapeChangeC2SPacket value) {
            buf.writeUuid(value.uuid);
            buf.writeString(value.capeId);
        }
    };

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public void receive(ServerPlayNetworking.Context context) {
        PlayerData data = ServerPlayerDatas.getPlayerDatas().getOrDefault(uuid, new PlayerData());
        if(capeId.isEmpty()) {
            data.setSelectedCape("");
        } else {
            for (String s : FadenCapes.getPlayerCapes().getOrDefault(uuid, new ArrayList<>())) {
                if (s.equalsIgnoreCase(capeId)) {
                    data.setSelectedCape(capeId);
                }
            }
        }
        ServerPlayerDatas.getPlayerDatas().put(uuid, data);
        NetworkUtils.syncPlayer(context.server(), context.player());
    }
}

