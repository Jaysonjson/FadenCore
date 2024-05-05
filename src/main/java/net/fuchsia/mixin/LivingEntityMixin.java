package net.fuchsia.mixin;

import net.fuchsia.common.init.FadenDataComponents;
import net.fuchsia.mixin_interfaces.IGearInventory;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Collection;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @Shadow public abstract Vec3d applyMovementInput(Vec3d movementInput, float slipperiness);

    @ModifyVariable(method = "damage", at = @At(value = "HEAD"), argsOnly = true)
    public float damage(float amount, DamageSource source) {
        if(source.getAttacker() instanceof PlayerEntity player) {
            IGearInventory gearInventory = (IGearInventory) player.getInventory();
            Collection<ItemStack> bracelets = gearInventory.getBracelets();
            for (ItemStack bracelet : bracelets) {
                if(bracelet.contains(FadenDataComponents.DAMAGE_INCREASE_VALUE)) {
                    amount += bracelet.get(FadenDataComponents.DAMAGE_INCREASE_VALUE);
                    return amount;
                } else if(bracelet.contains(FadenDataComponents.DAMAGE_INCREASE_PERCENTAGE)) {
                    amount = amount + (amount / 100.0f * bracelet.get(FadenDataComponents.DAMAGE_INCREASE_PERCENTAGE));
                    return amount;
                }
            }
        }
        return amount;
    }

}
