package net.fuchsia.common.init;

import net.fuchsia.util.FadenIdentifier;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public class FadenSoundEvents {

    public static final SoundEvent FADEN = registerSoundEvent("faden");

    private static SoundEvent registerSoundEvent(String name) {
        Identifier id = FadenIdentifier.create(name);
        return Registry.register(Registries.SOUND_EVENT, id, SoundEvent.of(id));
    }

    public static void register() {}

}
