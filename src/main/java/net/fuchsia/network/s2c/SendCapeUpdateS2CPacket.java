package net.fuchsia.network.s2c;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fuchsia.common.cape.FadenCapes;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

import java.util.ArrayList;
import java.util.UUID;

public record SendCapeUpdateS2CPacket(UUID uuid, String cape, boolean remove) implements CustomPayload {

    public static final CustomPayload.Id<SendCapeUpdateS2CPacket> ID = new CustomPayload.Id<>(FadenIdentifier.create("send_cape_update"));
    public static final PacketCodec<RegistryByteBuf, SendCapeUpdateS2CPacket> CODEC = new PacketCodec<>() {
        @Override
        public SendCapeUpdateS2CPacket decode(RegistryByteBuf buf) {
            return new SendCapeUpdateS2CPacket(buf.readUuid(), buf.readString(), buf.readBoolean());
        }

        @Override
        public void encode(RegistryByteBuf buf, SendCapeUpdateS2CPacket value) {
            buf.writeUuid(value.uuid);
            buf.writeString(value.cape);
            buf.writeBoolean(value.remove);
        }
    };

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public void receive(ClientPlayNetworking.Context context) {
        if(!remove) {
            ArrayList<String> capes = FadenCapes.getPlayerCapes().getOrDefault(uuid, new ArrayList<>());
            if (!capes.contains(cape)) {
                capes.add(cape);
            }
            FadenCapes.getPlayerCapes().put(uuid, capes);
        } else {
            FadenCapes.getPlayerCapes().getOrDefault(uuid, new ArrayList<>()).remove(cape);
        }
    }
}