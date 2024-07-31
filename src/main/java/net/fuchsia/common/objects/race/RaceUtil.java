package net.fuchsia.common.objects.race;

import java.util.ArrayList;
import java.util.Random;

import net.fuchsia.common.objects.race.cosmetic.RaceCosmetic;
import net.fuchsia.common.objects.race.skin.provider.SkinProvider;
import net.fuchsia.network.FadenNetwork;
import net.fuchsia.server.PlayerData;
import net.fuchsia.server.ServerPlayerDatas;
import net.minecraft.server.network.ServerPlayerEntity;

public class RaceUtil {

    public static void setPlayerRace(ServerPlayerEntity player, Race race) {
        String sub_id = race.subIds()[new Random().nextInt(race.subIds().length)];
        setPlayerRace(player, race, sub_id);
    }

    public static void setPlayerRace(ServerPlayerEntity player, Race race, String sub_id) {
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
        PlayerData.RaceDataCosmetics dataCosmetics = new PlayerData.RaceDataCosmetics();
        if(headCosmetics.size() != 0) dataCosmetics.setHead(headCosmetics.get(random.nextInt(headCosmetics.size())).getId());
        if(chestCosmetics.size() != 0) dataCosmetics.setChest(chestCosmetics.get(random.nextInt(chestCosmetics.size())).getId());
        if(legCosmetics.size() != 0) dataCosmetics.setLeg(legCosmetics.get(random.nextInt(legCosmetics.size())).getId());
        if(bootsCosmetics.size() != 0) dataCosmetics.setLeg(bootsCosmetics.get(random.nextInt(bootsCosmetics.size())).getId());

        setPlayerRace(player, race, sub_id, dataCosmetics);

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
