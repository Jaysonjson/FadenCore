package json.jayson.faden.core.registry;

import json.jayson.faden.core.common.cape.FadenCoreCape;
import json.jayson.faden.core.common.chat.FadenCoreChatType;
import json.jayson.faden.core.common.npc.NPC;
import json.jayson.faden.core.common.cloth.FadenCoreCloth;
import json.jayson.faden.core.common.objects.item.instrument.InstrumentType;
import json.jayson.faden.core.common.objects.music_instance.InstrumentedMusic;
import json.jayson.faden.core.common.quest.data.FadenCoreQuest;
import json.jayson.faden.core.common.race.FadenCoreRace;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.fabricmc.fabric.api.event.registry.RegistryAttribute;
import net.minecraft.registry.Registry;
import net.minecraft.registry.SimpleRegistry;
import net.minecraft.util.Identifier;

public class FadenCoreRegistry {

    public static Registry<FadenCoreRace> RACE = FabricRegistryBuilder.createSimple(FadenCoreRegistryKeys.RACE_KEY)
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();

    public static Registry<NPC> NPC = FabricRegistryBuilder.createSimple(FadenCoreRegistryKeys.NPC_KEY)
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();

    public static SimpleRegistry<InstrumentedMusic> INSTRUMENTED_MUSIC = FabricRegistryBuilder.createSimple(FadenCoreRegistryKeys.INSTRUMENTED_MUSIC_KEY)
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();

    public static Registry<FadenCoreQuest> QUEST = FabricRegistryBuilder.createSimple(FadenCoreRegistryKeys.QUEST_KEY)
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();

    public static Registry<FadenCoreCape> CAPE = FabricRegistryBuilder.createSimple(FadenCoreRegistryKeys.CAPE_KEY)
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();

    public static SimpleRegistry<InstrumentType> INSTRUMENT = FabricRegistryBuilder.createSimple(FadenCoreRegistryKeys.INSTRUMENT_KEY)
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();

    public static SimpleRegistry<FadenCoreCloth> CLOTH = FabricRegistryBuilder.createSimple(FadenCoreRegistryKeys.CLOTH_KEY)
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();

    public static SimpleRegistry<FadenCoreChatType> CHAT = FabricRegistryBuilder.createSimple(FadenCoreRegistryKeys.CHAT_KEY)
            .attribute(RegistryAttribute.SYNCED)
            .buildAndRegister();

    public static FadenCoreRace getRace(Identifier id) {
        return RACE.get(id);
    }

    public static void init() {}

}
