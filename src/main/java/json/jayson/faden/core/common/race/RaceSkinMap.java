package json.jayson.faden.core.common.race;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import json.jayson.faden.core.registry.FadenCoreRegistry;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import json.jayson.faden.core.FadenCore;
import json.jayson.faden.core.common.race.skin.provider.SkinProvider;

public class RaceSkinMap {

	public static void addSkins(String modId, ModContainer modContainer) {
		for (Race value : FadenCoreRegistry.RACE) {
			for (String s : value.subIds()) {
				if(!s.isEmpty()) loadSkin(value, s, modId, modContainer);
			}
		}
	}

	private static void loadSkin(Race race, String subId, String modId, ModContainer modContainer) {
		if(!race.hasSkins()) return;
		String skinPath = getSkinPath(race, modId);
		Path skinDir = modContainer.findPath(skinPath + subId + "/").orElse(null);

		if (skinDir == null) {
			FadenCore.LOGGER.error("Could not find Race Skins for SubId " + subId + " for mod " + modId);
			return;
		}

		try {
			Files.walk(skinDir)
					.filter(Files::isRegularFile)
					.forEach(path -> {
						try (InputStream inputStream = Files.newInputStream(path)) {
							String id = path.getFileName().toString();
							id = id.substring(0, id.lastIndexOf('.'));
							race.getSkinMap().put(Identifier.of(modId, "skin/" + subId + "/" + id), SkinProvider.readSkin(inputStream));
							FadenCore.LOGGER.debug("Loaded Skin: " + subId + "/" + id);
						} catch (IOException e) {
							FadenCore.LOGGER.error("Error loading skin from path: " + path, e);
						}
					});
		} catch (IOException e) {
			FadenCore.LOGGER.error("Error walking through skin directory: " + skinDir, e);
		}
	}

	public static String getSkinPath(Race race, String modid) {
		return "assets/" + modid + "/textures/skin/" + race.getIdentifier().getPath().toLowerCase() + "/";
	}

	@Nullable
	public static byte[] getSkin(String name) {
		for (Race value : FadenCoreRegistry.RACE) {
			if(value.getSkinMap().containsKey(name)) return value.getSkinMap().get(name);
		}
		return null;
	}

	public static HashMap<Identifier, byte[]> getAllMaps() {
		HashMap<Identifier, byte[]> skins = new HashMap<>();
		for (Race value : FadenCoreRegistry.RACE) {
			skins.putAll(value.getSkinMap());
		}
		return skins;
	}

	/**
	 * Returns an empty string if no skin could be found
	 */
	public static String getRandomSkin(Race race, String subId) {
		if(!race.hasSkins()) return "";
		Random random = new Random();
		ArrayList<String> buffer = new ArrayList<>();
		for (Identifier s : race.getSkinMap().keySet()) {
			String[] parts = s.toString().split("/");
			if (parts.length > 1) {
				String skinId = parts[1];
				if (skinId.equalsIgnoreCase(subId)) {
					buffer.add(s.toString());
				}
			}
		}
		if (buffer.isEmpty()) return "";
		return buffer.get(random.nextInt(buffer.size()));
	}
}
