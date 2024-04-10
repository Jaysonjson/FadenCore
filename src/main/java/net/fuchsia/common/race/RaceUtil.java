package net.fuchsia.common.race;

import net.minecraft.server.network.ServerPlayerEntity;

public class RaceUtil {

    public static void setPlayerRace(ServerPlayerEntity player, IRace race) {
        String id = RaceSkinMap.getRandomSkin(race);
        if(!id.isEmpty()) {
        	RaceSkinUtil.setPlayerRaceSkin(player, id);
        }
    }

}
