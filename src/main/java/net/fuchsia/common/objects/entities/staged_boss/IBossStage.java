package net.fuchsia.common.objects.entities.staged_boss;

import net.minecraft.entity.damage.DamageSource;

public interface IBossStage {

    void initStage();

    default void tick() {}

    /* Keep the Tick persistent, even if the current stage is not this tage */
    default boolean keepTick() { return false; }

    default void hurt(DamageSource source) {}

    /* Keep the hurt persistent, even if the current stage is not this tage */
    default boolean keepHurt() { return false; }

}
