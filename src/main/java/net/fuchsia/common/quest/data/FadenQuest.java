package net.fuchsia.common.quest.data;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

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
        return getSteps().get(0);
    }

    @Override
    public void renderRewardList(DrawContext context) {

    }

    public void checkAndRewardStep(PlayerEntity player, Identifier stepId) {
        IQuestStep step = null;
        for (IQuestStep iQuestStep : getSteps()) {
            System.out.println(iQuestStep.id().toString() + "_" + stepId.toString());
            if(iQuestStep.id().toString().equalsIgnoreCase(stepId.toString())) {
                step = iQuestStep;
                System.out.println("FOUND");
                break;
            }
        }
        System.out.println("STEP PRE CHECK");
        if(step != null) {
            System.out.println("STEP NOT NULL");
            if(QuestCache.stepActive(player.getUuid(), this, step)) {
                System.out.println("STEP ACTIVE");
                step.rewardAndFinish(player);
                for (IQuestStep iQuestStep : getSteps()) {
                    if(step.nextStep() == null) {
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
}
