package json.jayson.faden.core.common.race;

import java.util.Random;

import json.jayson.faden.core.server.PlayerData;
import json.jayson.faden.core.server.ServerPlayerDatas;
import net.minecraft.server.network.ServerPlayerEntity;

public class RaceUtil {

    public static void setPlayerRace(ServerPlayerEntity player, Race race) {
        String subId = race.subIds()[new Random().nextInt(race.subIds().length)];
        setPlayerRace(player, race, subId);
    }

    public static void setPlayerRace(ServerPlayerEntity player, Race race, String subId) {
        setPlayerRace(player, race, subId, race.randomizeCosmetics(subId));

    }


    public static void setPlayerRace(ServerPlayerEntity player, Race race, String sub_id, PlayerData.RaceDataCosmetics cosmetics) {
        String skinId = RaceSkinMap.getRandomSkin(race, sub_id);
        System.out.println(skinId + " : SKIN ID");
        if(!skinId.isEmpty()) {
            PlayerData data = ServerPlayerDatas.getOrLoadPlayerData(player.getUuid());
            data.getRaceSaveData().setSkin(skinId);
            data.getRaceSaveData().setRace(race.getIdentifier().toString());
            data.getRaceSaveData().setRaceSub(sub_id);
            data.getRaceSaveData().setCosmetics(cosmetics);
            data.sync();
            ServerPlayerDatas.save(player.getUuid());
            race.applyStats(player);
            //FadenNetwork.Server.sendRace(player, player.getUuid(), race.getId(), sub_id, false);
        }
    }
}
