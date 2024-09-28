package json.jayson.faden.core.mixin.entity;

import java.util.UUID;

import json.jayson.faden.core.common.race.FadenCoreRace;
import json.jayson.faden.core.util.PlayerDataUtil;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import json.jayson.faden.core.FadenCore;
import json.jayson.faden.core.common.init.FadenCoreDataComponents;
import json.jayson.faden.core.mixin_interfaces.IGearInventory;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.util.math.BlockPos;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Shadow public abstract float getStandingEyeHeight();
    @Shadow private float standingEyeHeight;
    @Shadow protected UUID uuid;

    @Shadow public abstract boolean isSubmergedInWater();

    @Shadow public abstract void setSwimming(boolean swimming);

    @Shadow protected abstract void fall(double heightDifference, boolean onGround, BlockState state, BlockPos landedPosition);

    @Shadow public abstract boolean isTeamPlayer(AbstractTeam team);

    @Shadow public abstract int getAir();

    @Shadow private EntityDimensions dimensions;
    private Entity entity = ((Entity) ((Object) this));


    /*@Environment(EnvType.CLIENT)
    @Inject(at = @At("HEAD"), method = "getStandingEyeHeight", cancellable = true)
    private void eyeHeightClient(CallbackInfoReturnable<Float> cir) {
        if(entity instanceof PlayerEntity) {
            PlayerData data = ClientPlayerDatas.getPlayerData(uuid);
            if (data.getRaceSaveData().hasRace() && data.getRaceSaveData().getRace().size().y != 1f) {
                cir.setReturnValue(this.standingEyeHeight - (1 - data.getRaceSaveData().getRace().size().y * data.getRaceSaveData().getRace().size().y - data.getRaceSaveData().getRace().size().y / 10.0f));
            }
        }
    }

    @Environment(EnvType.SERVER)
    @Inject(at = @At("HEAD"), method = "getStandingEyeHeight", cancellable = true)
    private void eyeHeightServer(CallbackInfoReturnable<Float> cir) {
        if(entity instanceof PlayerEntity) {
            PlayerData data = ClientPlayerDatas.getPlayerData(uuid);
            if (data.getRaceSaveData().hasRace() && data.getRaceSaveData().getRace().size().y != 1f) {
                cir.setReturnValue(this.standingEyeHeight - (1 - data.getRaceSaveData().getRace().size().y * data.getRaceSaveData().getRace().size().y - data.getRaceSaveData().getRace().size().y / 10.0f));
            }
        }
    }*/


    @Inject(at = @At("HEAD"), method = "updateSwimming", cancellable = true)
    private void submergedInWater(CallbackInfo ci) {
        if(entity instanceof PlayerEntity player) {
            IGearInventory playerInventory = (IGearInventory) player.getInventory();
            for (ItemStack gear : playerInventory.getGears()) {
                if(gear.getOrDefault(FadenCoreDataComponents.FREE_WATER_MOVEMENT, false)) {
                    if (!player.getWorld().isClient && player.isSubmergedIn(FluidTags.WATER)) {
                        if (FadenCore.RANDOM.nextInt(175) == 1) {
                            gear.damage(1, player, EquipmentSlot.CHEST);
                        }
                    }
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
                for (ItemStack gear : playerInventory.getGears()) {
                    if(gear.getOrDefault(FadenCoreDataComponents.FREE_WATER_MOVEMENT, false)) {
                        if (!player.getWorld().isClient && player.isSubmergedIn(FluidTags.WATER)) {
                            if (FadenCore.RANDOM.nextInt(175) == 1) {
                                gear.damage(1, player, EquipmentSlot.CHEST);
                            }
                        }
                        cir.cancel();
                    }
                }
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "calculateBoundingBox", cancellable = true)
    private void reinitDimensions(CallbackInfoReturnable<Box> cir) {
        if(entity instanceof PlayerEntity player) {
            FadenCoreRace fadenCoreRace = PlayerDataUtil.getClientOrServer(player.getUuid()).getRaceSaveData().getRace();
            if(fadenCoreRace != null) {
                cir.setReturnValue(fadenCoreRace.getDimensions().getBoxAt(player.getPos()));
            }
        }
    }


}
