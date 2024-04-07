package net.fuchsia.race;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.jetbrains.annotations.Nullable;

import net.fabricmc.loader.api.FabricLoader;
import net.fuchsia.Faden;
import net.fuchsia.race.skin.provider.SkinProvider;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtSizeTracker;

public class RaceSkinMap {

	public static HashMap<String, byte[]> ELF_SKINS = new HashMap<>();
	public static HashMap<String, byte[]> HUMAN_SKINS = new HashMap<>();
	public static HashMap<String, byte[]> RABBIT_SKINS = new HashMap<>();




	public static void addSkins() {
		ELF_SKINS = loadSkin(Race.ELF);
		HUMAN_SKINS = loadSkin(Race.HUMAN);
		RABBIT_SKINS = loadSkin(Race.RABBIT);
	}

	public static NbtCompound CACHE = new NbtCompound();
	private static final Path CACHE_PATH = new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/" + Faden.MC_VERSION + "/skin.nbt").toPath();

	public static void loadCache() {
		try {
			if(CACHE_PATH.toFile().exists()) {
				CACHE = NbtIo.readCompressed(CACHE_PATH, NbtSizeTracker.ofUnlimitedBytes());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void addToCache(UUID uuid, String id) {
		new Thread(() -> {
			if(CACHE.contains(id)) CACHE.remove(id);
			NbtCompound tag = new NbtCompound();
			tag.putString("id", id);
			CACHE.put(uuid.toString(), tag);
			new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/" + Faden.MC_VERSION + "/").mkdirs();
			try {
				NbtIo.writeCompressed(CACHE,  CACHE_PATH);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}).start();
	}

	public static void saveCache() {
		new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/" + Faden.MC_VERSION + "/").mkdirs();
		try {
			NbtIo.writeCompressed(CACHE,  CACHE_PATH);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static HashMap<String, byte[]>  loadSkin(Race race) {
		HashMap<String, byte[]> map = new HashMap<>();

		String skinPath = getSkinPath(race, Faden.MOD_ID);
		Path skins = Faden.CONTAINER.findPath(skinPath).get();
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
				map.put(id, SkinProvider.readSkin(inputStream));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return map;
	}


	public static String getSkinPath(Race race, String modid) {
		return "assets/" + modid + "/textures/skins/" + race.name().toLowerCase() + "/";
	}


	@Nullable
	public static byte[] getSkin(String name) {
		if(ELF_SKINS.containsKey(name)) return ELF_SKINS.get(name);
		if(HUMAN_SKINS.containsKey(name)) return HUMAN_SKINS.get(name);
		if(RABBIT_SKINS.containsKey(name)) return RABBIT_SKINS.get(name);
		return null;
	}

	public static HashMap<String, byte[]> getAllMaps() {
		HashMap<String, byte[]> skins = new HashMap<>();
		skins.putAll(ELF_SKINS);
		skins.putAll(HUMAN_SKINS);
		skins.putAll(RABBIT_SKINS);
		return skins;
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
