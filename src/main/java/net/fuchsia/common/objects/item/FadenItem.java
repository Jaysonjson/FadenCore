package net.fuchsia.common.objects.item;

import java.util.ArrayList;
import java.util.Collection;

import org.jetbrains.annotations.Nullable;

import net.fuchsia.common.objects.ItemWithValues;
import net.fuchsia.common.objects.tooltip.FadenTooltipComponent;
import net.fuchsia.common.objects.tooltip.ToolTipEntry;
import net.minecraft.item.Item;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class FadenItem extends Item implements ItemToolTipEntryRenderer {


    public FadenItem(Settings settings) {
        super(settings);
    }

    @Override
    public Collection<ToolTipEntry> getToolTipEntries(FadenTooltipComponent component) {
        ArrayList<ToolTipEntry> entries = new ArrayList<>();
        if(component.data.itemStack.getItem() instanceof ItemWithValues itemWithValues) {
            for (Item item : itemWithValues.getValues(component.data.itemStack).keySet()) {
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
                        return Text.literal(String.valueOf(itemWithValues.getValues(component.data.itemStack).get(item)));
                    }
                });
            }
        }
        return entries;
    }

}
