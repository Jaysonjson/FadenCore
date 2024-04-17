package net.fuchsia.common.race;

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
import net.fuchsia.common.race.skin.provider.SkinProvider;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtSizeTracker;

public class RaceSkinMap {

	public static void addSkins() {
		for (Race value : Race.values()) {
			loadSkin(value);
		}
	}

	private static void loadSkin(IRace race) {
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
				race.getSkinMap().put(id, SkinProvider.readSkin(inputStream));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public static String getSkinPath(IRace race, String modid) {
		return "assets/" + modid + "/textures/skins/" + race.getId().toLowerCase() + "/";
	}

	@Nullable
	public static byte[] getSkin(String name) {
		for (Race value : Race.values()) {
			if(value.getSkinMap().containsKey(name)) return value.getSkinMap().get(name);
		}
		return null;
	}

	public static HashMap<String, byte[]> getAllMaps() {
		HashMap<String, byte[]> skins = new HashMap<>();
		for (Race value : Race.values()) {
			skins.putAll(value.getSkinMap());
		}
		return skins;
	}

	/**
	 * Returns an empty string if no skin could be found
	 */
	public static String getRandomSkin(IRace race) {
		Random random = new Random();
		if(race.getSkinMap().isEmpty()) return "";
		return race.getSkinMap().keySet().toArray(new String[] {})[random.nextInt(race.getSkinMap().size())];
	}
	
	public static class Cache {
		private static NbtCompound CACHE = new NbtCompound();
		private static final Path CACHE_PATH = new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/" + Faden.MC_VERSION + "/skin.nbt").toPath();

		public static void load() {
			try {
				if(CACHE_PATH.toFile().exists()) {
					CACHE = NbtIo.readCompressed(CACHE_PATH, NbtSizeTracker.ofUnlimitedBytes());
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public static NbtCompound get() {
			return CACHE;
		}

		public static void add(UUID uuid, String id) {
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

		public static void save() {
			new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/" + Faden.MC_VERSION + "/").mkdirs();
			try {
				NbtIo.writeCompressed(CACHE,  CACHE_PATH);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public static String getId(UUID uuid) {
			if(get().contains(uuid.toString())) {
				NbtCompound compound = get().getCompound(uuid.toString());
				return compound.getString("id");
			}
			return "";
		}
	}
	
}
