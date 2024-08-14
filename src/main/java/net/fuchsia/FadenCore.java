package net.fuchsia;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fuchsia.common.events.FadenCoreServerEvents;
import net.fuchsia.common.init.*;
import net.fuchsia.common.npc.NPCEntity;
import net.fuchsia.common.objects.command.types.NPCArgumentType;
import net.fuchsia.server.FadenCoreData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fuchsia.common.objects.CoinMap;
import net.fuchsia.common.objects.command.FadenCoreCommands;
import net.fuchsia.common.objects.command.types.CapeArgumentType;
import net.fuchsia.common.objects.command.types.RaceArgumentType;
import net.fuchsia.common.objects.command.types.RaceSubIdArgumentType;
import net.fuchsia.common.race.RaceSkinMap;
import net.fuchsia.config.FadenCoreConfig;
import net.fuchsia.config.FadenCoreConfigScreen;
import net.fuchsia.config.FadenCoreOptions;
import net.fuchsia.network.FadenCoreNetwork;
import net.fuchsia.util.FadenCoreIdentifier;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;

public class FadenCore implements ModInitializer {
	public static final String MOD_ID = "fadencore";
	public static final String MC_VERSION = "1.21";
	public static final String FADEN_VERSION = "0.0.1";
    public static final Logger LOGGER = LoggerFactory.getLogger("FadenCore");
	public static ModContainer CONTAINER;
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	public static final Random RANDOM = new Random();
	public static FadenCoreData DATA = new FadenCoreData();

	@Override
	public void onInitialize() {
		new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/client/");
		CONTAINER = FabricLoader.getInstance().getModContainer(MOD_ID).get();
		init();
        loadConfig();
		FadenCoreServerEvents.init();
		argumentTypes();
	}

	public static void loadConfig() {
		try {
			if(new File(FabricLoader.getInstance().getConfigDir().toFile() + "/faden.json").exists()) {
				FadenCoreOptions.setConfig(GSON.fromJson(new FileReader(FabricLoader.getInstance().getConfigDir().toFile() + "/faden.json"), FadenCoreConfig.class));
			} else {
				FadenCoreOptions.setConfig(new FadenCoreConfig());
			}
		} catch (IOException e) {
			FadenCoreOptions.setConfig(new FadenCoreConfig());
			throw new RuntimeException(e);
		}
	}

	public static void init() {
		FadenCoreNetwork.registerC2S();
		CommandRegistrationCallback.EVENT.register(new FadenCoreCommands());
		FadenCoreDataComponents.register();
		FadenCoreEntities.register();
		entityAttributes();
	}

	public static void setupFadenAddon(String modId) {
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
}