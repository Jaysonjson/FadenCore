package net.fuchsia.race;

import net.fuchsia.network.FadenNetwork;
import net.fuchsia.race.skin.server.ServerSkinCache;
import net.minecraft.server.network.ServerPlayerEntity;

public class RaceSkinUtil {

	public static void setPlayerRaceSkin(ServerPlayerEntity player, String id) {
		RaceSkinMap.Cache.add(player.getUuid(), id);
		ServerSkinCache.setSkin(player.getUuid(), id);
		for (ServerPlayerEntity serverPlayerEntity : player.getServer().getPlayerManager().getPlayerList()) {
			FadenNetwork.Server.sendRaceSkin(serverPlayerEntity, player.getUuid(), id);
		}
	}
	
}
