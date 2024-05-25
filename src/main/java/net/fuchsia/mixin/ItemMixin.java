package net.fuchsia.mixin;


import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Optional;

import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.fuchsia.common.data.ItemValues;
import net.fuchsia.common.init.FadenDataComponents;
import net.fuchsia.common.objects.ItemWithValues;
import net.fuchsia.common.objects.item.ItemTier;
import net.fuchsia.common.objects.item.ItemToolTipEntryRenderer;
import net.fuchsia.common.objects.item.coin.IValue;
import net.fuchsia.common.objects.tooltip.FadenTooltipComponent;
import net.fuchsia.common.objects.tooltip.FadenTooltipData;
import net.fuchsia.common.objects.tooltip.ToolTipEntry;
import net.fuchsia.server.FadenData;
import net.minecraft.client.item.TooltipData;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.item.BundleItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;

@Mixin(Item.class)
public abstract class ItemMixin implements IValue, ItemWithValues, ItemToolTipEntryRenderer {

    @Unique
    public LinkedHashMap<Item, Integer> valueMap = null;

    @Inject(at = @At("HEAD"), method = "getTooltipData", cancellable = true)
    private void getTooltipData(ItemStack stack, CallbackInfoReturnable<Optional<TooltipData>> cir) {
        if (cir.getReturnValue() == null && !(stack.getItem() instanceof BundleItem)) {
            cir.setReturnValue(Optional.of(new FadenTooltipData(stack)));
        } else if (!cir.getReturnValue().isPresent()) {
            cir.setReturnValue(Optional.of(new FadenTooltipData(stack)));
        }
    }

	@Override
	public int getValue(ItemStack stack) {
		int initialValue = ItemValues.VALUES.getOrDefault(stack.getItem(), 0);
        //sstack.set(FadenDataComponents.EXTRA_VALUE, 35343);
        initialValue = (int) ((float)initialValue * ItemTier.valueOf(stack.getOrDefault(FadenDataComponents.ITEM_TIER, ItemTier.COMMON.name())).getSellValueMultiplier());
        initialValue += stack.getOrDefault(FadenDataComponents.EXTRA_VALUE, 0);
        if(stack.contains(DataComponentTypes.DAMAGE)) {
            initialValue = (int) ((float) stack.getOrDefault(DataComponentTypes.DAMAGE, 0) / (float) stack.getOrDefault(DataComponentTypes.MAX_DAMAGE, 1) * (float) initialValue);
        }
		//TODO: OTHER CALCULATIONS HERE
		return initialValue;
	}

    @Override
    public int getBuyValue(ItemStack stack) {
        return getValue(stack) * FadenData.BUY_MULTIPLIER;
    }

    @Override
    public LinkedHashMap<Item, Integer> getValues(ItemStack itemStack) {
        if(valueMap == null) {
            int value = getValue(itemStack);
            valueMap = FadenTooltipComponent.generateMap(value);
        }
        return valueMap;
    }

    @Override
    public Collection<ToolTipEntry> getToolTipEntries(FadenTooltipComponent component) {
        ArrayList<ToolTipEntry> entries = new ArrayList<>();
        for (Item item : getValues(component.data.itemStack).keySet()) {
            entries.add(new ToolTipEntry() {
                @Override
                public @Nullable Item getItem(FadenTooltipComponent component) {
                    return item;
                }

                @Override
                public Text getText(FadenTooltipComponent component) {
                    return Text.literal(String.valueOf(getValues(component.data.itemStack).get(item)));
                }
            });
        }
        return entries;
    }
}
