package json.jayson.common.objects.item;

import json.jayson.common.objects.tooltip.ItemValueTooltipComponent;
import json.jayson.common.objects.tooltip.ItemValueTooltipData;
import net.minecraft.client.item.TooltipData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Optional;

public class CoinItem extends Item {
    public final int value;
    public CoinItem(Settings settings, int value) {
        super(settings);
        this.value = value;
    }


    @Override
    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        return Optional.of(new ItemValueTooltipData(ItemValueTooltipComponent.generateMap(value)));
    }
}
