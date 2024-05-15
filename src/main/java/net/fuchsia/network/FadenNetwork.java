package net.fuchsia.network;

import java.util.ArrayList;
import java.util.UUID;

import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fuchsia.Faden;
import net.fuchsia.common.cape.FadenCapes;
import net.fuchsia.common.data.ItemValues;
import net.fuchsia.common.race.data.RaceData;
import net.fuchsia.common.race.data.ServerRaceCache;
import net.fuchsia.common.race.skin.provider.SkinProvider;
import net.fuchsia.common.race.skin.server.ServerSkinCache;
import net.fuchsia.network.c2s.RequestCapeChangeC2SPacket;
import net.fuchsia.network.s2c.RacePacket;
import net.fuchsia.network.s2c.ReloadServerJSONS2CPacket;
import net.fuchsia.network.s2c.RemoveSkinS2CPacket;
import net.fuchsia.network.s2c.SendAllRaceSkinsS2CPacket;
import net.fuchsia.network.s2c.SendAllRacesS2CPacket;
import net.fuchsia.network.s2c.SendPlayerDatasS2CPacket;
import net.fuchsia.network.s2c.SendRaceUpdateS2CPacket;
import net.fuchsia.network.s2c.SendSinglePlayerDataS2CPacket;
import net.fuchsia.network.s2c.SendSkinS2CPacket;
import net.fuchsia.server.PlayerData;
import net.fuchsia.server.ServerPlayerDatas;
import net.minecraft.server.network.ServerPlayerEntity;

public class FadenNetwork {

    public static void registerS2C() {
        PayloadTypeRegistry.playS2C().register(SendRaceUpdateS2CPacket.ID, SendRaceUpdateS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(RemoveSkinS2CPacket.ID, RemoveSkinS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(SendSkinS2CPacket.ID, SendSkinS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(SendAllRaceSkinsS2CPacket.ID, SendAllRaceSkinsS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(ReloadServerJSONS2CPacket.ID, ReloadServerJSONS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(SendAllRacesS2CPacket.ID, SendAllRacesS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(SendPlayerDatasS2CPacket.ID, SendPlayerDatasS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(SendSinglePlayerDataS2CPacket.ID, SendSinglePlayerDataS2CPacket.CODEC);

        ClientPlayNetworking.registerGlobalReceiver(SendRaceUpdateS2CPacket.ID, SendRaceUpdateS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(RemoveSkinS2CPacket.ID, RemoveSkinS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SendSkinS2CPacket.ID, SendSkinS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SendAllRaceSkinsS2CPacket.ID, SendAllRaceSkinsS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(ReloadServerJSONS2CPacket.ID, ReloadServerJSONS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SendAllRacesS2CPacket.ID, SendAllRacesS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SendPlayerDatasS2CPacket.ID, SendPlayerDatasS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SendSinglePlayerDataS2CPacket.ID, SendSinglePlayerDataS2CPacket::receive);
    }

    public static void registerC2S() {
        PayloadTypeRegistry.playC2S().register(RequestCapeChangeC2SPacket.ID, RequestCapeChangeC2SPacket.CODEC);
        ServerPlayNetworking.registerGlobalReceiver(RequestCapeChangeC2SPacket.ID, RequestCapeChangeC2SPacket::receive);

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

        public static void sendRaceSkin(ServerPlayerEntity player, UUID uuid, String id) {
            ServerPlayNetworking.send(player, new SendSkinS2CPacket(uuid, SkinProvider.getSkinIdentifier(id)));
        }

        public static void sendRace(ServerPlayerEntity player, UUID uuid, String id, String sub_id, RaceData.RaceDataCosmetics cosmetics, boolean remove) {
            ServerPlayNetworking.send(player, new SendRaceUpdateS2CPacket(uuid, new RaceData(id, sub_id, cosmetics), remove));
        }

        public static void sendRace(ServerPlayerEntity player, UUID uuid, RaceData data, boolean remove) {
            ServerPlayNetworking.send(player, new SendRaceUpdateS2CPacket(uuid, data, remove));
        }

        public static void sendAllRaces(ServerPlayerEntity player) {
            ServerPlayNetworking.send(player, new SendAllRaceSkinsS2CPacket(ServerSkinCache.getPlayerSkins()));
        }

        public static void sendRaces(ServerPlayerEntity player) {
            ArrayList<RacePacket> packets1 = new ArrayList<>();
            for (UUID uuid : ServerRaceCache.getCache().keySet()) {
                packets1.add(new RacePacket(uuid, ServerRaceCache.getCache().get(uuid)));
            }
            ServerPlayNetworking.send(player, new SendAllRacesS2CPacket(packets1));
        }


        public static void removeSkin(ServerPlayerEntity player, UUID uuid) {
            ServerPlayNetworking.send(player, new RemoveSkinS2CPacket(uuid));
        }

        public static void reloadCapes(ServerPlayerEntity player) {
            ServerPlayNetworking.send(player, new ReloadServerJSONS2CPacket("cape", Faden.GSON.toJson(FadenCapes.getPlayerCapes())));
        }

        public static void reloadItemValues(ServerPlayerEntity player) {
            ServerPlayNetworking.send(player, new ReloadServerJSONS2CPacket("item_values", Faden.GSON.toJson(ItemValues.VALUES)));
        }

        public static void sendPlayerDatas(ServerPlayerEntity player) {
            ServerPlayNetworking.send(player, new SendPlayerDatasS2CPacket(ServerPlayerDatas.getPlayerDatas()));
        }

        public static void syncPlayerData(ServerPlayerEntity player, UUID playerUuid, PlayerData playerData) {
            ServerPlayNetworking.send(player, new SendSinglePlayerDataS2CPacket(playerUuid, playerData));
        }
    }

}
