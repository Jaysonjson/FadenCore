package net.fuchsia.network.s2c;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fuchsia.common.race.skin.client.ClientRaceSkinCache;
import net.fuchsia.common.race.skin.provider.SkinProvider;
import net.fuchsia.common.race.skin.server.ServerSkinCache;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

public record SendAllRaceSkinsS2CPacket(HashMap<UUID, String> map) implements CustomPayload {

    public static final CustomPayload.Id<SendAllRaceSkinsS2CPacket> ID = new CustomPayload.Id<>(FadenIdentifier.create("send_all_race_skins"));
    public static final PacketCodec<RegistryByteBuf, SendAllRaceSkinsS2CPacket> CODEC =new PacketCodec<RegistryByteBuf, SendAllRaceSkinsS2CPacket>() {
        @Override
        public SendAllRaceSkinsS2CPacket decode(RegistryByteBuf buf) {
            HashMap<UUID, String> map = new HashMap<>();
            try {
                ByteArrayInputStream byteOut = new ByteArrayInputStream(Base64.getDecoder().decode(buf.readString()));
                ObjectInputStream out = new ObjectInputStream(byteOut);
                map = (HashMap<UUID, String>) out.readObject();
                byteOut.close();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new SendAllRaceSkinsS2CPacket(map);
        }

        @Override
        public void encode(RegistryByteBuf buf, SendAllRaceSkinsS2CPacket value) {
            try {
                PacketByteBuf byteBufs = PacketByteBufs.create();
                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(byteOut);
                out.writeObject(ServerSkinCache.PLAYER_SKINS);
                byte[] data = byteOut.toByteArray();
                byteOut.close();
                out.close();
                buf.writeString(new String(Base64.getEncoder().encode(data)));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }

    public void receive(ClientPlayNetworking.Context context) {
        for (UUID uuid : map.keySet()) {
            ClientRaceSkinCache.getPlayerSkins().put(uuid, SkinProvider.getSkinIdentifier(map.get(uuid)));
        }
    }
}
