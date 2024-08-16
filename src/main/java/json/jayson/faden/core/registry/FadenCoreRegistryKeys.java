package json.jayson.faden.core.registry;

import json.jayson.faden.core.common.cape.FadenCoreCape;
import json.jayson.faden.core.common.npc.NPC;
import json.jayson.faden.core.common.objects.item.instrument.InstrumentType;
import json.jayson.faden.core.common.objects.music_instance.InstrumentedMusic;
import json.jayson.faden.core.common.quest.data.FadenCoreQuest;
import json.jayson.faden.core.common.race.Race;
import json.jayson.faden.core.util.FadenCoreIdentifier;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;

public class FadenCoreRegistryKeys {

    public static RegistryKey<Registry<Race>> RACE_KEY = RegistryKey.ofRegistry(FadenCoreIdentifier.create("race"));
    public static RegistryKey<Registry<NPC>> NPC_KEY = RegistryKey.ofRegistry(FadenCoreIdentifier.create("npc"));
    public static RegistryKey<Registry<InstrumentedMusic>> INSTRUMENTED_MUSIC_KEY = RegistryKey.ofRegistry(FadenCoreIdentifier.create("instrumented_music"));
    public static RegistryKey<Registry<FadenCoreQuest>> QUEST_KEY = RegistryKey.ofRegistry(FadenCoreIdentifier.create("quest"));
    public static RegistryKey<Registry<FadenCoreCape>> CAPE_KEY = RegistryKey.ofRegistry(FadenCoreIdentifier.create("cape"));
    public static RegistryKey<Registry<InstrumentType>> INSTRUMENT_KEY = RegistryKey.ofRegistry(FadenCoreIdentifier.create("instrument"));

}
