package net.fuchsia.common.objects.music_instance;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fuchsia.common.objects.item.instrument.InstrumentType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.*;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import org.lwjgl.openal.AL10;

import java.util.HashMap;

public class ClientMusicInstance {

    private MusicInstance instance;
    @Environment(EnvType.CLIENT)
    private transient HashMap<InstrumentType, SoundInstance> soundInstances = new HashMap<>();

    @Environment(EnvType.CLIENT)
    public void tick() {
        for (InstrumentType instrumentType : soundInstances.keySet()) {
            if(getInstance().instruments.contains(instrumentType)) {
                Channel.SourceManager source = MinecraftClient.getInstance().getSoundManager().soundSystem.sources.get(soundInstances.get(instrumentType));
                if (source != null) {
                    source.run(this::openVolume);
                }
            }
        }
    }

    @Environment(EnvType.CLIENT)
    public void openVolume(Source source) {
        AL10.alSourcef(source.pointer, 4106, 1f);
        AL10.alSourcef(source.pointer, AL10.AL_GAIN, 1f);
    }

    @Environment(EnvType.CLIENT)
    public void closeVolume(Source source) {
        AL10.alSourcef(source.pointer, 4106, 0f);
        AL10.alSourcef(source.pointer, AL10.AL_GAIN, 0f);
    }

    @Environment(EnvType.CLIENT)
    public void startPlaying() {
        for (InstrumentType instrumentType : getInstance().getSoundEvents().keySet()) {
            AbstractSoundInstance music = PositionedSoundInstance.ambient(Registries.SOUND_EVENT.get(Identifier.of(getInstance().soundEvents.get(instrumentType))), Random.create(), getInstance().position.x, getInstance().position.y, getInstance().position.z);
            if(soundInstances == null) soundInstances = new HashMap<>();
            soundInstances.put(instrumentType, music);
            MinecraftClient.getInstance().getSoundManager().play(music);
            Channel.SourceManager source = MinecraftClient.getInstance().getSoundManager().soundSystem.sources.get(music);
            if(source != null) {
                source.run(this::closeVolume);
            }
        }
    }

    public MusicInstance getInstance() {
        return instance;
    }

    public void setInstance(MusicInstance instance) {
        this.instance = instance;
    }
}
