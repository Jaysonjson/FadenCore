package net.fuchsia.common.objects.music_instance;

import net.fuchsia.common.init.FadenSoundEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.AbstractSoundInstance;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.joml.Vector3f;

import java.util.ArrayList;
import java.util.UUID;

public class MusicInstance {

    private Vector3f position = new Vector3f();
    private ArrayList<UUID> players = new ArrayList<>();
    private UUID uuid = UUID.randomUUID();
    private transient AbstractSoundInstance sound;
    private ArrayList<Identifier> soundEvents = new ArrayList<>();

    public void tick() {
        if(!MinecraftClient.getInstance().getSoundManager().isPlaying(sound)) {
            MinecraftClient.getInstance().getSoundManager().play(sound, 5);
            //sound.volume = 5;
        }
    }

    public void startPlaying(World world) {
        for (Identifier soundEvent : soundEvents) {

        }
    }


    public ArrayList<Identifier> getSoundEvents() {
        return soundEvents;
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
}
