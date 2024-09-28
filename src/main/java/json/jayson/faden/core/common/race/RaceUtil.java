package json.jayson.faden.core.common.race;

import java.util.Random;

import json.jayson.faden.core.server.PlayerData;
import json.jayson.faden.core.server.ServerPlayerDatas;
import net.minecraft.server.network.ServerPlayerEntity;

public class RaceUtil {

    public static void setPlayerRace(ServerPlayerEntity player, FadenCoreRace fadenCoreRace) {
        String subId = fadenCoreRace.subIds()[new Random().nextInt(fadenCoreRace.subIds().length)];
        setPlayerRace(player, fadenCoreRace, subId);
    }

    public static void setPlayerRace(ServerPlayerEntity player, FadenCoreRace fadenCoreRace, String subId) {
        setPlayerRace(player, fadenCoreRace, subId, fadenCoreRace.randomizeCosmetics(subId));

    }


    public static void setPlayerRace(ServerPlayerEntity player, FadenCoreRace fadenCoreRace, String sub_id, PlayerData.RaceDataCosmetics cosmetics) {
        String skinId = RaceSkinMap.getRandomSkin(fadenCoreRace, sub_id);
        System.out.println(skinId + " : SKIN ID");
        if(!skinId.isEmpty()) {
            PlayerData data = ServerPlayerDatas.getOrLoadPlayerData(player.getUuid());
            data.getRaceSaveData().setSkin(skinId);
            data.getRaceSaveData().setRace(fadenCoreRace.getIdentifier().toString());
            data.getRaceSaveData().setRaceSub(sub_id);
            data.getRaceSaveData().setCosmetics(cosmetics);
            data.sync();
            ServerPlayerDatas.save(player.getUuid());
            fadenCoreRace.applyStats(player);
            //FadenNetwork.Server.sendRace(player, player.getUuid(), race.getId(), sub_id, false);
        }
    }
}
