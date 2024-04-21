package net.fuchsia.common.race;

import net.fuchsia.common.race.data.ServerRaceCache;
import net.fuchsia.network.FadenNetwork;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Random;

public class RaceUtil {

    public static void setPlayerRace(ServerPlayerEntity player, IRace race) {
        setPlayerRace(player, race, race.subIds()[new Random().nextInt(race.subIds().length)]);
    }


    public static void setPlayerRace(ServerPlayerEntity player, IRace race, String sub_id) {
        String skinId = RaceSkinMap.getRandomSkin(race, sub_id);
        if(!skinId.isEmpty()) {
            RaceSkinUtil.setPlayerRaceSkin(player, skinId);
            ServerRaceCache.Cache.add(player.getUuid(), race.getId(), sub_id);

            for (ServerPlayerEntity serverPlayerEntity : player.getServer().getPlayerManager().getPlayerList()) {
                FadenNetwork.Server.sendRace(serverPlayerEntity, player.getUuid(), race.getId(), sub_id, false);
            }
            //FadenNetwork.Server.sendRace(player, player.getUuid(), race.getId(), sub_id, false);
        }
    }

}
