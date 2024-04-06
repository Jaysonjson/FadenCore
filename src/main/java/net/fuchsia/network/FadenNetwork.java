package net.fuchsia.network;

import java.util.Base64;
import java.util.UUID;

import net.fuchsia.network.c2s.SendNewSkinC2S;
import net.fuchsia.network.s2c.RemoveSkinS2C;
import net.fuchsia.network.s2c.SendNewSkinS2C;
import net.fuchsia.skin.server.ServerSkinCache;
import net.fuchsia.util.FadenIdentifier;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class FadenNetwork {

    public static final Identifier SEND_SINGLETON_SKIN = FadenIdentifier.create("send_singleton_skin");
    public static final Identifier SEND_SINGLETON_SKIN_C2S = FadenIdentifier.create("send_singleton_skin_c2s");
    public static final Identifier REMOVE_SKIN = FadenIdentifier.create("remove_skin");

    public static void registerS2C() {
        ClientPlayNetworking.registerGlobalReceiver(SEND_SINGLETON_SKIN, SendNewSkinS2C::receive);
        ClientPlayNetworking.registerGlobalReceiver(REMOVE_SKIN, RemoveSkinS2C::receive);
    }

    public static void registerC2S() {
        ServerPlayNetworking.registerGlobalReceiver(SEND_SINGLETON_SKIN_C2S, SendNewSkinC2S::receive);
    }

    /*
     * Client to Server
     * */
    public static class Client {
        public static void sendSingletonSkin(UUID uuid, byte[] skinData) {
            if(skinData != null) {
                PacketByteBuf byteBufs = PacketByteBufs.create();
                byteBufs.writeUuid(uuid);
                byteBufs.writeString(new String(Base64.getEncoder().encode(skinData)));
                ClientPlayNetworking.send(SEND_SINGLETON_SKIN_C2S, byteBufs);
            }
        }
    }

    /*
     * Server to Client
     * */
    public static class Server {

        public static void sendSingletonSkin(ServerPlayerEntity player) {
            ServerSkinCache.ServerSkin serverSkin = ServerSkinCache.getServerSkin(player.getUuid());
            if(serverSkin != null) {
                PacketByteBuf byteBufs = PacketByteBufs.create();
                byteBufs.writeUuid(serverSkin.uuid);
                byteBufs.writeIdentifier(serverSkin.identifier);
                byteBufs.writeBytes(serverSkin.skinData);
                ServerPlayNetworking.send(player, SEND_SINGLETON_SKIN, byteBufs);
            }
        }

        public static void sendSingletonSkin(ServerPlayerEntity player, ServerSkinCache.ServerSkin serverSkin) {
            if(serverSkin != null) {
                PacketByteBuf byteBufs = PacketByteBufs.create();
                byteBufs.writeUuid(serverSkin.uuid);
                byteBufs.writeIdentifier(serverSkin.identifier);
                byteBufs.writeString(new String(Base64.getEncoder().encode(serverSkin.skinData)));
                ServerPlayNetworking.send(player, SEND_SINGLETON_SKIN, byteBufs);
            }
        }

        public static void removeSkin(ServerPlayerEntity player, UUID uuid) {
            PacketByteBuf byteBufs = PacketByteBufs.create();
            byteBufs.writeUuid(uuid);
            ServerPlayNetworking.send(player, REMOVE_SKIN, byteBufs);
        }
    }

}
