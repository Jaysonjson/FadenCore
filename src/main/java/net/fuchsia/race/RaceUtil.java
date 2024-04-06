package net.fuchsia.race;

import net.fuchsia.network.FadenNetwork;
import net.fuchsia.race.skin.server.ServerSkinCache;
import net.minecraft.server.network.ServerPlayerEntity;

public class RaceUtil {

    public static void setPlayerRace(ServerPlayerEntity player, Race race) {
        String id = RaceSkinMap.getRandomSkin(race);
        RaceSkinMap.addToCache(player.getUuid(), id);
        ServerSkinCache.PLAYER_SKINS.remove(player.getUuid());
        ServerSkinCache.PLAYER_SKINS.put(player.getUuid(), id);
        for (ServerPlayerEntity serverPlayerEntity : player.getServer().getPlayerManager().getPlayerList()) {
            FadenNetwork.Server.sendRaceSkin(serverPlayerEntity, player.getUuid(), id);
        }
    }

}
