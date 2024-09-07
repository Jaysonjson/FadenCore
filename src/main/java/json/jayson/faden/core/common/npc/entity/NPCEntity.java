package json.jayson.faden.core.common.npc.entity;

import com.mojang.serialization.Dynamic;
import json.jayson.faden.core.common.init.FadenCoreEntities;
import json.jayson.faden.core.common.npc.NPC;
import json.jayson.faden.core.registry.FadenCoreRegistry;
import net.minecraft.entity.ai.brain.Brain;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.PathAwareEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class NPCEntity extends PathAwareEntity {

    public static final TrackedData<String> NPC_DATA;
    /* Used for NPCUtil#Summon */
    private NPC npc;
    static {
        NPC_DATA = DataTracker.registerData(NPCEntity.class, TrackedDataHandlerRegistry.STRING);
    }

    public void setNpc(NPC npc) {
        this.npc = npc;
        npc.initGoals(this.goalSelector, this);
    }

    public NPCEntity(World world) {
        super(FadenCoreEntities.NPC, world);
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(NPC_DATA, "");
    }

    public NPC getNpc() {
        String npcData = getDataTracker().get(NPC_DATA);
        if (npcData.isBlank()) return null;
        checkNPCData(npcData);
        return npc;
    }

    @Override
    public boolean isInvisible() {
        return false;
    }

    @Override
    public boolean canTakeDamage() {
        return npc != null && npc.isInvulnerable();
    }

    @Override
    public boolean isInvulnerableTo(DamageSource damageSource) {
        return npc != null && npc.isInvulnerable();
    }

    @Override
    public boolean cannotDespawn() {
        return true;
    }

    public void checkNPCData(String npcData) {
        if (npc == null) {
            npc = FadenCoreRegistry.NPC.stream()
                    .filter(inpc -> inpc.getIdentifier().toString().equalsIgnoreCase(npcData))
                    .findFirst()
                    .orElse(null);
        }
    }

    @Override
    protected Brain<?> deserializeBrain(Dynamic<?> dynamic) {
        return npc != null ? npc.deserializeBrain(dynamic, this) : super.deserializeBrain(dynamic);
    }

    @Override
    protected Brain.Profile<?> createBrainProfile() {
        return npc != null ? npc.createBrainProfile(this) : super.createBrainProfile();
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
        String npcData = nbt.getString("npc");
        getDataTracker().set(NPC_DATA, npcData);
        checkNPCData(npcData);
        if(npc != null) {
            npc.initGoals(this.goalSelector, this);
            npc.initBrain(this);
        }
    }

    @Override
    public NbtCompound writeNbt(NbtCompound nbt) {
        if(npc != null) {
            nbt.putString("npc", npc.getIdentifier().toString());
        } else {
            nbt.putString("npc", getDataTracker().get(NPC_DATA));
        }
        return super.writeNbt(nbt);
    }

    @Override
    public ActionResult interactAt(PlayerEntity player, Vec3d hitPos, Hand hand) {
        return getNpc() != null ? getNpc().interaction(player, hitPos, hand) : ActionResult.PASS;
    }
}
