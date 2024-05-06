package net.fuchsia.common.objects.item.gear.necklace;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fuchsia.Faden;
import net.fuchsia.common.init.FadenDataComponents;
import net.fuchsia.common.objects.item.FadenItem;
import net.fuchsia.common.objects.item.ItemTier;
import net.fuchsia.common.objects.item.ItemToolTipRenderer;
import net.fuchsia.common.objects.item.ItemValueToolTipRenderer;
import net.fuchsia.common.objects.tooltip.FadenTooltipComponent;
import net.fuchsia.common.objects.tooltip.FadenTooltipData;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.item.TooltipData;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.world.World;
import org.joml.Matrix4f;

import java.util.LinkedHashMap;
import java.util.Optional;

public class KingOFWaterNecklaceItem extends NecklaceItem {

    public KingOFWaterNecklaceItem(Settings settings) {
        super(settings.maxDamage(500));
    }

    @Override
    public boolean freeWaterMovement(PlayerEntity player, ItemStack itemStack, boolean inWater) {
        if(player != null && itemStack != null) {
            if (!player.getWorld().isClient && player.isSubmergedIn(FluidTags.WATER)) {
                if (Faden.RANDOM.nextInt(175) == 1) {
                    itemStack.damage(1, player, EquipmentSlot.CHEST);
                }
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
    public ItemStack randomize(ItemStack itemStack, PlayerEntity player, ItemTier itemTier) {
        itemStack.set(DataComponentTypes.MAX_DAMAGE, Faden.RANDOM.nextInt(50 + (int)(50.0f * itemTier.getDurabilityMultiplier()), (int)(500.0f * itemTier.getDurabilityMultiplier())));
        itemStack.set(FadenDataComponents.ITEM_TIER, itemTier.name());
        return itemStack;
    }
}
