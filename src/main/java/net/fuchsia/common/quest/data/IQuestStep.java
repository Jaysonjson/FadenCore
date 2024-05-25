package net.fuchsia.common.quest.data;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public interface IQuestStep {

    Identifier id();
    void rewardAndFinish(PlayerEntity player);

    @Nullable
    Identifier nextStep();
}
