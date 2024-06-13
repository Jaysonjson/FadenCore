package net.fuchsia.common.objects.tooltip;

import net.minecraft.item.ItemStack;
import net.minecraft.item.tooltip.TooltipData;

public class FadenTooltipData implements TooltipData {
    public ItemStack itemStack;
    public FadenTooltipData(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
}
