package net.fuchsia.network.s2c;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fuchsia.common.data.ItemValues;
import net.fuchsia.network.PacketUtils;
import net.fuchsia.util.FadenCoreIdentifier;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

import java.util.HashMap;

public record ItemValuesS2CPacket(HashMap<String, Integer> values) implements CustomPayload {

    public static final CustomPayload.Id<ItemValuesS2CPacket> ID = new CustomPayload.Id<>(FadenCoreIdentifier.create("item_values"));
    public static final PacketCodec<RegistryByteBuf, ItemValuesS2CPacket> CODEC = new PacketCodec<>() {
        @Override
        public ItemValuesS2CPacket decode(RegistryByteBuf buf) {
            return new ItemValuesS2CPacket(PacketUtils.readByteData(buf));
        }

        @Override
        public void encode(RegistryByteBuf buf, ItemValuesS2CPacket value) {
            PacketUtils.writeByteData(value.values, buf);
        }
    };

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public void receive(ClientPlayNetworking.Context context) {
        ItemValues.reload(values);
        ItemValues.saveClient();
    }
}
