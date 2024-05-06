package net.fuchsia.common.objects.item.gear;

import net.fuchsia.common.init.FadenDataComponents;
import net.fuchsia.common.objects.item.FadenItem;
import net.fuchsia.common.objects.item.ItemTier;
import net.fuchsia.common.objects.item.ItemToolTipEntryRenderer;
import net.fuchsia.common.objects.tooltip.FadenTooltipComponent;
import net.fuchsia.common.objects.tooltip.FadenTooltipData;
import net.fuchsia.common.objects.tooltip.ToolTipEntry;
import net.minecraft.client.item.TooltipData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

public abstract class FadenGearItem extends FadenItem implements Gear, ItemToolTipEntryRenderer {
    public FadenGearItem(Settings settings) {
        super(settings.maxCount(1));
    }


    @Override
    public Collection<ToolTipEntry> getToolTipEntries(FadenTooltipComponent component) {
        ArrayList<ToolTipEntry> entries = new ArrayList<>();
        ItemStack itemStack = component.data.itemStack;

        if(itemStack.contains(FadenDataComponents.ITEM_TIER)) {
            ItemTier itemTier = ItemTier.valueOf(itemStack.get(FadenDataComponents.ITEM_TIER));
            entries.add(new ToolTipEntry() {
                @Override
                public @Nullable Item getItem(FadenTooltipComponent component) {
                    return null;
                }

                @Override
                public @Nullable Identifier getTexture(FadenTooltipComponent component) {
                    return itemTier.getIcon();
                }

                @Override
                public Text getText(FadenTooltipComponent component) {
                    return Text.literal(itemTier.name());
                }

                @Override
                public int getTextColor(FadenTooltipComponent component) {
                    return itemTier.getColor();
                }
            });
        }

        //ADD COINS
        entries.addAll(super.getToolTipEntries(component));

        if(freeWaterMovement(null, null, false)) {
            entries.add(new ToolTipEntry() {
                @Override
                public @Nullable Item getItem(FadenTooltipComponent component) {
                    return Items.WATER_BUCKET;
                }

                @Override
                public @Nullable Identifier getTexture(FadenTooltipComponent component) {
                    return null;
                }

                @Override
                public Text getText(FadenTooltipComponent component) {
                    return Text.translatable("tooltip.faden.free_water_movement");
                }
            });
        }

        if(itemStack.contains(FadenDataComponents.DAMAGE_INCREASE_VALUE)) {
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
                    return Text.literal(Text.translatable("tooltip.faden.damage_increase_value").getString().replaceAll("%s", String.valueOf(component.data.itemStack.getOrDefault(FadenDataComponents.DAMAGE_INCREASE_VALUE, 0f))));
                }
            });
        }
        return entries;
    }


    @Override
    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        return Optional.of(new FadenTooltipData(stack));
    }
}
