package json.jayson.faden.core.common.events;

import json.jayson.faden.core.common.race.FadenCoreRace;
import json.jayson.faden.core.util.SaveUtil;
import net.fabricmc.fabric.api.entity.event.v1.ServerPlayerEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import json.jayson.faden.core.FadenCore;
import json.jayson.faden.core.common.data.ItemValues;
import json.jayson.faden.core.common.quest.data.QuestCache;
import json.jayson.faden.core.server.ServerPlayerDatas;
import json.jayson.faden.core.util.NetworkUtils;
import net.minecraft.server.network.ServerPlayerEntity;

import java.io.File;

public class FadenCoreServerEvents {

    public static void init() {
        ServerLifecycleEvents.SERVER_STARTING.register(server -> {
            ServerPlayerDatas.SERVER = server;
            new File(SaveUtil.getFolder() + "/player_datas/").mkdirs();
            new File(SaveUtil.getFolder()).mkdirs();
            QuestCache.load();
            ItemValues.load();
            FadenCore.DATA.load();
            FadenCore.VALUE_DATA.load();
        });

        ServerLifecycleEvents.AFTER_SAVE.register((server, flush, force) -> {
            QuestCache.save();
            ServerPlayerDatas.save();
            ItemValues.save();
            FadenCore.DATA.save();
            FadenCore.VALUE_DATA.save();
        });

        ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
            ServerPlayerEntity serverPlayerEntity = handler.getPlayer();
            NetworkUtils.fullyUpdatePlayer(serverPlayerEntity, server);
        });

        ServerPlayerEvents.AFTER_RESPAWN.register((oldPlayer, newPlayer, alive) -> {
            FadenCoreRace fadenCoreRace = ServerPlayerDatas.getOrLoadPlayerData(newPlayer.getUuid()).getRaceSaveData().getRace();
            if(fadenCoreRace != null) fadenCoreRace.applyStats(newPlayer);
        });

        ServerPlayConnectionEvents.DISCONNECT.register((handler, sender) -> {
            ServerPlayerDatas.unloadPlayerData(handler.getPlayer().getUuid());
        });

        ServerTickEvents.END_WORLD_TICK.register(world -> {
            //FadenMusicInstances.getInstances().values().removeIf(instance -> instance.getInstruments().isEmpty());
        });
    }

}
