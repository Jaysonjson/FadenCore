package net.fuchsia.common.objects.tooltip;

import java.util.LinkedHashMap;

import net.minecraft.client.item.TooltipData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;


/*
 * TODO: REWRITE TO GENERIC TOOLTIPCOMPONENT TO USE ItemToolTipRenderer INTERFACE
 * */
public class FadenTooltipData implements TooltipData {
    public ItemStack itemStack;
    public FadenTooltipData(ItemStack itemStack) {
        this.itemStack = itemStack;
    }
}
