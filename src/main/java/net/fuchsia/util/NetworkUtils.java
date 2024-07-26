package net.fuchsia.util;

import net.fuchsia.Faden;
import net.fuchsia.common.race.RaceSkinMap;
import net.fuchsia.common.race.data.ServerRaceCache;
import net.fuchsia.common.race.skin.server.ServerSkinCache;
import net.fuchsia.network.FadenNetwork;
import net.fuchsia.server.PlayerData;
import net.fuchsia.server.ServerPlayerDatas;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.UUID;

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
        FadenNetwork.Server.sendItemValues(serverPlayerEntity);
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

}
