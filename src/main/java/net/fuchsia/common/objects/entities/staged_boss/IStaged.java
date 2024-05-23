package net.fuchsia.common.objects.entities.staged_boss;

public interface IStaged {

    int getMaxStages();

    void addDefaultStages();

    void onStageChanged();


}
