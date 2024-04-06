package net.fuchsia.network;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;
import java.util.UUID;

import net.fuchsia.network.s2c.RemoveSkinS2C;
import net.fuchsia.network.s2c.SendAllRaceSkinsS2C;
import net.fuchsia.network.s2c.SendNewSkinS2C;
import net.fuchsia.race.skin.provider.SkinProvider;
import net.fuchsia.race.skin.server.ServerSkinCache;
import net.fuchsia.util.FadenIdentifier;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class FadenNetwork {

    public static final Identifier SEND_RACE_SKIN = FadenIdentifier.create("send_race_skin");
    public static final Identifier SEND_ALL_RACE_SKIN = FadenIdentifier.create("send_all_race_skin");
    public static final Identifier REMOVE_SKIN = FadenIdentifier.create("remove_skin");

    public static void registerS2C() {
        ClientPlayNetworking.registerGlobalReceiver(SEND_RACE_SKIN, SendNewSkinS2C::receive);
        ClientPlayNetworking.registerGlobalReceiver(SEND_ALL_RACE_SKIN, SendAllRaceSkinsS2C::receive);
        ClientPlayNetworking.registerGlobalReceiver(REMOVE_SKIN, RemoveSkinS2C::receive);
    }

    public static void registerC2S() {
    }

    /*
     * Client to Server
     * */
    public static class Client {
    }

    /*
     * Server to Client
     * */
    public static class Server {

        public static void sendRaceSkin(ServerPlayerEntity player, UUID uuid, String id) {
            PacketByteBuf byteBufs = PacketByteBufs.create();
            byteBufs.writeUuid(uuid);
            byteBufs.writeIdentifier(SkinProvider.getSkinIdentifier(id));
            ServerPlayNetworking.send(player, SEND_RACE_SKIN, byteBufs);
        }

        public static void sendAllRaces(ServerPlayerEntity player) {
            try {
                PacketByteBuf byteBufs = PacketByteBufs.create();
                ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
                ObjectOutputStream out = new ObjectOutputStream(byteOut);
                out.writeObject(ServerSkinCache.PLAYER_SKINS);
                byte[] data = byteOut.toByteArray();
                byteOut.close();
                out.close();
                byteBufs.writeString(new String(Base64.getEncoder().encode(data)));
                ServerPlayNetworking.send(player, SEND_ALL_RACE_SKIN, byteBufs);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


        public static void removeSkin(ServerPlayerEntity player, UUID uuid) {
            PacketByteBuf byteBufs = PacketByteBufs.create();
            byteBufs.writeUuid(uuid);
            ServerPlayNetworking.send(player, REMOVE_SKIN, byteBufs);
        }
    }

}
