package json.jayson.faden.core.common.objects.music_instance;

import json.jayson.faden.core.common.init.FadenCoreMusicInstances;
import json.jayson.faden.core.common.objects.item.instrument.InstrumentType;
import json.jayson.faden.core.network.FadenCoreNetwork;
import json.jayson.faden.core.registry.FadenCoreRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import org.joml.Vector3f;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;


public class MusicInstance implements Serializable {

    protected Vector3f position = new Vector3f();
    protected ArrayList<UUID> players = new ArrayList<>();
    protected UUID uuid = UUID.randomUUID();
    protected int entityId = 0;
    protected HashMap<InstrumentType, String> soundEvents = new HashMap<>();
    protected HashMap<String, Integer> instruments = new HashMap<>();
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

    public HashMap<String, Integer> getInstruments() {
        return instruments;
    }

    public static MusicInstance createInstance(PlayerEntity user, InstrumentType firstInstrument) {
        UUID uuid = UUID.randomUUID();
        MusicInstance musicInstance = new MusicInstance();
        musicInstance.setUuid(uuid);
        musicInstance.setPosition(user.getPos().toVector3f());
        musicInstance.getInstruments().put(firstInstrument.getTypeId(), user.getId());
        InstrumentedMusic music = FadenCoreRegistry.INSTRUMENTED_MUSIC.get(new Random().nextInt(FadenCoreRegistry.INSTRUMENTED_MUSIC.size()));
        for (InstrumentType type : music.getInstrumentTypes().keySet()) musicInstance.getSoundEvents().put(type, music.getInstrumentTypes().get(type).getId().toString());
        musicInstance.setMusicId(music.getId());
        FadenCoreMusicInstances.getInstances().put(uuid, musicInstance);
        for (ServerPlayerEntity serverPlayerEntity : user.getServer().getPlayerManager().getPlayerList()) {
            FadenCoreNetwork.Server.sendMusicInstance(serverPlayerEntity, musicInstance);
        }
        return musicInstance;
    }

    public void addNewInstrument(InstrumentType instrumentType, PlayerEntity user) {
        if (!getInstruments().containsKey(instrumentType.getTypeId())) {
            getInstruments().put(instrumentType.getTypeId(), user.getId());
            for (ServerPlayerEntity serverPlayerEntity : user.getServer().getPlayerManager().getPlayerList()) {
                FadenCoreNetwork.Server.sendMusicInstance(serverPlayerEntity, this);
            }
        }
    }
}
