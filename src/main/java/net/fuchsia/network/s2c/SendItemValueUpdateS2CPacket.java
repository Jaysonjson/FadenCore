package net.fuchsia.network.s2c;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fuchsia.common.data.ItemValues;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public record SendItemValueUpdateS2CPacket(String item, int value) implements CustomPayload {

    public static final CustomPayload.Id<SendItemValueUpdateS2CPacket> ID = new CustomPayload.Id<>(FadenIdentifier.create("send_item_value_update"));
    public static final PacketCodec<RegistryByteBuf, SendItemValueUpdateS2CPacket> CODEC = new PacketCodec<>() {
        @Override
        public SendItemValueUpdateS2CPacket decode(RegistryByteBuf buf) {
            return new SendItemValueUpdateS2CPacket(buf.readString(), buf.readInt());
        }

        @Override
        public void encode(RegistryByteBuf buf, SendItemValueUpdateS2CPacket value) {
            buf.writeString(value.item);
            buf.writeInt(value.value);
        }
    };

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public void receive(ClientPlayNetworking.Context context) {
        ItemValues.VALUES.put(Registries.ITEM.get(Identifier.of(item)), value);
    }
}