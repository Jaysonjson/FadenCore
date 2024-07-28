package net.fuchsia.network.s2c;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fuchsia.common.objects.race.skin.client.ClientRaceSkinCache;
import net.fuchsia.common.objects.race.skin.provider.SkinProvider;
import net.fuchsia.common.objects.race.skin.server.ServerSkinCache;
import net.fuchsia.network.PacketUtils;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

public record SendAllRaceSkinsS2CPacket(HashMap<UUID, String> map) implements CustomPayload {

    public static final CustomPayload.Id<SendAllRaceSkinsS2CPacket> ID = new CustomPayload.Id<>(FadenIdentifier.create("send_all_race_skins"));
    public static final PacketCodec<RegistryByteBuf, SendAllRaceSkinsS2CPacket> CODEC =new PacketCodec<RegistryByteBuf, SendAllRaceSkinsS2CPacket>() {
        @Override
        public SendAllRaceSkinsS2CPacket decode(RegistryByteBuf buf) {
            return new SendAllRaceSkinsS2CPacket(PacketUtils.readByteData(buf));
        }

        @Override
        public void encode(RegistryByteBuf buf, SendAllRaceSkinsS2CPacket value) {
            PacketUtils.writeByteData(ServerSkinCache.getPlayerSkins(), buf);
        }
    };

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public void receive(ClientPlayNetworking.Context context) {
        for (UUID uuid : map.keySet()) {
            ClientRaceSkinCache.setSkin(uuid, SkinProvider.getSkinIdentifier(map.get(uuid)));
        }
    }
}
