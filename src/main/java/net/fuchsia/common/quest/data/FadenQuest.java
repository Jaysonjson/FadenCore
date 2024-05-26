package net.fuchsia.common.quest.data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

public abstract class FadenQuest implements IQuest {

    public List<IQuestStep> steps = new ArrayList<>();

    public FadenQuest() {
        setupQuestSteps();
    }

    public List<IQuestStep> getSteps() {
        return steps;
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

    public void checkAndRewardStep(PlayerEntity player, Identifier stepId) {
        IQuestStep step = null;
        for (IQuestStep iQuestStep : getSteps()) {
            if(iQuestStep.id().toString().equalsIgnoreCase(stepId.toString())) {
                step = iQuestStep;
                break;
            }
        }
        if(step != null) {
            if(QuestCache.stepActive(player.getUuid(), this, step)) {
                step.rewardAndFinish(player);
                for (IQuestStep iQuestStep : getSteps()) {
                    if(step.nextStep() == null) {
                        finishQuest(player);
                        QuestCache.finishQuestLine(player.getUuid(), this, step);
                    } else {
                        if (iQuestStep.id().toString().equalsIgnoreCase(step.nextStep().toString())) {
                            QuestCache.addOrUpdate(player.getUuid(), this, iQuestStep);
                            break;
                        }
                    }
                }
            }
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
