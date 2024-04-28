package net.fuchsia.mixin;


import java.util.Optional;

import net.fuchsia.common.init.FadenDataComponents;
import net.minecraft.component.DataComponentTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.fuchsia.common.objects.item.IValue;
import net.fuchsia.common.objects.tooltip.ItemValueTooltipComponent;
import net.fuchsia.common.objects.tooltip.ItemValueTooltipData;
import net.fuchsia.common.data.ItemValues;
import net.minecraft.client.item.TooltipData;
import net.minecraft.item.BundleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

@Mixin(Item.class)
public class ItemMixin implements IValue {


    @Inject(at = @At("HEAD"), method = "getTooltipData", cancellable = true)
    private void getTooltipData(ItemStack stack, CallbackInfoReturnable<Optional<TooltipData>> cir) {
        int value = getValue(stack);
        if(value != 0) {
            if (cir.getReturnValue() == null && !(stack.getItem() instanceof BundleItem)) {
                cir.setReturnValue(Optional.of(new ItemValueTooltipData(ItemValueTooltipComponent.generateMap(value))));
            } else if (!cir.getReturnValue().isPresent()) {
                cir.setReturnValue(Optional.of(new ItemValueTooltipData(ItemValueTooltipComponent.generateMap(value))));
            }
        }
    }

	@Override
	public int getValue(ItemStack stack) {
		int initialValue = ItemValues.VALUES.getOrDefault(stack.getItem(), 0);
        //sstack.set(FadenDataComponents.EXTRA_VALUE, 35343);
        initialValue += stack.getOrDefault(FadenDataComponents.EXTRA_VALUE, 0);
        if(stack.contains(DataComponentTypes.DAMAGE)) {
            initialValue = (int) ((float) stack.getOrDefault(DataComponentTypes.DAMAGE, 0) / (float) stack.getOrDefault(DataComponentTypes.MAX_DAMAGE, 1) * (float) initialValue);
        }
		//TODO: OTHER CALCULATIONS HERE
		return initialValue;
	}

    @Override
    public int getBuyValue(ItemStack stack) {
        return getValue(stack) * 9;
    }

}
