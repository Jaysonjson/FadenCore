package net.fuchsia;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Random;

import net.fabricmc.fabric.api.object.builder.v1.entity.FabricDefaultAttributeRegistry;
import net.fuchsia.common.events.FadenServerEvents;
import net.fuchsia.common.init.*;
import net.fuchsia.common.init.blocks.FadenBlocks;
import net.fuchsia.common.npc.NPCEntity;
import net.fuchsia.server.FadenData;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.ArgumentTypeRegistry;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.fuchsia.common.CheckSums;
import net.fuchsia.common.cape.online.OnlineCapes;
import net.fuchsia.common.objects.CoinMap;
import net.fuchsia.common.objects.command.FadenCommands;
import net.fuchsia.common.objects.command.types.CapeArgumentType;
import net.fuchsia.common.objects.command.types.RaceArgumentType;
import net.fuchsia.common.objects.command.types.RaceSubIdArgumentType;
import net.fuchsia.common.objects.race.RaceCosmetics;
import net.fuchsia.common.objects.race.RaceSkinMap;
import net.fuchsia.config.FadenConfig;
import net.fuchsia.config.FadenConfigScreen;
import net.fuchsia.config.FadenOptions;
import net.fuchsia.network.FadenNetwork;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.command.argument.serialize.ConstantArgumentSerializer;

public class Faden implements ModInitializer {
	public static final String MOD_ID = "faden";
	public static final String MC_VERSION = "1.21";
	public static final String FADEN_VERSION = "0.0.1";
    public static final Logger LOGGER = LoggerFactory.getLogger("Faden");
	public static ModContainer CONTAINER;
	public static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	public static final Random RANDOM = new Random();
	public static CheckSums CHECKSUMS = new CheckSums();
	public static FadenData DATA = new FadenData();

	@Override
	public void onInitialize() {
		new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/client/");
		CONTAINER = FabricLoader.getInstance().getModContainer(MOD_ID).get();
		init();
        loadConfig();
		FadenServerEvents.init();
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

		//I DONT USE ONLINE CHECKSUMS ANYMORE, BUT IF WE EVER DO IT AGAIN, UNCOMMENT THIS
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

	public static Screen openConfig(Screen parent) {
		if (FabricLoader.getInstance().isModLoaded("cloth-config2")) {
			return FadenConfigScreen.create(parent);
		}
		return null;
	}

}