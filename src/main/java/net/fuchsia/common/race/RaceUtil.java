package net.fuchsia.common.race;

import java.util.Random;

import net.fuchsia.common.race.skin.provider.SkinProvider;
import net.fuchsia.server.PlayerData;
import net.fuchsia.server.ServerPlayerDatas;
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
        if(!skinId.isEmpty()) {
            PlayerData data = ServerPlayerDatas.getOrLoadPlayerData(player.getUuid());
            data.getRaceSaveData().setSkin(SkinProvider.getSkinIdentifier(skinId).toString());
            data.getRaceSaveData().setRace(race.getId());
            data.getRaceSaveData().setRaceSub(sub_id);
            data.getRaceSaveData().setCosmetics(cosmetics);
            data.sync();
            race.applyEntityAttributes(player);
            //FadenNetwork.Server.sendRace(player, player.getUuid(), race.getId(), sub_id, false);
        }
    }
}
