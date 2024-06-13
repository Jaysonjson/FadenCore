package net.fuchsia.network.s2c;

import java.util.UUID;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fuchsia.common.race.skin.client.ClientRaceSkinCache;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record RemoveSkinS2CPacket(UUID uuid) implements CustomPayload {

    public static final CustomPayload.Id<RemoveSkinS2CPacket> ID = new CustomPayload.Id<>(FadenIdentifier.create("remove_race"));
    public static final PacketCodec<RegistryByteBuf, RemoveSkinS2CPacket> CODEC = new PacketCodec<>() {
        @Override
        public RemoveSkinS2CPacket decode(RegistryByteBuf buf) {
            return new RemoveSkinS2CPacket(buf.readUuid());
        }

        @Override
        public void encode(RegistryByteBuf buf, RemoveSkinS2CPacket value) {
            buf.writeUuid(value.uuid);
        }
    };

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public void receive(ClientPlayNetworking.Context context) {
        ClientRaceSkinCache.removeSkin(uuid);
    }
}
