package net.fuchsia.network.s2c;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fuchsia.common.race.Race;
import net.fuchsia.common.race.data.ClientRaceCache;
import net.fuchsia.common.race.data.RaceData;
import net.fuchsia.common.race.data.ServerRaceCache;
import net.fuchsia.common.race.skin.server.ServerSkinCache;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.packet.CustomPayload;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

public record SendAllRacesS2CPacket(ArrayList<RacePacket> packets) implements CustomPayload {

    public static final CustomPayload.Id<SendAllRacesS2CPacket> ID = new CustomPayload.Id<>(FadenIdentifier.create("send_all_races"));
    public static final PacketCodec<RegistryByteBuf, SendAllRacesS2CPacket> CODEC =new PacketCodec<RegistryByteBuf, SendAllRacesS2CPacket>() {
        @Override
        public SendAllRacesS2CPacket decode(RegistryByteBuf buf) {
            ArrayList<RacePacket> map = new ArrayList<>();
            try {
                ByteArrayInputStream byteOut = new ByteArrayInputStream(Base64.getDecoder().decode(buf.readString()));
                ObjectInputStream out = new ObjectInputStream(byteOut);
                map = ( ArrayList<RacePacket>) out.readObject();
                byteOut.close();
                out.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return new SendAllRacesS2CPacket(map);
        }

        @Override
        public void encode(RegistryByteBuf buf, SendAllRacesS2CPacket value) {
            try {
                PacketByteBuf byteBufs = PacketByteBufs.create();
                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(byteOut);
                ArrayList<RacePacket> packets1 = new ArrayList<>();
                for (UUID uuid : ServerRaceCache.getCache().keySet()) {
                    packets1.add(new RacePacket(uuid, ServerRaceCache.getCache().get(uuid).getRace().getId(), ServerRaceCache.getCache().get(uuid).getSubId(), ServerRaceCache.getCache().get(uuid).getHeadCosmeticId()));
                }
                out.writeObject(packets1);
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
    public CustomPayload.Id<? extends CustomPayload> getId() {
        return ID;
    }

    public void receive(ClientPlayNetworking.Context context) {
        for (RacePacket packet : packets) {
            ClientRaceCache.getCache().put(packet.uuid(), new RaceData(Race.valueOf(packet.id()), packet.sub_id(), packet.head_cosmetic()));
        }
    }
}
