package net.fuchsia.common.quest.data;

import net.fabricmc.loader.api.FabricLoader;
import net.fuchsia.Faden;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtSizeTracker;
import net.minecraft.util.Identifier;

import java.io.File;
import java.nio.file.Path;
import java.util.UUID;

public class QuestCache {

    private static NbtCompound CACHE = new NbtCompound();
    private static final Path CACHE_PATH = new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/" + Faden.MC_VERSION + "/quests.nbt").toPath();

    public static void load() {
        try {
            if(CACHE_PATH.toFile().exists()) {
                CACHE = NbtIo.readCompressed(CACHE_PATH, NbtSizeTracker.ofUnlimitedBytes());
            } else {
                save();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static NbtCompound get() {
        return CACHE;
    }

    public static boolean stepActive(UUID uuid, IQuest quest, IQuestStep step) {
        NbtCompound playerTag = get().getCompound(uuid.toString());
        if(playerTag.contains(quest.id().toString())) {
            NbtCompound questTag = playerTag.getCompound(quest.id().toString());
            if(questTag.contains("step")) {
                return questTag.getString("step").equalsIgnoreCase(step.id().toString());
            }
        }
        return false;
    }

    public static void finishQuestLine(UUID uuid, IQuest quest, IQuestStep step) {
        new Thread(() -> {
            NbtCompound playerTag = get().getCompound(uuid.toString());
            if(playerTag.contains(quest.id().toString())) {
                NbtCompound questTag = playerTag.getCompound(quest.id().toString());
                if(questTag.contains("step")) {
                    if(questTag.getString("step").equalsIgnoreCase(step.id().toString())) {
                        questTag.remove("step");
                    }
                }
                playerTag.put(quest.id().toString(), questTag);
            }

            CACHE.put(uuid.toString(), playerTag);

            new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/" + Faden.MC_VERSION + "/").mkdirs();
            try {
                NbtIo.writeCompressed(CACHE, CACHE_PATH);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();
    }

    public static void addOrUpdate(UUID uuid, IQuest quest, IQuestStep step) {
        new Thread(() -> {
            NbtCompound tag = new NbtCompound();
            NbtCompound questTag = new NbtCompound();
            questTag.putString("step", step.id().toString());
            tag.put(quest.id().toString(), questTag);
            CACHE.put(uuid.toString(), tag);
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


}
