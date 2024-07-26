package net.fuchsia;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fuchsia.common.init.*;
import net.fuchsia.common.npc.NPCEntity;
import net.fuchsia.util.NetworkUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fuchsia.common.CheckSums;
import net.fuchsia.common.cape.online.OnlineCapes;
import net.fuchsia.common.data.ItemValues;
import net.fuchsia.common.objects.CoinMap;
import net.fuchsia.common.objects.command.FadenCommands;
import net.fuchsia.common.objects.command.types.CapeArgumentType;
import net.fuchsia.common.objects.command.types.RaceArgumentType;
import net.fuchsia.common.objects.command.types.RaceSubIdArgumentType;
import net.fuchsia.common.quest.FadenQuests;
import net.fuchsia.common.quest.data.QuestCache;
import net.fuchsia.common.race.RaceCosmetics;
import net.fuchsia.common.race.RaceSkinMap;
import net.fuchsia.common.race.data.ServerRaceCache;
import net.fuchsia.common.race.skin.server.ServerSkinCache;
import net.fuchsia.config.FadenConfig;
import net.fuchsia.config.FadenConfigScreen;
import net.fuchsia.config.FadenOptions;
import net.fuchsia.network.FadenNetwork;
import net.fuchsia.server.ServerPlayerDatas;
import net.fuchsia.util.FadenIdentifier;
import net.fuchsia.util.FadenOnlineUtil;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;
import net.minecraft.server.network.ServerPlayerEntity;

public class Faden implements ModInitializer {
	public static final String MOD_ID = "faden";
	public static final String MC_VERSION = "1.21";
	public static final String FADEN_VERSION = "0.0.1";
    public static final Logger LOGGER = LoggerFactory.getLogger("Faden");
	public static ModContainer CONTAINER;
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	public static final Random RANDOM = new Random();
	public static CheckSums CHECKSUMS = new CheckSums();

	@Override
	public void onInitialize() {
		new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/client/");
		CONTAINER = FabricLoader.getInstance().getModContainer(MOD_ID).get();
		init();
        loadConfig();
		serverEvents();
		argumentTypes();
	}

	public static void loadConfig() {
		try {
			if(new File(FabricLoader.getInstance().getConfigDir().toFile() + "/faden.json").exists()) {
				FadenOptions.setConfig(GSON.fromJson(new FileReader(FabricLoader.getInstance().getConfigDir().toFile() + "/faden.json"), FadenConfig.class));
			} else {
				FadenOptions.setConfig(new FadenConfig());
			}
		} catch (IOException e) {
			FadenOptions.setConfig(new FadenConfig());
			throw new RuntimeException(e);
		}
	}

	public static void init() {

		//I DONT USE CHECKSUMS ANYMORE, BUT IF WE EVER DO IT AGAIN, UNCOMMENT THIS
		/*try {
			CHECKSUMS = Faden.GSON.fromJson(FadenOnlineUtil.getJSONData("https://raw.githubusercontent.com/FuchsiaTeam/FadenData/main/checksums.json"), CheckSums.class);
		} catch (Exception ignored) {}
		*/

		RaceCosmetics.add();
		FadenSoundEvents.register();
		FadenItems.register();
		FadenTabs.register();
		CoinMap.reloadCoins();
		FadenNetwork.registerC2S();
		CommandRegistrationCallback.EVENT.register(new FadenCommands());
		RaceSkinMap.addSkins();
		FadenDataComponents.register();
		FadenCloths.register();
		FadenGear.register();
		FadenBlocks.register();
		OnlineCapes.retrieve();
		FadenEntities.register();
		entityAttributes();
	}

	public static void entityAttributes() {
		FabricDefaultAttributeRegistry.register(FadenEntities.NPC, NPCEntity.createMobAttributes());
	}

	public static void argumentTypes() {
		ArgumentTypeRegistry.registerArgumentType(FadenIdentifier.create("cape_argument"), CapeArgumentType.class, ConstantArgumentSerializer.of(CapeArgumentType::empty));
		ArgumentTypeRegistry.registerArgumentType(FadenIdentifier.create("race_sub_id_argument"), RaceSubIdArgumentType.class, ConstantArgumentSerializer.of(RaceSubIdArgumentType::empty));
		ArgumentTypeRegistry.registerArgumentType(FadenIdentifier.create("race_argument"), RaceArgumentType.class, ConstantArgumentSerializer.of(RaceArgumentType::empty));
	}

	public static void serverEvents() {


		ServerLifecycleEvents.SERVER_STARTING.register(server -> {
			new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/" + Faden.MC_VERSION + "/player_datas/").mkdirs();
			RaceSkinMap.Cache.load();
			ServerRaceCache.Cache.load();
			QuestCache.load();
			ServerPlayerDatas.SERVER = server;
			ItemValues.load();
		});

		ServerLifecycleEvents.AFTER_SAVE.register((server, flush, force) -> {
			RaceSkinMap.Cache.save();
			ServerRaceCache.Cache.save();
			QuestCache.save();
			ServerPlayerDatas.save();
			ItemValues.save();
		});

		ServerPlayConnectionEvents.JOIN.register((handler, sender, server) -> {
			ServerPlayerEntity serverPlayerEntity = handler.getPlayer();
			NetworkUtils.fullyUpdatePlayer(serverPlayerEntity, server);
			//TODO REMVOE: QUEST TESTING
			FadenQuests.TEST.startQuest(serverPlayerEntity.getUuid());
		});


		ServerPlayConnectionEvents.DISCONNECT.register((handler, sender) -> {
			ServerSkinCache.removeSkin(handler.getPlayer().getUuid());
			for (ServerPlayerEntity serverPlayerEntity : sender.getPlayerManager().getPlayerList()) {
				FadenNetwork.Server.removeSkin(serverPlayerEntity, handler.getPlayer().getUuid());
			}
			ServerRaceCache.Cache.sendUpdate(handler.getPlayer(), handler.getPlayer().server, true);
		});
	}


	public static Screen openConfig(Screen parent) {
		if (FabricLoader.getInstance().isModLoaded("cloth-config2")) {
			return FadenConfigScreen.create(parent);
		}
		return null;
	}

}