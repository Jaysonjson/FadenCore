package json.jayson.faden.core.common.objects.music_instance;

import json.jayson.faden.core.client.sound.InstrumentSoundInstance;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import json.jayson.faden.core.common.objects.item.instrument.InstrumentType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.*;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundCategory;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import org.lwjgl.openal.AL10;

import java.util.HashMap;

public class ClientMusicInstance {

    private MusicInstance instance;
    @Environment(EnvType.CLIENT)
    private transient HashMap<InstrumentType, InstrumentSoundInstance> soundInstances = new HashMap<>();
    public boolean playing = false;

    @Environment(EnvType.CLIENT)
    public void tick() {
        for (InstrumentType instrumentType : soundInstances.keySet()) {
            InstrumentSoundInstance music = soundInstances.get(instrumentType);
            if(getInstance().instruments.containsKey(instrumentType.getTypeId())) {
                music.setVolume(1);
                Channel.SourceManager source = MinecraftClient.getInstance().getSoundManager().soundSystem.sources.get(music);
                if (source != null) {
                    source.run(this::openVolume);
                    if(music.getFollowingEntity() == null) music.setFollowingEntity(MinecraftClient.getInstance().player.getWorld().getEntityById(getInstance().getInstruments().get(instrumentType.getTypeId())));
                }
            } else {
                music.setVolume(0);
                Channel.SourceManager source = MinecraftClient.getInstance().getSoundManager().soundSystem.sources.get(music);
                if (source != null) {
                    source.run(this::closeVolume);
                }
            }
        }
    }

    public HashMap<InstrumentType, InstrumentSoundInstance> getSoundInstances() {
        return soundInstances;
    }

    @Environment(EnvType.CLIENT)
    public void openVolume(Source source) {
        AL10.alSourcef(source.pointer, 4106, 1f);
        AL10.alSourcef(source.pointer, AL10.AL_GAIN, 1f);
        AL10.alSourcef(source.pointer, AL10.AL_MAX_DISTANCE, 15f);
        source.setVolume(1);
    }

    @Environment(EnvType.CLIENT)
    public void closeVolume(Source source) {
        AL10.alSourcef(source.pointer, 4106, 0f);
        AL10.alSourcef(source.pointer, AL10.AL_GAIN, 0f);
        AL10.alSourcef(source.pointer, AL10.AL_MAX_DISTANCE, 15f);
        source.setVolume(0);
    }

    @Environment(EnvType.CLIENT)
    public void startPlaying() {
        playing = true;
        for (InstrumentType instrumentType : getInstance().getSoundEvents().keySet()) {
            InstrumentSoundInstance music = new InstrumentSoundInstance(Registries.SOUND_EVENT.get(Identifier.of(getInstance().soundEvents.get(instrumentType))), SoundCategory.RECORDS, 1, 1, Random.create(), getInstance().position.x, getInstance().position.y, getInstance().position.z);
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
