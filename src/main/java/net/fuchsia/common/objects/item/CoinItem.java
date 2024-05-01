package net.fuchsia.common.objects.item;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;

import net.fuchsia.common.quest.FadenQuests;
import net.fuchsia.common.quest.TestQuest;
import net.fuchsia.common.quest.data.QuestCache;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import org.jetbrains.annotations.Nullable;

import net.fuchsia.common.init.FadenItems;
import net.fuchsia.common.objects.tooltip.ItemValueTooltipData;
import net.minecraft.client.item.TooltipData;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Colors;
import net.minecraft.world.World;

public class CoinItem extends Item {
    private final int value;
    public CoinItem(Settings settings, int value) {
        super(settings);
        this.value = value;
    }

    public int getValue() {
        return this.value;
    }

    @Override
    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        LinkedHashMap<Item, Integer> itemStacks = new LinkedHashMap<>();
        itemStacks.put(FadenItems.COPPER_COIN, value * stack.getCount());
        return Optional.of(new ItemValueTooltipData(itemStacks));
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        if(!world.isClient) {
            //QUESTS SHOULD BE IN A STATIC CONTEXT LATER, JUST FOR TESTING
            FadenQuests.TEST.checkAndRewardStep(user, FadenIdentifier.create("use_coin"));
        }
        return super.use(world, user, hand);
    }


    /*@Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        tooltip.add(Text.literal(Text.translatable("lore.faden.coin_value").withColor(Colors.GRAY).getString().replaceAll("%v", String.valueOf(stack.getCount() * value))));
        if(context.isAdvanced()) {
            tooltip.add(Text.translatable("tooltip.faden.coin_value").withColor(Colors.GRAY));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }*/
}
