package net.fuchsia.common.objects.tooltip;

import java.util.LinkedHashMap;

import net.minecraft.client.item.TooltipData;
import net.minecraft.item.Item;

public class ItemValueTooltipData implements TooltipData {
    public LinkedHashMap<Item, Integer> values;

    public ItemValueTooltipData(LinkedHashMap<Item, Integer> value) {
        this.values = value;
    }
}
