package net.fuchsia.util;

import net.fuchsia.common.data.ItemValues;
import net.fuchsia.common.objects.race.RaceSkinMap;
import net.fuchsia.common.objects.race.cache.ServerRaceCache;
import net.fuchsia.network.FadenNetwork;
import net.fuchsia.server.ServerPlayerDatas;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;

public class NetworkUtils {

    public static void fullyUpdatePlayer(ServerPlayerEntity serverPlayerEntity, MinecraftServer server) {
        if(!ServerPlayerDatas.getPlayerDatas().containsKey(serverPlayerEntity.getUuid())) {
            ServerPlayerDatas.getPlayerDatas().put(serverPlayerEntity.getUuid(), ServerPlayerDatas.getOrLoadPlayerData(serverPlayerEntity.getUuid()));
        }

        FadenNetwork.Server.sendAllRaceSkins(serverPlayerEntity);

        //EXP
        FadenNetwork.Server.sendRaces(serverPlayerEntity);


        broadcastRaceUpdate(serverPlayerEntity, server);
        FadenNetwork.Server.sendPlayerDatas(serverPlayerEntity);
        syncPlayer(server, serverPlayerEntity);
        FadenNetwork.Server.askItemValues(serverPlayerEntity);
    }

    public static void syncPlayer(MinecraftServer server, ServerPlayerEntity serverPlayerEntity) {
        FadenNetwork.Server.syncPlayerData(serverPlayerEntity, serverPlayerEntity.getUuid(), ServerPlayerDatas.getOrLoadPlayerData(serverPlayerEntity.getUuid()));
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            if(player.getUuid().toString().equalsIgnoreCase(serverPlayerEntity.getUuid().toString())) continue;
            FadenNetwork.Server.syncPlayerData(player, serverPlayerEntity.getUuid(), ServerPlayerDatas.getOrLoadPlayerData(serverPlayerEntity.getUuid()));
        }
    }

    public static void broadcastRaceUpdate(ServerPlayerEntity serverPlayerEntity, MinecraftServer server) {
        RaceSkinMap.Cache.sendUpdate(serverPlayerEntity, server);
        ServerRaceCache.Cache.sendUpdate(serverPlayerEntity, server, false);
    }

    public static HashMap<String, Integer> trimItemValueMap() {
        HashMap<String, Integer> map = new HashMap<>();
        for (Item item : ItemValues.VALUES.keySet()) {
            map.put(Registries.ITEM.getId(item).toString(), ItemValues.VALUES.get(item));
        }
        map.values().removeIf(integer -> integer == 0);
        return map;
    }

}
