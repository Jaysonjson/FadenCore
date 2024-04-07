package net.fuchsia.race;

import net.fuchsia.network.FadenNetwork;
import net.fuchsia.race.skin.server.ServerSkinCache;
import net.minecraft.server.network.ServerPlayerEntity;

public class RaceUtil {

    public static void setPlayerRace(ServerPlayerEntity player, Race race) {
        String id = RaceSkinMap.getRandomSkin(race);
        RaceSkinMap.setPlayerRaceSkin(player, id);
    }

}
