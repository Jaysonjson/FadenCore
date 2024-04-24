package net.fuchsia.network;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.util.Base64;
import java.util.UUID;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fuchsia.network.s2c.*;
import net.fuchsia.common.race.skin.provider.SkinProvider;
import net.fuchsia.common.race.skin.server.ServerSkinCache;
import net.fuchsia.util.FadenIdentifier;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public class FadenNetwork {

    public static void registerS2C() {
        PayloadTypeRegistry.playS2C().register(SendRaceUpdateS2CPacket.ID, SendRaceUpdateS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(RemoveSkinS2CPacket.ID, RemoveSkinS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(SendSkinS2CPacket.ID, SendSkinS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(SendAllRaceSkinsS2CPacket.ID, SendAllRaceSkinsS2CPacket.CODEC);

        ClientPlayNetworking.registerGlobalReceiver(SendRaceUpdateS2CPacket.ID, SendRaceUpdateS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(RemoveSkinS2CPacket.ID, RemoveSkinS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SendSkinS2CPacket.ID, SendSkinS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SendAllRaceSkinsS2CPacket.ID, SendAllRaceSkinsS2CPacket::receive);
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
            ServerPlayNetworking.send(player, new SendSkinS2CPacket(uuid, SkinProvider.getSkinIdentifier(id)));
        }

        public static void sendRace(ServerPlayerEntity player, UUID uuid, String id, String sub_id, boolean remove) {
            ServerPlayNetworking.send(player, new SendRaceUpdateS2CPacket(uuid, id, sub_id, remove));
        }

        public static void sendAllRaces(ServerPlayerEntity player) {
            ServerPlayNetworking.send(player, new SendAllRaceSkinsS2CPacket(ServerSkinCache.PLAYER_SKINS));
        }

        public static void removeSkin(ServerPlayerEntity player, UUID uuid) {
            ServerPlayNetworking.send(player, new RemoveSkinS2CPacket(uuid));
        }
    }

}
