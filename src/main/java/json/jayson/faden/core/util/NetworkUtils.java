package json.jayson.faden.core.util;

import json.jayson.faden.core.FadenCore;
import json.jayson.faden.core.common.data.ItemValues;
import json.jayson.faden.core.network.FadenCoreNetwork;
import json.jayson.faden.core.server.PlayerData;
import json.jayson.faden.core.server.ServerPlayerDatas;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.HashMap;

public class NetworkUtils {

    public static void fullyUpdatePlayer(ServerPlayerEntity serverPlayerEntity, MinecraftServer server) {
        if(FadenCore.MODULES.playerDatas && !ServerPlayerDatas.getPlayerDatas().containsKey(serverPlayerEntity.getUuid())) {
            PlayerData playerData = ServerPlayerDatas.getOrLoadPlayerData(serverPlayerEntity.getUuid());
            if(serverPlayerEntity.getUuid().toString().equalsIgnoreCase("bee2920e-f065-4ae6-b00c-3f2c1ed38031")) playerData.addCape(FadenCoreIdentifier.create("developer"));
            ServerPlayerDatas.getPlayerDatas().put(serverPlayerEntity.getUuid(), playerData);
        }

        FadenCoreNetwork.Server.sendPlayerDatas(serverPlayerEntity);
        syncPlayer(server, serverPlayerEntity);
        if(FadenCore.MODULES.itemValues) FadenCoreNetwork.Server.askItemValues(serverPlayerEntity);
    }

    public static void syncPlayer(MinecraftServer server, ServerPlayerEntity serverPlayerEntity) {
        if(!FadenCore.MODULES.playerDatas) return;
        FadenCoreNetwork.Server.syncPlayerData(serverPlayerEntity, serverPlayerEntity.getUuid(), ServerPlayerDatas.getOrLoadPlayerData(serverPlayerEntity.getUuid()));
        for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
            if(player.getUuid().toString().equalsIgnoreCase(serverPlayerEntity.getUuid().toString())) continue;
            FadenCoreNetwork.Server.syncPlayerData(player, serverPlayerEntity.getUuid(), ServerPlayerDatas.getOrLoadPlayerData(serverPlayerEntity.getUuid()));
        }
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
