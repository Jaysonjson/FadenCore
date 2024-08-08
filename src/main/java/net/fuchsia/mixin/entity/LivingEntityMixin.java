package net.fuchsia.mixin.entity;

import net.fuchsia.common.race.Race;
import net.fuchsia.util.PlayerDataUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.fuchsia.common.objects.item.gear.FadenGearItem;
import net.fuchsia.mixin_interfaces.IGearInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    private LivingEntity entity = ((LivingEntity) ((Object) this));

    @Shadow public abstract void remove(Entity.RemovalReason reason);

    @Shadow public abstract void endCombat();

    @ModifyVariable(method = "damage", at = @At(value = "HEAD"), argsOnly = true)
    public float damage(float amount, DamageSource source) {
        if(source.getAttacker() instanceof PlayerEntity player) {
            IGearInventory gearInventory = (IGearInventory) player.getInventory();
            float dmgIncrease = 0f;
            for (ItemStack gear : gearInventory.getGears()) {
                if(gear.getItem() instanceof FadenGearItem gearItem) {
                    dmgIncrease += gearItem.onLivingDamaged(player, entity, gear, amount + dmgIncrease);
                }
            }
            return amount + dmgIncrease;
        }
        return amount;
    }


    @ModifyVariable(method = "travel", at = @At(value = "STORE"), ordinal = 1)
    public float travel(float value) {
        if(entity instanceof PlayerEntity player) {
            Race race = PlayerDataUtil.getClientOrServer(player.getUuid()).getRaceSaveData().getRace();
            if(race != null) {
                if(race.waterMovementSpeed() != 0) {
                    if(player.isInFluid()) {
                        return race.waterMovementSpeed();
                    }
                }
            }
        }
        return value;
    }

    @ModifyVariable(method = "handleFallDamage", at = @At(value = "STORE"))
    public int fallDamage(int i, float fallDistance, float damageMultiplier, DamageSource damageSource) {
        if(entity instanceof PlayerEntity player) {
            IGearInventory gearInventory = (IGearInventory) player.getInventory();
            int dmg = i;
            for (ItemStack gear : gearInventory.getGears()) {
                if(gear.getItem() instanceof FadenGearItem gearItem) {
                    dmg = gearItem.onLivingFallDamage(player, fallDistance, entity, gear, dmg);
                }
            }
            return dmg;
        }
        return i;
    }



    @Inject(at = @At("HEAD"), method = "getJumpVelocity()F", cancellable = true)
    public void getJumpVelocity(CallbackInfoReturnable<Float> cir) {
        if(entity instanceof PlayerEntity player) {
            IGearInventory gearInventory = (IGearInventory) player.getInventory();
            float jumpIncrease = 0f;
            for (ItemStack gear : gearInventory.getGears()) {
                if(gear.getItem() instanceof FadenGearItem gearItem) {
                   jumpIncrease += gearItem.jumpVelocity(player, gear, 1.0f + jumpIncrease);
                }
            }
            if(jumpIncrease > 0) {
                cir.setReturnValue(1.0f + jumpIncrease);
            }
        }
    }

}
