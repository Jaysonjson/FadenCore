package net.fuchsia.common.race;

import net.fuchsia.common.race.cosmetic.RaceCosmetic;
import net.fuchsia.common.race.cosmetic.RaceCosmeticType;
import net.fuchsia.common.race.data.ServerRaceCache;
import net.fuchsia.network.FadenNetwork;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.ArrayList;
import java.util.Random;

public class RaceUtil {

    public static void setPlayerRace(ServerPlayerEntity player, IRace race) {
        String sub_id = race.subIds()[new Random().nextInt(race.subIds().length)];
        setPlayerRace(player, race, sub_id);
    }

    public static void setPlayerRace(ServerPlayerEntity player, IRace race, String sub_id) {
        String head_cosmetic = "";
        ArrayList<RaceCosmetic> headCosmetics = new ArrayList<>();
        for (RaceCosmetic cosmetic : race.getCosmeticPalette().getCosmetics(sub_id)) {
            if(cosmetic.getType() == RaceCosmeticType.HEAD) {
                headCosmetics.add(cosmetic);
            }
        }
        setPlayerRace(player, race, sub_id, headCosmetics.get(new Random().nextInt(headCosmetics.size())).getId());
    }


    public static void setPlayerRace(ServerPlayerEntity player, IRace race, String sub_id, String head_cosmetic) {
        String skinId = RaceSkinMap.getRandomSkin(race, sub_id);
        if(!skinId.isEmpty()) {
            RaceSkinUtil.setPlayerRaceSkin(player, skinId);
            ServerRaceCache.Cache.add(player.getUuid(), race.getId(), sub_id, head_cosmetic);

            for (ServerPlayerEntity serverPlayerEntity : player.getServer().getPlayerManager().getPlayerList()) {
                FadenNetwork.Server.sendRace(serverPlayerEntity, player.getUuid(), race.getId(), sub_id, head_cosmetic,false);
            }
            //FadenNetwork.Server.sendRace(player, player.getUuid(), race.getId(), sub_id, false);
        }
    }

}
