package net.fuchsia.common.objects.entity;

import net.fuchsia.common.objects.IHasSouls;
import net.fuchsia.util.FadenNBT;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class SoulsEntity extends Entity implements IHasSouls {

    public static final TrackedData<Integer> SOULS;

    static {
        SOULS = DataTracker.registerData(SoulsEntity.class, TrackedDataHandlerRegistry.INTEGER);
    }

    public SoulsEntity(EntityType<?> type, World world) {
        super(type, world);
    }

    @Override
    protected void initDataTracker() {
        this.getDataTracker().startTracking(SOULS, 0);
    }

    public void setSouls(int souls) {
        this.getDataTracker().set(SOULS, souls);
    }

    public int getSouls() {
        return this.getDataTracker().get(SOULS);
    }

    @Override
    protected void readCustomDataFromNbt(NbtCompound nbt) {
        if(nbt.contains(FadenNBT.Int.SOULS)) {
            setSouls(nbt.getInt(FadenNBT.Int.SOULS));
        }
    }

    @Override
    protected void writeCustomDataToNbt(NbtCompound nbt) {
        nbt.putInt(FadenNBT.Int.SOULS, getSouls());
    }

    @Override
    public ActionResult interact(PlayerEntity player, Hand hand) {
        IHasSouls playerSouls = (IHasSouls) player;
        playerSouls.setSouls(playerSouls.getSouls() + getSouls());
        return super.interact(player, hand);
    }
}
