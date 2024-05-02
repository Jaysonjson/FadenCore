package net.fuchsia.common.objects.item.gear.necklace;

import net.minecraft.client.item.TooltipType;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.world.World;

import java.util.List;

public class KingOFWaterNecklaceItem extends NecklaceItem {
    public KingOFWaterNecklaceItem(Settings settings) {
        super(settings);
    }

    @Override
    public boolean freeWaterMovement(ItemStack itemStack) {
        return true;
    }


    @Override
    public void appendTooltip(ItemStack stack, TooltipContext context, List<Text> tooltip, TooltipType type) {
        tooltip.add(Text.translatable("tooltip.faden.free_water_movement"));
    }
}
