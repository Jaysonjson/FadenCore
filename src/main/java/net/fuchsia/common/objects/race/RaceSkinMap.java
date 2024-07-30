package net.fuchsia.common.objects.race;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import net.fuchsia.common.init.FadenRaces;
import net.fuchsia.network.FadenNetwork;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

import net.fabricmc.loader.api.FabricLoader;
import net.fuchsia.Faden;
import net.fuchsia.common.objects.race.skin.provider.SkinProvider;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtSizeTracker;

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
				NbtCompound slot = new NbtCompound();
				NbtCompound tag = new NbtCompound();
				tag.putString("id", id);
				slot.put("slot_0", tag);
				CACHE.put(uuid.toString(), slot);
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
				System.out.println("Saving Race Skin Cache");
				NbtIo.writeCompressed(CACHE,  CACHE_PATH);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		public static String getId(UUID uuid) {
			if(get().contains(uuid.toString())) {
				NbtCompound compound = get().getCompound(uuid.toString());
				if(compound.contains("slot_0")) {
					return compound.getCompound("slot_0").getString("id");
				}
			}
			return "";
		}

		public static void sendUpdate(ServerPlayerEntity updatedPlayer, MinecraftServer server) {
			String id = RaceSkinMap.Cache.getId(updatedPlayer.getUuid());
			if(!id.isEmpty()) {
				for (ServerPlayerEntity playerEntity : server.getPlayerManager().getPlayerList()) {
					FadenNetwork.Server.sendRaceSkin(playerEntity, updatedPlayer.getUuid(), id);
				}
				FadenNetwork.Server.sendRaceSkin(updatedPlayer, updatedPlayer.getUuid(), id);
			}
		}
	}
	
}
