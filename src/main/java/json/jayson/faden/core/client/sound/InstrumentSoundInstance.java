package json.jayson.faden.core.client.sound;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.TickableSoundInstance;
import net.minecraft.entity.Entity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.random.Random;

@Environment(EnvType.CLIENT)
public class InstrumentSoundInstance extends PositionedSoundInstance implements TickableSoundInstance {
    private Entity followingEntity;

    public InstrumentSoundInstance(SoundEvent sound, SoundCategory category, float volume, float pitch, Random random, BlockPos pos) {
        super(sound, category, volume, pitch, random, pos);
    }

    public InstrumentSoundInstance(SoundEvent sound, SoundCategory category, float volume, float pitch, Random random, double x, double y, double z) {
        super(sound, category, volume, pitch, random, x, y, z);
    }

    public InstrumentSoundInstance(Identifier id, SoundCategory category, float volume, float pitch, Random random, boolean repeat, int repeatDelay, AttenuationType attenuationType, double x, double y, double z, boolean relative) {
        super(id, category, volume, pitch, random, repeat, repeatDelay, attenuationType, x, y, z, relative);
    }

    public Entity getFollowingEntity() {
        return followingEntity;
    }

    public void setFollowingEntity(Entity followingEntity) {
        this.followingEntity = followingEntity;
    }

    @Override
    public boolean isDone() {
        return false;
    }

    @Override
    public float getVolume() {
        return this.volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    @Override
    public void tick() {
        if(followingEntity != null) {
            x = followingEntity.getX();
            y = followingEntity.getY();
            z = followingEntity.getZ();
        }
    }
}
