package net.fuchsia.network;

import java.util.ArrayList;
import java.util.HashMap;
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
import net.fuchsia.network.s2c.*;
import net.fuchsia.server.PlayerData;
import net.fuchsia.server.ServerPlayerDatas;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.server.network.ServerPlayerEntity;

public class FadenNetwork {

    public static void registerS2C() {
        ClientPlayNetworking.registerGlobalReceiver(SendRaceUpdateS2CPacket.ID, SendRaceUpdateS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(RemoveSkinS2CPacket.ID, RemoveSkinS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SendSkinS2CPacket.ID, SendSkinS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SendAllRaceSkinsS2CPacket.ID, SendAllRaceSkinsS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SendAllRacesS2CPacket.ID, SendAllRacesS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SendPlayerDatasS2CPacket.ID, SendPlayerDatasS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SendSinglePlayerDataS2CPacket.ID, SendSinglePlayerDataS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SendCapesS2CPacket.ID, SendCapesS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SendCapeUpdateS2CPacket.ID, SendCapeUpdateS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(SendItemValueUpdateS2CPacket.ID, SendItemValueUpdateS2CPacket::receive);
        ClientPlayNetworking.registerGlobalReceiver(ItemValuesS2CPacket.ID, ItemValuesS2CPacket::receive);

    }

    public static void registerC2S() {

        PayloadTypeRegistry.playS2C().register(SendRaceUpdateS2CPacket.ID, SendRaceUpdateS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(RemoveSkinS2CPacket.ID, RemoveSkinS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(SendSkinS2CPacket.ID, SendSkinS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(SendAllRaceSkinsS2CPacket.ID, SendAllRaceSkinsS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(SendAllRacesS2CPacket.ID, SendAllRacesS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(SendPlayerDatasS2CPacket.ID, SendPlayerDatasS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(SendSinglePlayerDataS2CPacket.ID, SendSinglePlayerDataS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(SendCapesS2CPacket.ID, SendCapesS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(SendCapeUpdateS2CPacket.ID, SendCapeUpdateS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(SendItemValueUpdateS2CPacket.ID, SendItemValueUpdateS2CPacket.CODEC);
        PayloadTypeRegistry.playS2C().register(ItemValuesS2CPacket.ID, ItemValuesS2CPacket.CODEC);



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

        public static void sendAllRaceSkins(ServerPlayerEntity player) {
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

        public static void sendPlayerDatas(ServerPlayerEntity player) {
            ServerPlayNetworking.send(player, new SendPlayerDatasS2CPacket(ServerPlayerDatas.getPlayerDatas()));
        }

        public static void syncPlayerData(ServerPlayerEntity player, UUID playerUuid, PlayerData playerData) {
            ServerPlayNetworking.send(player, new SendSinglePlayerDataS2CPacket(playerUuid, playerData));
        }

        public static void sendPlayerCapes(ServerPlayerEntity player) {
            ServerPlayNetworking.send(player, new SendCapesS2CPacket(FadenCapes.getPlayerCapes()));
        }

        public static void sendCapeUpdate(ServerPlayerEntity player, UUID uuid, String cape, boolean remove) {
            ServerPlayNetworking.send(player, new SendCapeUpdateS2CPacket(uuid, cape, remove));
        }

        public static void sendItemValueUpdate(ServerPlayerEntity player, Item item, int value) {
            ServerPlayNetworking.send(player, new SendItemValueUpdateS2CPacket(Registries.ITEM.getId(item).toString(), value));
        }

        public static void sendItemValues(ServerPlayerEntity player) {
            HashMap<String, Integer> map = new HashMap<>();
            for (Item item : ItemValues.VALUES.keySet()) {
                map.put(Registries.ITEM.getId(item).toString(), ItemValues.VALUES.get(item));
            }
            map.values().removeIf(integer -> integer == 0);

        }
    }

}
