package net.fuchsia.common.objects.tooltip;

import net.minecraft.client.item.TooltipData;
import net.minecraft.item.ItemStack;

public class FadenTooltipData implements TooltipData {
    public ItemStack itemStack;
    public FadenTooltipData(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
}
