package net.fuchsia.common.objects.item;

import net.fuchsia.common.objects.item.coin.IValue;
import net.fuchsia.common.objects.tooltip.FadenTooltipComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.LinkedHashMap;

public class FadenItem extends Item implements ItemValueToolTipRenderer {

    public LinkedHashMap<Item, Integer> valueMap = null;

    public FadenItem(Settings settings) {
        super(settings);
    }

    @Override
    public LinkedHashMap<Item, Integer> getValues(ItemStack itemStack) {
        if (valueMap == null) {
            System.out.println("CHECK");
            if(itemStack.getItem() instanceof IValue iValue) {
                int value = iValue.getValue(itemStack);
                valueMap = FadenTooltipComponent.generateMap(value);
            }
        }
        return valueMap;
    }
}
