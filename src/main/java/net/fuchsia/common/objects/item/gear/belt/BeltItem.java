package net.fuchsia.common.objects.item.gear.belt;

import net.fuchsia.common.objects.item.ItemTier;
import net.fuchsia.common.objects.item.gear.FadenGearItem;
import net.fuchsia.common.slot.GearSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;

import java.util.Collection;

public class BeltItem extends FadenGearItem {
    public BeltItem(Settings settings) {
        super(settings);
    }

    @Override
    public GearSlot getGearType() {
        return null;
    }

    @Override
    public ItemStack randomize(ItemStack itemStack, PlayerEntity player, ItemTier itemTier) {
        return null;
    }

    @Override
    public ItemStack upgrade(Collection<ItemStack> inputs, ItemStack itemStack) {
        return null;
    }
}
