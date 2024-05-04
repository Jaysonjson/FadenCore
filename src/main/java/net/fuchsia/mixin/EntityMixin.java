package net.fuchsia.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fuchsia.mixin_interfaces.IGearInventory;
import net.fuchsia.common.objects.item.gear.Gear;
import net.fuchsia.common.race.data.ClientRaceCache;
import net.fuchsia.common.race.data.RaceData;
import net.fuchsia.common.race.data.ServerRaceCache;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.util.math.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public abstract float getStandingEyeHeight();
    @Shadow private float standingEyeHeight;
    @Shadow protected UUID uuid;

    @Shadow public abstract boolean isSubmergedInWater();

    @Shadow public abstract void setSwimming(boolean swimming);

    @Shadow protected abstract void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition);

    @Shadow public abstract boolean isTeamPlayer(AbstractTeam team);

    private Entity entity = ((Entity) ((Object) this));


    @Environment(EnvType.CLIENT)
    @Inject(at = @At("HEAD"), method = "getStandingEyeHeight", cancellable = true)
    private void eyeHeightClient(CallbackInfoReturnable<Float> cir) {
        if(entity instanceof PlayerEntity) {
            RaceData data = ClientRaceCache.get(uuid);
            if (data.getRace() != null && data.getRace().size().y != 1f) {
                cir.setReturnValue(this.standingEyeHeight - (1 - data.getRace().size().y * data.getRace().size().y - data.getRace().size().y / 10.0f));
            }
        }
    }

    @Environment(EnvType.SERVER)
    @Inject(at = @At("HEAD"), method = "getStandingEyeHeight", cancellable = true)
    private void eyeHeightServer(CallbackInfoReturnable<Float> cir) {
        if(entity instanceof PlayerEntity) {
            RaceData data = ServerRaceCache.getCache().get(uuid);
            if (data.getRace() != null && data.getRace().size().y != 1f) {
                cir.setReturnValue(this.standingEyeHeight - (1 - data.getRace().size().y * data.getRace().size().y - data.getRace().size().y / 10.0f));
            }
        }
    }


    @Inject(at = @At("HEAD"), method = "updateSwimming", cancellable = true)
    private void submergedInWater(CallbackInfo ci) {
        if(entity instanceof PlayerEntity player) {
            IGearInventory playerInventory = (IGearInventory) player.getInventory();
            ItemStack necklace = playerInventory.getNecklace();
            if(necklace.getItem() instanceof Gear gear) {
                if(gear.freeWaterMovement(player, necklace, player.isSubmergedInWater())) {
                    setSwimming(false);
                    ci.cancel();
                }
            }
        }
    }


    @Inject(at = @At("HEAD"), method = "updateMovementInFluid", cancellable = true)
    private void updateMovementInFluid(TagKey<Fluid> tag, double speed, CallbackInfoReturnable<Boolean> cir) {
        if(Fluids.WATER.isIn(tag) || Fluids.FLOWING_WATER.isIn(tag)) {
            if (entity instanceof PlayerEntity player) {
                IGearInventory playerInventory = (IGearInventory) player.getInventory();
                ItemStack necklace = playerInventory.getNecklace();
                if (necklace.getItem() instanceof Gear gear) {
                    if(gear.freeWaterMovement(player, necklace, player.isSubmergedInWater())) {
                        cir.cancel();
                    }
                }
            }
        }
    }


}
