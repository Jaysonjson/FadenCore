package net.fuchsia.common.objects.music_instance;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fuchsia.common.init.FadenSoundEvents;
import net.fuchsia.common.objects.item.instrument.InstrumentType;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.*;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;
import net.minecraft.world.World;
import org.joml.Vector3f;
import org.lwjgl.openal.AL10;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;


public class MusicInstance implements Serializable {

    protected Vector3f position = new Vector3f();
    protected ArrayList<UUID> players = new ArrayList<>();
    protected UUID uuid = UUID.randomUUID();
    protected HashMap<InstrumentType, String> soundEvents = new HashMap<>();
    protected ArrayList<InstrumentType> instruments = new ArrayList<>();
    protected String musicId = "";

    public HashMap<InstrumentType, String> getSoundEvents() {
        return soundEvents;
    }


    public String getMusicId() {
        return musicId;
    }

    public void setMusicId(String musicId) {
        this.musicId = musicId;
    }

    public UUID getUuid() {
        return uuid;
    }

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public ArrayList<UUID> getPlayers() {
        return players;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public ArrayList<InstrumentType> getInstruments() {
        return instruments;
    }
}
