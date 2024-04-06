package net.fuchsia.race;

import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Random;

public class RaceSkinMap {

	public static HashMap<String, byte[]> ELF_SKINS = new HashMap<>();
	public static HashMap<String, byte[]> HUMAN_SKINS = new HashMap<>();
	public static HashMap<String, byte[]> RABBIT_SKINS = new HashMap<>();
	
	public static void loadSkins() {
		
	}

	@Nullable
	public static byte[] getSkin(String name) {
		if(ELF_SKINS.containsKey(name)) return ELF_SKINS.get(name);
		if(HUMAN_SKINS.containsKey(name)) return HUMAN_SKINS.get(name);
		if(RABBIT_SKINS.containsKey(name)) return RABBIT_SKINS.get(name);
		return null;
	}
	
	/*
	 * Returns empty string when no skin could be found
	 * */
	public static String getRandomSkin(Race race) {
		Random random = new Random();
		switch (race) {
			case ELF -> {
				return ELF_SKINS.keySet().toArray(new String[] {})[random.nextInt(ELF_SKINS.size())];
			}

			case HUMAN -> {
				return HUMAN_SKINS.keySet().toArray(new String[] {})[random.nextInt(HUMAN_SKINS.size())];
			}

			case RABBIT -> {
				return RABBIT_SKINS.keySet().toArray(new String[] {})[random.nextInt(RABBIT_SKINS.size())];
			}
		}
		return "";
	}
	
}
