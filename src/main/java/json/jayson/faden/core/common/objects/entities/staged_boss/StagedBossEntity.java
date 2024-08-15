package json.jayson.faden.core.common.objects.entities.staged_boss;

import net.minecraft.entity.EntityType;
import net.minecraft.entity.boss.ServerBossBar;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;

import java.util.HashMap;

public abstract class StagedBossEntity extends MobEntity implements IStaged {

    private int STAGE = 1;
    private int LAST_STAGE = 1;

    private HashMap<Integer, IBossStage> STAGES = new HashMap<>();
    private final ServerBossBar bossEvent;

    protected StagedBossEntity(EntityType<? extends MobEntity> entityType, World world, ServerBossBar serverBossInfo) {
        super(entityType, world);
        addDefaultStages();
        this.bossEvent = (ServerBossBar) serverBossInfo.setDarkenSky(true);
        LAST_STAGE = STAGE;
    }

    @Override
    public void onDamaged(DamageSource damageSource) {
        if(STAGES.containsKey(getCurrentStage())) {
            for (int i = 1; i < getMaxStages() + 1; i++) {
                if(!STAGES.containsKey(i)) continue;
                if((STAGES.get(i).keepHurt() && i < getLastStage()) || i == getCurrentStage()) {
                    STAGES.get(i).hurt(damageSource);
                }
            }
        }
        super.onDamaged(damageSource);
    }

    @Override
    public void tick() {
        super.tick();
        STAGE = getMaxStages() - (int) (this.getHealth() / this.getMaxHealth() * (float) getMaxStages());
        this.bossEvent.setPercent(this.getHealth() / this.getMaxHealth());
        if(STAGE != LAST_STAGE) {
            if(!getWorld().isClient) onStageChanged();
            initStage();
            LAST_STAGE = STAGE;
        }

        if(STAGES.containsKey(getCurrentStage())) {
            for (int i = 1; i < getMaxStages() + 1; i++) {
                if(!STAGES.containsKey(i)) continue;
                if((STAGES.get(i).keepTick() && i < getLastStage()) || i == getCurrentStage()) {
                    STAGES.get(i).tick();
                }
            }
        }
    }

    public void initStage() {
        if(STAGES.containsKey(LAST_STAGE + 1)) {
            STAGES.get(LAST_STAGE + 1).initStage();
        }
    }

    public int getCurrentStage() {
        return STAGE;
    }

    public int getLastStage() {
        return LAST_STAGE;
    }

    public ServerBossBar getBossBar() {
        return bossEvent;
    }

    public void replaceStage(int stage, IBossStage stageFunc) {
        STAGES.replace(stage, stageFunc);
    }

    public void addStage(int stage, IBossStage stageFunc) {
        STAGES.put(stage, stageFunc);
    }

    @Override
    public int getMaxStages() {
        return STAGES.size() + 1;
    }

    public void onStartedTrackingBy(ServerPlayerEntity player) {
        super.onStartedTrackingBy(player);
        bossEvent.addPlayer(player);
    }

    public void onStoppedTrackingBy(ServerPlayerEntity player) {
        super.onStoppedTrackingBy(player);
        bossEvent.removePlayer(player);
    }
}
