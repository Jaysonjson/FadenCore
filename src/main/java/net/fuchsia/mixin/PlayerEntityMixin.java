package net.fuchsia.mixin;

import net.fuchsia.common.cape.FadenCapes;
import net.fuchsia.common.race.data.ClientRaceCache;
import net.fuchsia.common.race.data.RaceData;
import net.fuchsia.common.race.data.ServerRaceCache;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerModelPart;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity {

    @Shadow public abstract int unlockRecipes(Collection<RecipeEntry<?>> recipes);

    protected PlayerEntityMixin(EntityType<? extends LivingEntity> entityType, World world) {
        super(entityType, world);
    }

    @Inject(at = @At("HEAD"), method = "isPartVisible", cancellable = true)
    public void isPartVisible(PlayerModelPart modelPart, CallbackInfoReturnable<Boolean> cir) {
        if (FadenCapes.playerHasCape(uuid)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(at = @At("HEAD"), method = "getBaseDimensions", cancellable = true)
    public void baseDimensions(EntityPose pose, CallbackInfoReturnable<EntityDimensions> cir) {
        RaceData data = ServerRaceCache.getCache().get(uuid);
        if(data == null) {
            data = ClientRaceCache.get(uuid);
        }
        if (data != null && data.getRace() != null) {
            if(data.getRace().poseDimensions() != null) {
                cir.setReturnValue((EntityDimensions) data.getRace().poseDimensions().getOrDefault(pose, data.getRace().dimensions()));
            }
        }
    }

}
