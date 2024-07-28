package net.fuchsia.common.npc;

import net.fuchsia.common.init.FadenEntities;
import net.fuchsia.common.init.FadenNPCs;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.mob.ZombieEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class NPCEntity extends PathAwareEntity {

    private static final TrackedData<String> NPC_DATA;
    private INPC npc;
    static {
        NPC_DATA = DataTracker.registerData(NPCEntity.class, TrackedDataHandlerRegistry.STRING);
    }

    public NPCEntity(World world) {
        super(FadenEntities.NPC, world);
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new LookAtEntityGoal(this, PlayerEntity.class, 9.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
        super.initGoals();
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(NPC_DATA, FadenNPCs.TEST.getId().toString());
    }

    public INPC getNpc() {
        if(npc == null) {
            for (INPC inpc : FadenNPCs.getNPCS()) {
                if(inpc.getId().toString().equalsIgnoreCase(getDataTracker().get(NPC_DATA))) {
                    npc = inpc;
                    break;
                }
            }
        }
        return npc;
    }

    @Override
    public boolean isInvisible() {
        return true;
    }


    @Override
    public boolean canTakeDamage() {
        return false;
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return true;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        getDataTracker().set(NPC_DATA, nbt.getString("npc"));
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        nbt.putString("npc", getDataTracker().get(NPC_DATA));
        return super.writeNbt(nbt);
    }

    @Override
    public ActionResult interactAt(PlayerEntity player, Vec3d hitPos, Hand hand) {
        return getNpc() != null ? getNpc().interaction(player, hitPos, hand) : ActionResult.PASS;
    }
}
