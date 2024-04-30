package net.fuchsia.common.race.data;

import net.fabricmc.loader.api.FabricLoader;
import net.fuchsia.Faden;
import net.fuchsia.common.race.RaceSkinMap;
import net.fuchsia.network.FadenNetwork;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtSizeTracker;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.UUID;

public class ServerRaceCache {

    private static HashMap<UUID, RaceData> CACHE = new HashMap<>();

    public static HashMap<UUID, RaceData> getCache() {
        return CACHE;
    }

    public static class Cache {
        private static NbtCompound CACHE = new NbtCompound();
        private static final Path CACHE_PATH = new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/" + Faden.MC_VERSION + "/races.nbt").toPath();

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

        public static void add(UUID uuid, String id, String sub_id, String head_cosmetic) {
            new Thread(() -> {
                if(CACHE.contains(uuid.toString())) CACHE.remove(uuid.toString());
                NbtCompound tag = new NbtCompound();
                tag.putString("id", id);
                tag.putString("sub_id", sub_id);
                tag.putString("head_cosmetic", head_cosmetic);
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

        public static String getSubId(UUID uuid) {
            if(get().contains(uuid.toString())) {
                NbtCompound compound = get().getCompound(uuid.toString());
                return compound.getString("sub_id");
            }
            return "";
        }

        public static String getHeadCosmetic(UUID uuid) {
            if(get().contains(uuid.toString())) {
                NbtCompound compound = get().getCompound(uuid.toString());
                return compound.getString("head_cosmetic");
            }
            return "";
        }

        public static void sendUpdate(ServerPlayerEntity updatedPlayer, MinecraftServer server, boolean remove) {
            String id = ServerRaceCache.Cache.getId(updatedPlayer.getUuid());
            String sub_id = ServerRaceCache.Cache.getSubId(updatedPlayer.getUuid());
            String head_cosmetic = ServerRaceCache.Cache.getHeadCosmetic(updatedPlayer.getUuid());
            if(!id.isEmpty()) {
                for (ServerPlayerEntity playerEntity : server.getPlayerManager().getPlayerList()) {
                    FadenNetwork.Server.sendRace(playerEntity, updatedPlayer.getUuid(), id, sub_id, head_cosmetic, remove);
                }
                FadenNetwork.Server.sendRace(updatedPlayer, updatedPlayer.getUuid(), id, sub_id, head_cosmetic, remove);
            }
            FadenNetwork.Server.sendRaces(updatedPlayer);
        }
    }

}
