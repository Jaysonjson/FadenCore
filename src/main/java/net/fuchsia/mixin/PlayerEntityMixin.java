package net.fuchsia.mixin;

import net.fuchsia.common.cape.FadenCapes;
import net.fuchsia.common.init.FadenDataAttachements;
import net.fuchsia.common.objects.IHasSouls;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.PlayerModelPart;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin extends LivingEntity implements IHasSouls {

    @Shadow public abstract int unlockRecipes(Collection<RecipeEntry<?>> recipes);

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


    @Inject(at = @At("HEAD"), method = "isPartVisible", cancellable = true)
    public void isPartVisible(PlayerModelPart modelPart, CallbackInfoReturnable<Boolean> cir) {
        if(FadenCapes.playerHasCape(uuid)) {
            cir.setReturnValue(true);
        }
    }

}
