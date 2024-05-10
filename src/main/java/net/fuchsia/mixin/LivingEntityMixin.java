package net.fuchsia.mixin;

import net.fuchsia.common.objects.item.gear.FadenGearItem;
import net.fuchsia.mixin_interfaces.IGearInventory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow public abstract Vec3d applyMovementInput(Vec3d movementInput, float slipperiness);

    @Shadow protected abstract void initDataTracker(DataTracker.Builder builder);

    @ModifyVariable(method = "damage", at = @At(value = "HEAD"), argsOnly = true)
    public float damage(float amount, DamageSource source) {
        if(source.getAttacker() instanceof PlayerEntity player) {
            IGearInventory gearInventory = (IGearInventory) player.getInventory();
            for (ItemStack gear : gearInventory.getGears()) {
                if(gear.getItem() instanceof FadenGearItem gearItem) {
                    return gearItem.onLivingDamaged(player, (LivingEntity) (Object)this, gear, amount);
                }
            }
        }
        return amount;
    }

}
