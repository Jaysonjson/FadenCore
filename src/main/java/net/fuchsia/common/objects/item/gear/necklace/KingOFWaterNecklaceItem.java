package net.fuchsia.common.objects.item.gear.necklace;

import net.fuchsia.Faden;
import net.minecraft.client.item.TooltipType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.FluidTags;
import net.minecraft.text.Text;
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
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.faden.free_water_movement"));
    }
}
