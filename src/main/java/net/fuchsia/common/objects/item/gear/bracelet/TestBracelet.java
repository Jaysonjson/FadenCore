package net.fuchsia.common.objects.item.gear.bracelet;

import net.fuchsia.Faden;
import net.fuchsia.common.data.ItemValues;
import net.fuchsia.common.init.FadenDataComponents;
import net.fuchsia.common.objects.item.ItemTier;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

public class TestBracelet extends BraceletItem {
    public TestBracelet(Settings settings) {
        super(settings);
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.setStackInHand(hand, randomize(user.getStackInHand(hand), user, ItemTier.MYTHIC));
        return super.use(world, user, hand);
    }

    @Override
    public ItemStack randomize(ItemStack itemStack, PlayerEntity player, ItemTier itemTier) {
        itemStack.set(DataComponentTypes.MAX_DAMAGE, Faden.RANDOM.nextInt(50 + (int)(50.0f * itemTier.getDurabilityMultiplier()), (int)(500.0f * itemTier.getDurabilityMultiplier())));
        itemStack.set(FadenDataComponents.ITEM_TIER, itemTier.name());
        itemStack.set(FadenDataComponents.DAMAGE_INCREASE_VALUE, Faden.RANDOM.nextFloat() * (itemTier.getSellValueMultiplier() * 3f));
        itemStack.set(FadenDataComponents.DAMAGE_INCREASE_PERCENTAGE, Faden.RANDOM.nextFloat() * (itemTier.getSellValueMultiplier() * 3f));
        itemStack.set(FadenDataComponents.JUMP_INCREASE_PERCENTAGE, Faden.RANDOM.nextFloat() * (itemTier.getSellValueMultiplier() * 3f));
        itemStack.set(FadenDataComponents.JUMP_INCREASE_VALUE, Faden.RANDOM.nextFloat() * (itemTier.getSellValueMultiplier() * 3f));
        itemStack.set(FadenDataComponents.FREE_WATER_MOVEMENT, true);
        return itemStack;
    }
}
