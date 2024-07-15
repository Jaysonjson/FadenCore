package net.fuchsia.network.s2c;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fuchsia.common.cape.FadenCapes;
import net.fuchsia.network.PacketUtils;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record SendCapesS2CPacket(HashMap<UUID, ArrayList<String>> map) implements CustomPayload {

    public static final CustomPayload.Id<SendCapesS2CPacket> ID = new CustomPayload.Id<>(FadenIdentifier.create("send_capes"));
    public static final PacketCodec<RegistryByteBuf, SendCapesS2CPacket> CODEC = new PacketCodec<>() {
        @Override
        public SendCapesS2CPacket decode(RegistryByteBuf buf) {
            return new SendCapesS2CPacket(PacketUtils.readByteData(buf));
        }

        @Override
        public void encode(RegistryByteBuf buf, SendCapesS2CPacket value) {
            PacketUtils.writeByteData(FadenCapes.getPlayerCapes(), buf);
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