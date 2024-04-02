package json.jayson.common.objects.item;

import json.jayson.common.init.FadenItems;
import json.jayson.common.objects.tooltip.ItemValueTooltipComponent;
import json.jayson.common.objects.tooltip.ItemValueTooltipData;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.item.TooltipData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

public class CoinItem extends Item {
    public final int value;
    public CoinItem(Settings settings, int value) {
        super(settings);
        this.value = value;
    }


    @Override
    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        LinkedHashMap<Item, Integer> itemStacks = new LinkedHashMap<>();
        itemStacks.put(FadenItems.COPPER_COIN, value * stack.getCount());
        return Optional.of(new ItemValueTooltipData(itemStacks));
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        //tooltip.add(Text.literal(Text.translatable("lore.faden.coin_value").withColor(Colors.GRAY).getString().replaceAll("%v", String.valueOf(stack.getCount() * value))));
        if(context.isAdvanced()) {
            tooltip.add(Text.translatable("tooltip.faden.coin_value").withColor(Colors.GRAY));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }
}
