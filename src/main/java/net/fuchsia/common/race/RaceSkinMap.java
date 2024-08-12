package net.fuchsia.common.race;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import net.fabricmc.loader.api.ModContainer;
import net.fuchsia.common.init.FadenCoreRaces;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import net.fuchsia.FadenCore;
import net.fuchsia.common.race.skin.provider.SkinProvider;

public class RaceSkinMap {

	public static void addSkins(String modId, ModContainer modContainer) {
		for (Race value : FadenCoreRaces.getRegistry().values()) {
			for (String s : value.subIds()) {
				if(!s.isEmpty()) loadSkin(value, s, modId, modContainer);
			}
		}
	}

	private static void loadSkin(Race race, String subId, String modId, ModContainer modContainer) {
		String skinPath = getSkinPath(race, modId);
		if(modContainer.findPath(skinPath + subId + "/").isEmpty()) {
			FadenCore.LOGGER.error("Could not find Race Skins for SubId " + subId + " for mod " + modId);
			System.out.println("Could not find Race Skins for mod " + modId);
			return;
		}
		Path skins = modContainer.findPath(skinPath + subId + "/").get();
		try {
			Path[] ar = Files.list(skins).toArray(Path[]::new);
			for (int i = 0; i < ar.length; i++) {
				InputStream inputStream = Files.newInputStream(ar[i]);
				String id = ar[i].toString();
				if(id.contains("/")) {
					id = id.substring(ar[i].toString().lastIndexOf("/") + 1);
				} else {
					id = id.substring(ar[i].toString().lastIndexOf("\\") + 1);
				}
				id = id.substring(0, id.length() - 4);
				race.getSkinMap().put(Identifier.of(modId, "skin/" + subId + "/" + id), SkinProvider.readSkin(inputStream));
				FadenCore.LOGGER.debug("Loaded Skin: " + subId + "/" + id);
				System.out.println("Loaded Skin: " + subId + "/" + id);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static String getSkinPath(Race race, String modid) {
		return "assets/" + modid + "/textures/skin/" + race.getIdentifier().getPath().toLowerCase() + "/";
	}

	@Nullable
	public static byte[] getSkin(String name) {
		for (Race value : FadenCoreRaces.getRegistry().values()) {
			if(value.getSkinMap().containsKey(name)) return value.getSkinMap().get(name);
		}
		return null;
	}

	public static HashMap<Identifier, byte[]> getAllMaps() {
		HashMap<Identifier, byte[]> skins = new HashMap<>();
		for (Race value : FadenCoreRaces.getRegistry().values()) {
			skins.putAll(value.getSkinMap());
		}
		return skins;
	}

	/**
	 * Returns an empty string if no skin could be found
	 */
	public static String getRandomSkin(Race race, String subId) {
		Random random = new Random();
		ArrayList<String> buffer = new ArrayList<>();
		for (Identifier s : race.getSkinMap().keySet()) {
			if(s.toString().contains("/")) {
				String skinId = s.toString().substring(s.toString().indexOf('/') + 1);
				System.out.println("S1: " + skinId);
				skinId = skinId.substring(0, skinId.indexOf("/"));
				System.out.println(skinId);
				if (skinId.equalsIgnoreCase(subId)) {
					buffer.add(s.toString());
				}
			}
		}
		if(race.getSkinMap().isEmpty()) return "";
		//return race.getSkinMap().keySet().toArray(new String[] {})[random.nextInt(race.getSkinMap().size())];
		return buffer.get(random.nextInt(buffer.size()));
	}
}
