package json.jayson.faden.core.common.npc;

import json.jayson.faden.core.common.init.FadenCoreEntities;
import json.jayson.faden.core.registry.FadenCoreRegistry;
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
    private static String preNpc = "";
    private NPC npc;
    static {
        NPC_DATA = DataTracker.registerData(NPCEntity.class, TrackedDataHandlerRegistry.STRING);
    }

    public NPCEntity(World world) {
        super(FadenCoreEntities.NPC, world);
    }
    public NPCEntity(World world, String npc) {
        super(FadenCoreEntities.NPC, world);
        preNpc = npc;
    }

    @Override
    protected void initGoals() {
        this.goalSelector.add(1, new LookAtEntityGoal(this, PlayerEntity.class, 9.0F));
        this.goalSelector.add(8, new LookAroundGoal(this));
        super.initGoals();
    }

    @Override
    public void tick() {
        if(!preNpc.isBlank()) {
            getDataTracker().set(NPC_DATA, preNpc);
            preNpc = "";
        }
        super.tick();
    }

    @Override
    protected void initDataTracker(DataTracker.Builder builder) {
        super.initDataTracker(builder);
        builder.add(NPC_DATA, preNpc);
    }

    public NPC getNpc() {
        String npcData = getDataTracker().get(NPC_DATA);
        if (npcData.isBlank()) return null;
        if (npc == null) {
            npc = FadenCoreRegistry.NPC.stream()
                    .filter(inpc -> inpc.getIdentifier().toString().equalsIgnoreCase(npcData))
                    .findFirst()
                    .orElse(null);
        }
        return npc;
    }

    @Override
    public boolean isInvisible() {
        return false;
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
    public boolean cannotDespawn() {
        return true;
    }

    @Override
    public void readNbt(NbtCompound nbt) {
        super.readNbt(nbt);
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
