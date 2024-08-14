package json.jayson.faden.core.network.s2c;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import json.jayson.faden.core.client.FadenCoreClient;
import json.jayson.faden.core.network.c2s.SendItemValuesCheckC2SPacket;
import json.jayson.faden.core.util.FadenCoreIdentifier;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record AskItemValuesS2CPacket() implements CustomPayload {

    public static final CustomPayload.Id<AskItemValuesS2CPacket> ID = new CustomPayload.Id<>(FadenCoreIdentifier.create("ask_item_values"));
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
        ClientPlayNetworking.send(new SendItemValuesCheckC2SPacket(FadenCoreClient.getItemValuesChecksum()));
    }
}
