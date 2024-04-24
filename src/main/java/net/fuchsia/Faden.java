package net.fuchsia;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.fuchsia.common.cape.FadenCapes;
import net.fuchsia.common.init.FadenSoundEvents;
import net.fuchsia.common.objects.command.types.RaceArgumentType;
import net.fuchsia.common.objects.command.types.RaceSubIdArgumentType;
import net.fuchsia.common.race.RaceCosmetics;
import net.fuchsia.common.race.data.ServerRaceCache;
import net.fuchsia.config.FadenConfigScreen;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.GameMenuScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;
import net.minecraft.server.command.LocateCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fuchsia.common.objects.command.FadenCommands;
import net.fuchsia.common.init.FadenItems;
import net.fuchsia.common.init.FadenTabs;
import net.fuchsia.common.objects.CoinMap;
import net.fuchsia.common.data.ItemValues;
import net.fuchsia.network.FadenNetwork;
import net.fuchsia.common.race.RaceSkinMap;
import net.fuchsia.common.race.skin.server.ServerSkinCache;
import net.minecraft.server.network.ServerPlayerEntity;

public class Faden implements ModInitializer {
	public static final String MOD_ID = "faden";
	public static final String MC_VERSION = "1.20.4";
	public static final String FADEN_VERSION = "0.0.1";
    public static final Logger LOGGER = LoggerFactory.getLogger("Faden");
	public static ModContainer CONTAINER;
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

	@Override
	public void onInitialize() {
		CONTAINER = FabricLoader.getInstance().getModContainer(MOD_ID).get();
		FadenCapes.init();
		RaceCosmetics.add();
		FadenSoundEvents.register();
		FadenItems.register();
		FadenTabs.register();
		CoinMap.reloadCoins();
		FadenNetwork.registerC2S();
		ItemValues.add();
		CommandRegistrationCallback.EVENT.register(new FadenCommands());
		RaceSkinMap.addSkins();

		ServerLifecycleEvents.SERVER_STARTING.register(server -> {
			RaceSkinMap.Cache.load();
			ServerRaceCache.Cache.load();
		});

		ServerLifecycleEvents.SERVER_STOPPING.register(server -> {
			RaceSkinMap.Cache.save();
			ServerRaceCache.Cache.save();
		});

		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			ServerPlayerEntity serverPlayerEntity = handler.getPlayer();
			FadenNetwork.Server.sendAllRaces(serverPlayerEntity);
			RaceSkinMap.Cache.sendUpdate(serverPlayerEntity, server);
			ServerRaceCache.Cache.sendUpdate(serverPlayerEntity, server, false);
		});


		ServerPlayConnectionEvents.DISCONNECT.register((handler, sender) -> {
			ServerSkinCache.PLAYER_SKINS.remove(handler.getPlayer().getUuid());
			for (ServerPlayerEntity serverPlayerEntity : sender.getPlayerManager().getPlayerList()) {
				FadenNetwork.Server.removeSkin(serverPlayerEntity, handler.getPlayer().getUuid());
			}
			ServerRaceCache.Cache.sendUpdate(handler.getPlayer(), handler.getPlayer().server, true);
		});
		ArgumentTypeRegistry.registerArgumentType(FadenIdentifier.create("race_sub_id_argument"), RaceSubIdArgumentType.class, ConstantArgumentSerializer.of(RaceSubIdArgumentType::empty));
		ArgumentTypeRegistry.registerArgumentType(FadenIdentifier.create("race_argument"), RaceArgumentType.class, ConstantArgumentSerializer.of(RaceArgumentType::empty));
	}

	public static Screen openConfig(Screen parent) {
		if (FabricLoader.getInstance().isModLoaded("cloth-config2")) {
			return FadenConfigScreen.create(parent);
		}
		return null;
	}

}