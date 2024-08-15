package json.jayson.faden.core.common.objects.item.gear.belt;

import json.jayson.faden.core.common.objects.item.ItemTier;
import json.jayson.faden.core.common.objects.item.gear.FadenGearItem;
import json.jayson.faden.core.common.slot.GearSlot;
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
