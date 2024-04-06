package net.fuchsia;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.event.server.ServerStartCallback;
import net.fuchsia.race.RaceSkinMap;
import net.fuchsia.race.skin.server.ServerSkinCache;
import net.minecraft.nbt.NbtCompound;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fuchsia.command.FadenCommands;
import net.fuchsia.common.objects.CoinMap;
import net.fuchsia.common.init.FadenItems;
import net.fuchsia.common.init.FadenTabs;
import net.fuchsia.data.ItemValues;
import net.fuchsia.network.FadenNetwork;
import net.fabricmc.api.ModInitializer;

import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.UUID;

public class Faden implements ModInitializer {
	public static final String MOD_ID = "faden";
	public static final String MC_VERSION = "1.20.4";
	public static final String FADEN_VERSION = "0.0.1";
    public static final Logger LOGGER = LoggerFactory.getLogger("faden");
	public static ModContainer CONTAINER;

	@Override
	public void onInitialize() {
		CONTAINER = FabricLoader.getInstance().getModContainer(MOD_ID).get();
		FadenItems.register();
		FadenTabs.register();
		CoinMap.reloadCoins();
		FadenNetwork.registerC2S();
		ItemValues.add();
		CommandRegistrationCallback.EVENT.register(new FadenCommands());
		RaceSkinMap.addSkins();

		ServerLifecycleEvents.SERVER_STARTING.register(server -> {
			RaceSkinMap.loadCache();
		});

		ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
			RaceSkinMap.saveCache();
		});

		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			ServerPlayerEntity serverPlayerEntity = handler.getPlayer();
			FadenNetwork.Server.sendAllRaces(serverPlayerEntity);
			if(RaceSkinMap.CACHE.contains(serverPlayerEntity.getUuid().toString())) {
				NbtCompound compound = RaceSkinMap.CACHE.getCompound(serverPlayerEntity.getUuid().toString());
				for (ServerPlayerEntity playerEntity : server.getPlayerManager().getPlayerList()) {
					FadenNetwork.Server.sendRaceSkin(playerEntity, serverPlayerEntity.getUuid(), compound.getString("id"));
				}
			}
		});


		ServerPlayConnectionEvents.DISCONNECT.register((handler, sender) -> {
			ServerSkinCache.PLAYER_SKINS.remove(handler.getPlayer());
			for (ServerPlayerEntity serverPlayerEntity : sender.getPlayerManager().getPlayerList()) {
				FadenNetwork.Server.removeSkin(serverPlayerEntity, handler.getPlayer().getUuid());
			}
		});
	}
}