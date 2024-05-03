package net.fuchsia.common.objects.item.gear.necklace;

import net.fuchsia.Faden;
import net.fuchsia.common.objects.item.ItemTier;
import net.minecraft.client.item.TooltipType;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;

import java.util.List;

public class KingOFWaterNecklaceItem extends NecklaceItem {
    public KingOFWaterNecklaceItem(Settings settings) {
        super(settings.maxDamage(500));
    }

    @Override
    public boolean freeWaterMovement(PlayerEntity player, ItemStack itemStack, boolean inWater) {
        if(!player.getWorld().isClient && player.isSubmergedIn(FluidTags.WATER)) {
            if(Faden.RANDOM.nextInt(175) == 1) {
                itemStack.damage(1, player, EquipmentSlot.CHEST);
            }
        }
        return true;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        user.setStackInHand(hand, randomize(user.getStackInHand(hand), user, ItemTier.LEGENDARY));
        return super.use(world, user, hand);
    }

    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.faden.free_water_movement"));
    }

    @Override
    public ItemStack randomize(ItemStack itemStack, PlayerEntity player, ItemTier itemTier) {
        itemStack.set(DataComponentTypes.MAX_DAMAGE, Faden.RANDOM.nextInt(50 + (int)(50.0f * itemTier.getDurabilityMultiplier()), (int)(500.0ff * itemTier.getDurabilityMultiplier())));
        return itemStack;
    }
}
