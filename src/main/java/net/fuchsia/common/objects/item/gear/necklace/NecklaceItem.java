package net.fuchsia.common.objects.item.gear.necklace;

import net.fuchsia.common.objects.item.FadenItem;
import net.fuchsia.common.objects.item.ItemTier;
import net.fuchsia.common.objects.item.gear.FadenGearItem;
import net.fuchsia.common.objects.item.gear.Gear;
import net.fuchsia.common.slot.GearSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import java.util.Collection;

public class NecklaceItem extends FadenGearItem {
    public NecklaceItem(Settings settings) {
        super(settings.maxCount(1));
    }

    @Override
    public GearSlot getGearType() {
        return GearSlot.NECKLACE;
    }

    @Override
    public ItemStack randomize(ItemStack itemStack, PlayerEntity player, ItemTier itemTier) {
        return itemStack;
    }

    @Override
    public ItemStack upgrade(Collection<ItemStack> inputs, ItemStack itemStack) {
        return itemStack;
    }
}
