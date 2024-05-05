package net.fuchsia.common.objects.item.gear.bracelet;

import net.fuchsia.common.objects.item.FadenItem;
import net.fuchsia.common.objects.item.ItemTier;
import net.fuchsia.common.objects.item.gear.Gear;
import net.fuchsia.common.slot.GearSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Collection;

public class BraceletItem extends FadenItem implements Gear {

    public BraceletItem(Settings settings) {
        super(settings.maxCount(1));
    }

    @Override
    public GearSlot getGearType() {
        return GearSlot.BRACELET;
    }

    @Override
    public ItemStack randomize(ItemStack itemStack, PlayerEntity player, ItemTier itemTier) {
        return null;
    }

    @Override
    public ItemStack upgrade(Collection<ItemStack> inputs, ItemStack itemStack) {
        return itemStack;
    }
}
