package json.jayson.common.objects.item;

import json.jayson.common.init.FadenItems;
import json.jayson.common.objects.CoinMap;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.NbtElement;
import net.minecraft.nbt.NbtList;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.ClickType;

import java.util.Optional;

public class PouchItem extends Item {
    private static final String COINS_KEY = "Coins";
    public final int capacity;
    public PouchItem(Settings settings, int capacity) {
        super(settings);
        this.capacity = capacity;
    }

    public boolean onStackClicked(ItemStack stack, Slot slot, ClickType clickType, PlayerEntity player) {
        if (clickType != ClickType.RIGHT) {
            return false;
        } else {
            ItemStack itemStack = slot.getStack();
            if (itemStack.getItem() instanceof CoinItem && !itemStack.hasNbt()) {
                addCoinStack(stack, itemStack);
                slot.setStack(Items.AIR.getDefaultStack());
            }
            return true;
        }
    }

    public static void addCoinStack(ItemStack pouch, ItemStack coinStack){
        if (!coinStack.isEmpty() && coinStack.getItem() instanceof CoinItem) {
            NbtCompound nbtCompound = pouch.getOrCreateNbt();
            if (!nbtCompound.contains(COINS_KEY)) {
                nbtCompound.put(COINS_KEY, new NbtList());
            }

            NbtList coinList = nbtCompound.getList(COINS_KEY, 10);
            boolean coinAdded = false;

            for(int i = 0; i < coinList.size(); ++i){
                NbtCompound coinCompound = coinList.getCompound(i);
                ItemStack compoundStack = ItemStack.fromNbt(coinCompound);
                if(compoundStack.getItem() == coinStack.getItem()){
                    compoundStack.setCount(compoundStack.getCount()+coinStack.getCount());
                    compoundStack.writeNbt(coinCompound);
                    coinAdded = true;
                    break;
                }
            }

            if(!coinAdded){
                NbtCompound newCoinCompound = new NbtCompound();
                coinStack.writeNbt(newCoinCompound);
                coinList.add(newCoinCompound)   ;
            }
        }
    }
}
