package net.fuchsia.mixin;

import net.fuchsia.common.init.FadenDataAttachements;
import net.fuchsia.common.objects.IHasSouls;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements IHasSouls {

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Override
    public int getSouls() {
        return getAttached(FadenDataAttachements.SOULS).intValue();
    }

    @Override
    public void setSouls(int souls) {
        setAttached(FadenDataAttachements.SOULS, souls);
    }

}
