package net.fuchsia.common.quest.data;

import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.UUID;

import org.apache.commons.io.FileUtils;

import net.fabricmc.loader.api.FabricLoader;
import net.fuchsia.Faden;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtIo;
import net.minecraft.nbt.NbtSizeTracker;
import net.minecraft.util.Identifier;

public class QuestCache {

    private static NbtCompound CACHE = new NbtCompound();
    private static PlayerQuests PLAYER_CACHE = new PlayerQuests();
    private static final Path CACHE_PATH = new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/" + Faden.MC_VERSION + "/quests.nbt").toPath();
    private static final Path PLAYER_CACHE_PATH = new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/" + Faden.MC_VERSION + "/quests_player.json").toPath();

    public static void load() {
        try {
            if(CACHE_PATH.toFile().exists()) {
                CACHE = NbtIo.readCompressed(CACHE_PATH, NbtSizeTracker.ofUnlimitedBytes());
            } else {
                save();
            }

            PLAYER_CACHE = Faden.GSON.fromJson(new FileReader(PLAYER_CACHE_PATH.toFile()), PlayerQuests.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static NbtCompound get() {
        return CACHE;
    }

    public static PlayerQuests getPlayerCache() {
        return PLAYER_CACHE;
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

    public static Identifier currentStep(UUID uuid, IQuest quest) {
        NbtCompound playerTag = get().getCompound(uuid.toString());
        if(playerTag.contains(quest.id().toString())) {
            NbtCompound questTag = playerTag.getCompound(quest.id().toString());
            if(questTag.contains("step")) {
                return new Identifier(questTag.getString("step"));
            }
        }
        return null;
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
            ArrayList<String> done = QuestCache.getPlayerCache().done.getOrDefault(uuid, new ArrayList<>());
            if(!done.contains(quest.id().toString())) {
                done.add(quest.id().toString());
            }
            QuestCache.getPlayerCache().done.put(uuid, done);

            ArrayList<String> onGoing = QuestCache.getPlayerCache().onGoing.getOrDefault(uuid, new ArrayList<>());
            Iterator<String> iterator = onGoing.iterator();
            while(iterator.hasNext()) {
                String str = iterator.next();
                if(str.equalsIgnoreCase(quest.id().toString())) {
                    iterator.remove();
                }
            }
            QuestCache.getPlayerCache().onGoing.put(uuid, onGoing);

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
            ArrayList<String> onGoing = QuestCache.getPlayerCache().onGoing.getOrDefault(uuid, new ArrayList<>());
            if (!onGoing.contains(quest.id().toString())) {
                onGoing.add(quest.id().toString());
            }
            QuestCache.getPlayerCache().onGoing.put(uuid, onGoing);
        }).start();
    }

    public static void save() {
        new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/" + Faden.MC_VERSION + "/").mkdirs();
        try {
            NbtIo.writeCompressed(CACHE,  CACHE_PATH);
            FileUtils.writeStringToFile(PLAYER_CACHE_PATH.toFile(), Faden.GSON.toJson(getPlayerCache()), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
