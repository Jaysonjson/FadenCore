package net.fuchsia.network.s2c;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fuchsia.client.FadenClient;
import net.fuchsia.network.c2s.RequestCapeChangeC2SPacket;
import net.fuchsia.network.c2s.SendItemValuesCheckC2SPacket;
import net.fuchsia.util.FadenCheckSum;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record AskItemValuesS2CPacket() implements CustomPayload {

    public static final CustomPayload.Id<AskItemValuesS2CPacket> ID = new CustomPayload.Id<>(FadenIdentifier.create("ask_item_values"));
    public static final PacketCodec<RegistryByteBuf, AskItemValuesS2CPacket> CODEC = new PacketCodec<>() {
        @Override
        public AskItemValuesS2CPacket decode(RegistryByteBuf buf) {
            return new AskItemValuesS2CPacket();
        }

        @Override
        public void encode(RegistryByteBuf buf, AskItemValuesS2CPacket value) {
        }
    };

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public void receive(ClientPlayNetworking.Context context) {
        ClientPlayNetworking.send(new SendItemValuesCheckC2SPacket(FadenClient.getItemValuesChecksum()));
    }
}
