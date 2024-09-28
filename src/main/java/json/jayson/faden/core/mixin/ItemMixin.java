package json.jayson.faden.core.mixin;


import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Optional;

import json.jayson.faden.core.FadenCore;
import net.minecraft.item.tooltip.TooltipData;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import json.jayson.faden.core.common.data.ItemValues;
import json.jayson.faden.core.common.init.FadenCoreDataComponents;
import json.jayson.faden.core.common.objects.ItemWithValues;
import json.jayson.faden.core.common.objects.item.ItemTier;
import json.jayson.faden.core.common.objects.tooltip.ItemToolTipEntryRenderer;
import json.jayson.faden.core.common.objects.item.coin.IValue;
import json.jayson.faden.core.common.objects.tooltip.FadenTooltipComponent;
import json.jayson.faden.core.common.objects.tooltip.FadenTooltipData;
import json.jayson.faden.core.common.objects.tooltip.ToolTipEntry;
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
        //sstack.set(FadenCoreDataComponents.EXTRA_VALUE, 35343);
        initialValue = (int) ((float)initialValue * ItemTier.valueOf(stack.getOrDefault(FadenCoreDataComponents.ITEM_TIER, ItemTier.COMMON.name())).getSellValueMultiplier());
        initialValue += stack.getOrDefault(FadenCoreDataComponents.EXTRA_VALUE, 0);
        if(stack.contains(DataComponentTypes.DAMAGE)) {
            initialValue = (int) ((float) stack.getOrDefault(DataComponentTypes.DAMAGE, 0) / (float) stack.getOrDefault(DataComponentTypes.MAX_DAMAGE, 1) * (float) initialValue);
        }
		//TODO: OTHER CALCULATIONS HERE
		return initialValue;
	}

    @Override
    public int getBuyValue(ItemStack stack) {
        return getValue(stack) * FadenCore.VALUE_DATA.buyMultiplier;
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
    public void resetItemMap() {
        valueMap = null;
    }

    @Override
    public Collection<ToolTipEntry> getToolTipEntries(FadenTooltipComponent component) {
        ArrayList<ToolTipEntry> entries = new ArrayList<>();
        if(FadenCore.MODULES.itemValues) {
            if (component.data.itemStack.getItem() instanceof ItemWithValues itemWithValues) {
                LinkedHashMap<Item, Integer> values = itemWithValues.getValues(component.data.itemStack);
                for (Item item : values.keySet()) {
                    entries.add(new ToolTipEntry() {
                        @Override
                        public @Nullable Item getItem(FadenTooltipComponent component) {
                            return item;
                        }

                        @Override
                        public @Nullable Identifier getTexture(FadenTooltipComponent component) {
                            return null;
                        }

                        @Override
                        public Text getText(FadenTooltipComponent component) {
                            return Text.literal(String.valueOf(values.get(item)));
                        }
                    });
                }
                if (component.data.itemStack.getItem() instanceof IValue value) {
                    int val = value.getValue(component.data.itemStack);
                    if (val > 4) {
                        entries.add(component1 -> Text.literal("Total: " + val));
                    }
                }
            }
        }
        return entries;
    }
}
