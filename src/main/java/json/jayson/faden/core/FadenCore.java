package json.jayson.faden.core;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Random;

import json.jayson.faden.core.common.data.listeners.InstrumentedMusicDataListener;
import json.jayson.faden.core.common.init.FadenCoreBlocks;
import json.jayson.faden.core.common.init.FadenCoreDataComponents;
import json.jayson.faden.core.common.init.FadenCoreEntities;
import json.jayson.faden.core.common.init.FadenCoreTabs;
import json.jayson.faden.core.registry.FadenCoreRegistry;
import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import json.jayson.faden.core.common.events.FadenCoreServerEvents;
import json.jayson.faden.core.common.npc.NPCEntity;
import json.jayson.faden.core.common.objects.command.types.NPCArgumentType;
import json.jayson.faden.core.server.FadenCoreData;
import net.fabricmc.fabric.api.resource.ResourceManagerHelper;
import net.minecraft.resource.ResourceType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import json.jayson.faden.core.common.objects.CoinMap;
import json.jayson.faden.core.common.objects.command.FadenCoreCommands;
import json.jayson.faden.core.common.objects.command.types.CapeArgumentType;
import json.jayson.faden.core.common.objects.command.types.RaceArgumentType;
import json.jayson.faden.core.common.objects.command.types.RaceSubIdArgumentType;
import json.jayson.faden.core.common.race.RaceSkinMap;
import json.jayson.faden.core.config.FadenCoreConfig;
import json.jayson.faden.core.config.FadenCoreConfigScreen;
import json.jayson.faden.core.config.FadenCoreOptions;
import json.jayson.faden.core.network.FadenCoreNetwork;
import json.jayson.faden.core.util.FadenCoreIdentifier;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;

public class FadenCore implements ModInitializer {
	public static final String MOD_ID = "fadencore";
	//public static final String MC_VERSION = "1.21";
    public static final Logger LOGGER = LoggerFactory.getLogger("FadenCore");
	public static ModContainer CONTAINER;
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	public static final Random RANDOM = new Random();
	public static FadenCoreData DATA = new FadenCoreData();
	public static final FadenCoreModules MODULES = new FadenCoreModules();
	public static HashMap<String, FadenCoreApi> ADDONS = new HashMap<>();

	@Override
	public void onInitialize() {
		new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/client/");
		CONTAINER = FabricLoader.getInstance().getModContainer(MOD_ID).get();
		FadenCoreServerEvents.init();
		init();
        loadConfig();
		resourceReloadListener();
		argumentTypes();
	}

	public void resourceReloadListener() {
		ResourceManagerHelper.get(ResourceType.SERVER_DATA).registerReloadListener(new InstrumentedMusicDataListener());
	}

	public static void loadConfig() {
		try {
			if(new File(FabricLoader.getInstance().getConfigDir().toFile() + "/fadencore.json").exists()) {
				FadenCoreOptions.setConfig(GSON.fromJson(new FileReader(FabricLoader.getInstance().getConfigDir().toFile() + "/fadencore.json"), FadenCoreConfig.class));
			} else {
				FadenCoreOptions.setConfig(new FadenCoreConfig());
			}
		} catch (IOException e) {
			FadenCoreOptions.setConfig(new FadenCoreConfig());
		}
	}

	public static void init() {
		FadenCoreBlocks.init();
		FadenCoreRegistry.init();
		FadenCoreNetwork.registerC2S();
		CommandRegistrationCallback.EVENT.register(new FadenCoreCommands());
		FadenCoreDataComponents.init();
		FadenCoreEntities.init();
		FadenCoreTabs.init();
		entityAttributes();
		FabricLoader.getInstance().getEntrypointContainers("fadencore", FadenCoreApi.class).forEach(entrypoint -> {
			String id = entrypoint.getProvider().getMetadata().getId();
			FadenCoreApi fadenCoreApi = entrypoint.getEntrypoint();
			fadenCoreApi.onInitalize();
			if(fadenCoreApi.enablePlayerData()) MODULES.playerDatas = true;
			if(fadenCoreApi.enableQuests()) MODULES.quests = true;
			ADDONS.put(id, fadenCoreApi);
			setupFadenAddon(id);
		});
	}

	private static void setupFadenAddon(String modId) {
		RaceSkinMap.addSkins(modId, FabricLoader.getInstance().getModContainer(modId).get());
		CoinMap.reloadCoins();
	}

	public static void entityAttributes() {
		FabricDefaultAttributeRegistry.register(FadenCoreEntities.NPC, NPCEntity.createMobAttributes());
	}

	public static void argumentTypes() {
		ArgumentTypeRegistry.registerArgumentType(FadenCoreIdentifier.create("cape_argument"), CapeArgumentType.class, ConstantArgumentSerializer.of(CapeArgumentType::empty));
		ArgumentTypeRegistry.registerArgumentType(FadenCoreIdentifier.create("race_sub_id_argument"), RaceSubIdArgumentType.class, ConstantArgumentSerializer.of(RaceSubIdArgumentType::empty));
		ArgumentTypeRegistry.registerArgumentType(FadenCoreIdentifier.create("race_argument"), RaceArgumentType.class, ConstantArgumentSerializer.of(RaceArgumentType::empty));
		ArgumentTypeRegistry.registerArgumentType(FadenCoreIdentifier.create("npc_argument"), NPCArgumentType.class, ConstantArgumentSerializer.of(NPCArgumentType::empty));
	}

	public static Screen openConfig(Screen parent) {
		if (FabricLoader.getInstance().isModLoaded("cloth-config2")) {
			return FadenCoreConfigScreen.create(parent);
		}
		return null;
	}

	public static class FadenCoreModules {
		public boolean playerDatas = false;
		public boolean quests = false;
	}

}