package net.fuchsia.common.objects.race;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import net.fuchsia.common.init.FadenRaces;
import org.jetbrains.annotations.Nullable;

import net.fuchsia.Faden;
import net.fuchsia.common.objects.race.skin.provider.SkinProvider;

public class RaceSkinMap {

	public static void addSkins() {
		for (Race value : FadenRaces.getRegistry().values()) {
			for (String s : value.subIds()) {
				if(!s.isEmpty()) loadSkin(value, s);
			}
		}
	}

	private static void loadSkin(IRace race, String subId) {
		String skinPath = getSkinPath(race, Faden.MOD_ID);
		if(Faden.CONTAINER.findPath(skinPath + subId + "/").isEmpty()) {
			Faden.LOGGER.error("Could not find Race Skins for SubId " + subId);
			return;
		}
		Path skins = Faden.CONTAINER.findPath(skinPath + subId + "/").get();
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
				race.getSkinMap().put(subId + "/" + id, SkinProvider.readSkin(inputStream));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static String getSkinPath(IRace race, String modid) {
		return "assets/" + modid + "/textures/skin/" + race.getId().toLowerCase() + "/";
	}

	@Nullable
	public static byte[] getSkin(String name) {
		for (Race value : FadenRaces.getRegistry().values()) {
			if(value.getSkinMap().containsKey(name)) return value.getSkinMap().get(name);
		}
		return null;
	}

	public static HashMap<String, byte[]> getAllMaps() {
		HashMap<String, byte[]> skins = new HashMap<>();
		for (Race value : FadenRaces.getRegistry().values()) {
			skins.putAll(value.getSkinMap());
		}
		return skins;
	}

	/**
	 * Returns an empty string if no skin could be found
	 */
	public static String getRandomSkin(IRace race, String subId) {
		Random random = new Random();
		ArrayList<String> buffer = new ArrayList<>();
		for (String s : race.getSkinMap().keySet()) {
			if(s.contains("/")) {
				int i = s.indexOf('/');
				String skinId = s.substring(0, i);
				if (skinId.equalsIgnoreCase(subId)) {
					buffer.add(s);
				}
			}
		}
		if(race.getSkinMap().isEmpty()) return "";
		//return race.getSkinMap().keySet().toArray(new String[] {})[random.nextInt(race.getSkinMap().size())];
		return buffer.get(random.nextInt(buffer.size()));
	}
}
