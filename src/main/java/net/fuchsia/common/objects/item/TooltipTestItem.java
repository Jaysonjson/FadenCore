package net.fuchsia.common.objects.item;

import net.fuchsia.common.objects.tooltip.FadenTooltipComponent;
import net.fuchsia.common.objects.tooltip.ToolTipEntry;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TooltipTestItem extends Item implements ItemToolTipEntryRenderer {
    public TooltipTestItem(Settings settings) {
        super(settings);
    }

    @Override
    public Collection<ToolTipEntry> getToolTipEntries(FadenTooltipComponent component) {
        ArrayList<ToolTipEntry> entries = new ArrayList<>();
        entries.add(new ToolTipEntry() {
            @Override
            public @Nullable Item getItem(FadenTooltipComponent component) {
                return Items.IRON_SWORD;
            }

            @Override
            public @Nullable Identifier getTexture(FadenTooltipComponent component) {
                return null;
            }

            @Override
            public Text getText(FadenTooltipComponent component) {
                return Text.literal("Test1");
            }
        });

        entries.add(new ToolTipEntry() {
            @Override
            public @Nullable Item getItem(FadenTooltipComponent component) {
                return Items.SHEARS;
            }

            @Override
            public @Nullable Identifier getTexture(FadenTooltipComponent component) {
                return null;
            }

            @Override
            public Text getText(FadenTooltipComponent component) {
                return Text.literal("Test2");
            }
        });


        return entries;
    }
}
