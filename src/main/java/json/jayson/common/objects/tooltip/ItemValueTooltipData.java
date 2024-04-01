package json.jayson.common.objects.tooltip;

import net.minecraft.client.item.TooltipData;
import net.minecraft.item.Item;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

public class ItemValueTooltipData implements TooltipData {
    public LinkedHashMap<Item, Integer> values;

    public ItemValueTooltipData(LinkedHashMap<Item, Integer> value) {
        this.values = value;
    }
}
