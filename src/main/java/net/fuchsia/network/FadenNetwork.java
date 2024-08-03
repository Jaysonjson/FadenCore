package net.fuchsia.network;

import java.util.ArrayList;
import java.util.UUID;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fuchsia.common.objects.music_instance.MusicInstance;
import net.fuchsia.network.c2s.RequestCapeChangeC2SPacket;
import net.fuchsia.network.c2s.SendItemValuesCheckC2SPacket;
import net.fuchsia.network.s2c.*;
import net.fuchsia.server.PlayerData;
import net.fuchsia.server.ServerPlayerDatas;
import net.fuchsia.util.NetworkUtils;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;

public class FadenNetwork {

    public static void registerS2C() {
        ClientPlayNetworking.registerGlobalReceiver(SendPlayerDatasS2CPacket.ID, SendPlayerDatasS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SendSinglePlayerDataS2CPacket.ID, SendSinglePlayerDataS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SendCapeUpdateS2CPacket.ID, SendCapeUpdateS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SendItemValueUpdateS2CPacket.ID, SendItemValueUpdateS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(ItemValuesS2CPacket.ID, ItemValuesS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(AskItemValuesS2CPacket.ID, AskItemValuesS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SendMusicInstanceS2CPacket.ID, SendMusicInstanceS2CPacket::receive);

    }

    public static void registerC2S() {
        PayloadTypeRegistry.playS2C().register(SendPlayerDatasS2CPacket.ID, SendPlayerDatasS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(SendSinglePlayerDataS2CPacket.ID, SendSinglePlayerDataS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(SendCapeUpdateS2CPacket.ID, SendCapeUpdateS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(SendItemValueUpdateS2CPacket.ID, SendItemValueUpdateS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(ItemValuesS2CPacket.ID, ItemValuesS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(AskItemValuesS2CPacket.ID, AskItemValuesS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(SendMusicInstanceS2CPacket.ID, SendMusicInstanceS2CPacket.CODEC);


        PayloadTypeRegistry.playC2S().register(RequestCapeChangeC2SPacket.ID, RequestCapeChangeC2SPacket.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(RequestCapeChangeC2SPacket.ID, RequestCapeChangeC2SPacket::receive);

        PayloadTypeRegistry.playC2S().register(SendItemValuesCheckC2SPacket.ID, SendItemValuesCheckC2SPacket.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(SendItemValuesCheckC2SPacket.ID, SendItemValuesCheckC2SPacket::receive);

    }

    /*
     * Client to Server
     * */
    public static class Client {

        public static void requestCapeUpdate(UUID uuid, String id) {
            ClientPlayNetworking.send(new RequestCapeChangeC2SPacket(uuid, id));
        }

    }

    /*
     * Server to Client
     * */
    public static class Server {

        public static void sendPlayerDatas(ServerPlayerEntity player) {
            ServerPlayNetworking.send(player, new SendPlayerDatasS2CPacket(ServerPlayerDatas.getPlayerDatas()));
        }

        public static void syncPlayerData(ServerPlayerEntity player, UUID playerUuid, PlayerData playerData) {
            ServerPlayNetworking.send(player, new SendSinglePlayerDataS2CPacket(playerUuid, playerData));
        }


        public static void sendCapeUpdate(ServerPlayerEntity player, UUID uuid, String cape, boolean remove) {
            ServerPlayNetworking.send(player, new SendCapeUpdateS2CPacket(uuid, cape, remove));
        }

        public static void sendItemValueUpdate(ServerPlayerEntity player, Item item, int value) {
            ServerPlayNetworking.send(player, new SendItemValueUpdateS2CPacket(Registries.ITEM.getId(item).toString(), value));
        }

        public static void sendItemValues(ServerPlayerEntity player) {
            ServerPlayNetworking.send(player, new ItemValuesS2CPacket(NetworkUtils.trimItemValueMap()));
        }

        public static void askItemValues(ServerPlayerEntity player) {
            ServerPlayNetworking.send(player, new AskItemValuesS2CPacket());
        }

        public static void sendMusicInstance(ServerPlayerEntity player, MusicInstance musicInstance) {
            ServerPlayNetworking.send(player, new SendMusicInstanceS2CPacket(musicInstance));
        }
    }

}
