package net.fuchsia.mixin.entity;

import net.fuchsia.common.cape.FadenCoreCapes;
import net.fuchsia.common.race.Race;
import net.fuchsia.server.PlayerData;
import net.fuchsia.server.client.ClientPlayerDatas;
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
        if (FadenCoreCapes.playerHasCape(uuid)) {
            cir.setReturnValue(true);
        }
    }

    @Inject(at = @At("HEAD"), method = "getBaseDimensions", cancellable = true)
    public void baseDimensions(EntityPose pose, CallbackInfoReturnable<EntityDimensions> cir) {
        PlayerData data = ClientPlayerDatas.getPlayerData(uuid);
        Race race = data.getRaceSaveData().getRace();
        if (race != null) {
            if(race.poseDimensions() != null) {
                cir.setReturnValue((EntityDimensions) race.poseDimensions().getOrDefault(pose, race.dimensions()));
            }
        }
    }

}
