package json.jayson.faden.core.common.quest.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import json.jayson.faden.core.registry.FadenCoreRegistry;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public abstract class FadenCoreQuest implements IQuest {

    public List<IQuestStep> steps = new ArrayList<>();

    public FadenCoreQuest() {
        setupQuestSteps();
    }

    public List<IQuestStep> getSteps() {
        return steps;
    }

    @Override
    public Identifier getIdentifier() {
        return FadenCoreRegistry.QUEST.getId(this);
    }

    @Override
    public int getMaxSteps() {
        return getSteps().size();
    }

    @Override
    public IQuestStep getCurrentStep(UUID player) {
        Identifier id = QuestCache.currentStep(player, this);
        if(id != null) {
            for (IQuestStep step : getSteps()) {
                if(step.id().toString().equalsIgnoreCase(id.toString())) {
                    return step;
                }
            }
        }
        return getSteps().get(0);
    }

    private IQuestStep getStep(Identifier stepId) {
        for (IQuestStep iQuestStep : getSteps()) {
            if (iQuestStep.id().toString().equalsIgnoreCase(stepId.toString())) {
                return iQuestStep;
            }
        }
        return null;
    }

    private void handleNextStep(PlayerEntity player, IQuestStep step) {
        if (step.nextStep() == null) {
            finishQuest(player);
            QuestCache.finishQuestLine(player.getUuid(), this, step);
        } else {
            IQuestStep nextStep = getStep(step.nextStep());
            if (nextStep != null) {
                QuestCache.addOrUpdate(player.getUuid(), this, nextStep);
            }
        }
    }

    public void checkAndRewardStep(PlayerEntity player, Identifier stepId) {
        IQuestStep step = getStep(stepId);
        if (step != null && QuestCache.stepActive(player.getUuid(), this, step)) {
            step.rewardAndFinish(player);
            handleNextStep(player, step);
        }
    }

    @Override
    public void startQuest(UUID player) {
        if(QuestCache.getPlayerCache().newQuestForPlayer(player, this)) {
            QuestCache.addOrUpdate(player, this, getSteps().get(0));
        }
    }

    public void startQuest(ServerPlayerEntity player) {
        startQuest(player.getUuid());
    }
}
