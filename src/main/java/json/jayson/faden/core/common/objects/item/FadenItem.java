package json.jayson.faden.core.common.objects.item;

import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;

import json.jayson.faden.core.FadenCore;
import json.jayson.faden.core.common.objects.item.coin.IValue;
import json.jayson.faden.core.common.objects.tooltip.ItemToolTipEntryRenderer;

import json.jayson.faden.core.common.objects.ItemWithValues;
import json.jayson.faden.core.common.objects.tooltip.FadenTooltipComponent;
import json.jayson.faden.core.common.objects.tooltip.ToolTipEntry;
import net.minecraft.item.Item;
import net.minecraft.text.Text;

public class FadenItem extends Item implements ItemToolTipEntryRenderer {


    public FadenItem(Settings settings) {
        super(settings);
    }

    @Override
    public Collection<ToolTipEntry> getToolTipEntries(FadenTooltipComponent component) {
        ArrayList<ToolTipEntry> entries = new ArrayList<>();
        if(FadenCore.MODULES.itemValues) {
            if (component.data.itemStack.getItem() instanceof ItemWithValues itemWithValues) {
                LinkedHashMap<Item, Integer> values = itemWithValues.getValues(component.data.itemStack);
                for (Item item : values.keySet()) {
                    entries.add(ToolTipEntry.of(Text.literal(String.valueOf(values.get(item))), item));
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
