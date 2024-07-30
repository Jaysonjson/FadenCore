package net.fuchsia.network.s2c;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fuchsia.race.skin.client.ClientRaceSkinCache;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.util.UUID;

public record SendSkinS2CPacket(UUID uuid, Identifier identifier) implements CustomPayload {

    public static final CustomPayload.Id<SendSkinS2CPacket> ID = new CustomPayload.Id<>(FadenIdentifier.create("send_race_skin"));
    public static final PacketCodec<RegistryByteBuf, SendSkinS2CPacket> CODEC =new PacketCodec<RegistryByteBuf, SendSkinS2CPacket>() {
        @Override
        public SendSkinS2CPacket decode(RegistryByteBuf buf) {
            return new SendSkinS2CPacket(buf.readUuid(), buf.readIdentifier());
        }

        @Override
        public void encode(RegistryByteBuf buf, SendSkinS2CPacket value) {
            buf.writeUuid(value.uuid);
            buf.writeIdentifier(value.identifier);
        }
    };

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public void receive(ClientPlayNetworking.Context context) {
        ClientRaceSkinCache.getPlayerSkins().put(uuid, identifier);
    }
}
