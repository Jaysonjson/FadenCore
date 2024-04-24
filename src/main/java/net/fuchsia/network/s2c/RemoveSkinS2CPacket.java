package net.fuchsia.network.s2c;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fuchsia.common.race.IRace;
import net.fuchsia.common.race.Race;
import net.fuchsia.common.race.data.ClientRaceCache;
import net.fuchsia.common.race.data.RaceData;
import net.fuchsia.common.race.skin.client.ClientRaceSkinCache;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

import java.util.UUID;

public record RemoveSkinS2CPacket(UUID uuid) implements CustomPayload {

    public static final CustomPayload.Id<RemoveSkinS2CPacket> ID = new CustomPayload.Id<>(FadenIdentifier.create("remove_race"));
    public static final PacketCodec<RegistryByteBuf, RemoveSkinS2CPacket> CODEC =new PacketCodec<RegistryByteBuf, RemoveSkinS2CPacket>() {
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
        ClientRaceSkinCache.getPlayerSkins().remove(uuid);
    }
}
