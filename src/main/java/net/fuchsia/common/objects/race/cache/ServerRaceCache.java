package net.fuchsia.common.objects.race.cache;

import java.io.File;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.UUID;

import net.fabricmc.loader.api.FabricLoader;
import net.fuchsia.Faden;
import net.fuchsia.common.objects.race.Race;
import net.fuchsia.network.FadenNetwork;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtSizeTracker;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayerEntity;
import org.jetbrains.annotations.Nullable;

public class ServerRaceCache {

    private static HashMap<UUID, RaceData> CACHE = new HashMap<>();

    public static HashMap<UUID, RaceData> getCache() {
        return CACHE;
    }

    public static RaceData getData(UUID uuid) {
        return getCache().getOrDefault(uuid, new RaceData());
    }

    @Nullable
    public static Race get(UUID uuid) {
        RaceData data = getData(uuid);
        return data.getRace();
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

        public static void add(UUID uuid, String id, String sub_id, RaceData.RaceDataCosmetics cosmetics) {
            new Thread(() -> {
                if(CACHE.contains(uuid.toString())) CACHE.remove(uuid.toString());
                NbtCompound slot = new NbtCompound();
                NbtCompound tag = new NbtCompound();
                tag.putString("id", id);
                tag.putString("sub_id", sub_id);
                tag.putString("head_cosmetic", cosmetics.getHeadCosmeticId());
                tag.putString("chest_cosmetic", cosmetics.getChestCosmeticId());
                tag.putString("leg_cosmetic", cosmetics.getLegCosmeticId());
                tag.putString("boots_cosmetic", cosmetics.getBootsCosmeticId());
                slot.put("slot_0", tag);
                CACHE.put(uuid.toString(), slot);
                ServerRaceCache.getCache().put(uuid, new RaceData(id, sub_id, cosmetics));
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
                System.out.println("Saving Race Cache");
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

        public static String getSubId(UUID uuid) {
            if(get().contains(uuid.toString())) {
                NbtCompound compound = get().getCompound(uuid.toString());
                if(compound.contains("slot_0")) {
                    return compound.getCompound("slot_0").getString("sub_id");
                }
            }
            return "";
        }

        public static String getHeadCosmetic(UUID uuid) {
            if(get().contains(uuid.toString())) {
                NbtCompound compound = get().getCompound(uuid.toString());
                if(compound.contains("slot_0")) {
                    return compound.getCompound("slot_0").getString("head_cosmetic");
                }
            }
            return "";
        }

        public static String getChestCosmetic(UUID uuid) {
            if(get().contains(uuid.toString())) {
                NbtCompound compound = get().getCompound(uuid.toString());
                if(compound.contains("slot_0")) {
                    return compound.getCompound("slot_0").getString("chest_cosmetic");
                }
            }
            return "";
        }

        public static String getLegCosmetic(UUID uuid) {
            if(get().contains(uuid.toString())) {
                NbtCompound compound = get().getCompound(uuid.toString());
                if(compound.contains("slot_0")) {
                    return compound.getCompound("slot_0").getString("leg_cosmetic");
                }
            }
            return "";
        }

        public static String getBootsCosmetic(UUID uuid) {
            if(get().contains(uuid.toString())) {
                NbtCompound compound = get().getCompound(uuid.toString());
                if(compound.contains("slot_0")) {
                    return compound.getCompound("slot_0").getString("boots_cosmetic");
                }
            }
            return "";
        }

        public static void sendUpdate(ServerPlayerEntity updatedPlayer, MinecraftServer server, boolean remove) {
            String id = ServerRaceCache.Cache.getId(updatedPlayer.getUuid());
            String sub_id = ServerRaceCache.Cache.getSubId(updatedPlayer.getUuid());
            String head_cosmetic = ServerRaceCache.Cache.getHeadCosmetic(updatedPlayer.getUuid());
            String chest_cosmetic = ServerRaceCache.Cache.getChestCosmetic(updatedPlayer.getUuid());
            String leg_cosmetic = ServerRaceCache.Cache.getLegCosmetic(updatedPlayer.getUuid());
            String boots_cosmetic = ServerRaceCache.Cache.getBootsCosmetic(updatedPlayer.getUuid());
            if(!id.isEmpty()) {
                for (ServerPlayerEntity playerEntity : server.getPlayerManager().getPlayerList()) {
                    FadenNetwork.Server.sendRace(playerEntity, updatedPlayer.getUuid(), id, sub_id, new RaceData.RaceDataCosmetics(head_cosmetic, chest_cosmetic, leg_cosmetic, boots_cosmetic), remove);
                }
                FadenNetwork.Server.sendRace(updatedPlayer, updatedPlayer.getUuid(), id, sub_id, new RaceData.RaceDataCosmetics(head_cosmetic, chest_cosmetic, leg_cosmetic, boots_cosmetic), remove);
            }
            FadenNetwork.Server.sendRaces(updatedPlayer);
        }
    }

}
