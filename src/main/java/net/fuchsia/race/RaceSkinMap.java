package net.fuchsia.race;

import java.util.HashMap;
import java.util.Random;

public class RaceSkinMap {

	public static HashMap<String, byte[]> ELF_SKINS = new HashMap<>();
	public static HashMap<String, byte[]> HUMAN_SKINS = new HashMap<>();
	public static HashMap<String, byte[]> RABBIT_SKINS = new HashMap<>();
	
	public static void loadSkins() {
		
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
				return HUMAN_SKINS.keySet().toArray(new String[] {})[random.nextInt(ELF_SKINS.size())];
			}

			case RABBIT -> {
				return RABBIT_SKINS.keySet().toArray(new String[] {})[random.nextInt(ELF_SKINS.size())];
			}
		}
		return "";
	}
	
}
