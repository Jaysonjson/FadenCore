package net.fuchsia.mixin;

import net.fuchsia.common.objects.item.gear.FadenGearItem;
import net.fuchsia.common.race.data.ClientRaceCache;
import net.fuchsia.common.race.data.RaceData;
import net.fuchsia.common.race.data.ServerRaceCache;
import net.fuchsia.mixin_interfaces.IGearInventory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    private LivingEntity entity = ((LivingEntity) ((Object) this));

    @Shadow public abstract void remove(Entity.RemovalReason reason);

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

    @ModifyVariable(method = "handleFallDamage", at = @At(value = "STORE"))
    public int fallDamage(int i, float fallDistance, float damageMultiplier, DamageSource damageSource) {
        if(entity instanceof PlayerEntity player) {
            IGearInventory gearInventory = (IGearInventory) player.getInventory();
            int dmg = i;
            for (ItemStack gear : gearInventory.getGears()) {
                if(gear.getItem() instanceof FadenGearItem gearItem) {
                    dmg = gearItem.onLivingFallDamage(player, entity, gear, dmg);
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
