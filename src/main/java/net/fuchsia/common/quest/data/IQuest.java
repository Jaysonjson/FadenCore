package net.fuchsia.common.quest.data;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.UUID;

public interface IQuest {

    Identifier id();
    int getMaxSteps();

    void setupQuestSteps();
    List<IQuestStep> getSteps();
    IQuestStep getCurrentStep(UUID player);
    void startQuest(UUID player);

    void finishQuest(PlayerEntity player);
}
