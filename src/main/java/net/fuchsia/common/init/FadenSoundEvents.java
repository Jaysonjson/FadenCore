package net.fuchsia.common.init;

import net.fuchsia.util.FadenIdentifier;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class FadenSoundEvents {

    public static final SoundEvent FADEN = registerSoundEvent("faden");
    public static final SoundEvent BURNING_MEMORY_PANFLUTE = registerSoundEvent("burning_memory_panflute");
    public static final SoundEvent BURNING_MEMORY_LUTE = registerSoundEvent("burning_memory_lute");
    public static final SoundEvent BURNING_MEMORY_HURTY = registerSoundEvent("burning_memory_hurty");
    public static final SoundEvent BURNING_MEMORY_DRUM = registerSoundEvent("burning_memory_drum");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = FadenIdentifier.create(name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void register() {}

}
