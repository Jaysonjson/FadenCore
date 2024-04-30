package net.fuchsia.common.race;

import net.fuchsia.common.race.cosmetic.RaceCosmetic;
import net.fuchsia.common.race.cosmetic.RaceCosmeticType;
import net.fuchsia.common.race.data.RaceData;
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
        ArrayList<RaceCosmetic> chestCosmetics = new ArrayList<>();
        ArrayList<RaceCosmetic> legCosmetics = new ArrayList<>();
        ArrayList<RaceCosmetic> bootsCosmetics = new ArrayList<>();
        for (RaceCosmetic cosmetic : race.getCosmeticPalette().getCosmetics(sub_id)) {
            switch (cosmetic.getType()) {
                case HEAD -> {
                    headCosmetics.add(cosmetic);
                    break;
                }

                case CHEST -> {
                    chestCosmetics.add(cosmetic);
                    break;
                }

                case LEG -> {
                    legCosmetics.add(cosmetic);
                    break;
                }

                case BOOTS -> {
                    bootsCosmetics.add(cosmetic);
                    break;
                }
            }
        }
        Random random = new Random();
        RaceData.RaceDataCosmetics dataCosmetics = new RaceData.RaceDataCosmetics();
        if(headCosmetics.size() != 0) dataCosmetics.setHeadCosmeticId(headCosmetics.get(random.nextInt(headCosmetics.size())).getId());
        if(chestCosmetics.size() != 0) dataCosmetics.setChestCosmeticId(chestCosmetics.get(random.nextInt(chestCosmetics.size())).getId());
        if(legCosmetics.size() != 0) dataCosmetics.setLegCosmeticId(legCosmetics.get(random.nextInt(legCosmetics.size())).getId());
        if(bootsCosmetics.size() != 0) dataCosmetics.setLegCosmeticId(bootsCosmetics.get(random.nextInt(bootsCosmetics.size())).getId());

        setPlayerRace(player, race, sub_id, dataCosmetics);
    }


    public static void setPlayerRace(ServerPlayerEntity player, IRace race, String sub_id, RaceData.RaceDataCosmetics cosmetics) {
        String skinId = RaceSkinMap.getRandomSkin(race, sub_id);
        if(!skinId.isEmpty()) {
            RaceSkinUtil.setPlayerRaceSkin(player, skinId);
            ServerRaceCache.Cache.add(player.getUuid(), race.getId(), sub_id, cosmetics);

            for (ServerPlayerEntity serverPlayerEntity : player.getServer().getPlayerManager().getPlayerList()) {
                FadenNetwork.Server.sendRace(serverPlayerEntity, player.getUuid(), race.getId(), sub_id, cosmetics,false);
            }
            //FadenNetwork.Server.sendRace(player, player.getUuid(), race.getId(), sub_id, false);
        }
    }

}
