package net.fuchsia.common.objects.item;

import json.jayson.common.init.FadenItems;
import json.jayson.common.objects.CoinMap;
import json.jayson.common.objects.tooltip.ItemValueTooltipData;
import net.minecraft.client.item.TooltipData;
import net.minecraft.client.particle.ElderGuardianAppearanceParticle;
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

import java.util.Collections;
import java.util.LinkedHashMap;
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
        if (!coinStack.isEmpty() && coinStack.getItem() instanceof CoinItem coinItem) {
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
                CoinItem checkCoin = (CoinItem)compoundStack.getItem();
                if(checkCoin.getValue() < coinItem.getValue()){
                    NbtCompound newCoinCompound = new NbtCompound();
                    coinStack.writeNbt(newCoinCompound);
                    coinList.add(i, newCoinCompound);
                    coinAdded = true;
                    break;
                }
            }

            if(!coinAdded){
                NbtCompound newCoinCompound = new NbtCompound();
                coinStack.writeNbt(newCoinCompound);
                coinList.add(newCoinCompound);
            }
        }
    }

    public static LinkedHashMap<Item, Integer> getCoinStacks(ItemStack pouch){
        LinkedHashMap<Item, Integer> coinCounts = new LinkedHashMap<>();

        NbtCompound nbtCompound = pouch.getOrCreateNbt();
        if(!nbtCompound.contains(COINS_KEY)) return coinCounts;

        NbtList coinList = nbtCompound.getList(COINS_KEY, 10);
        coinList.forEach(element -> {
            NbtCompound coinCompound = (NbtCompound)element;
            ItemStack compoundStack = ItemStack.fromNbt(coinCompound);
            coinCounts.put(compoundStack.getItem(), compoundStack.getCount());
        });

        return coinCounts;
    }

    @Override
    public Optional<TooltipData> getTooltipData(ItemStack stack) {
        LinkedHashMap<Item, Integer> itemStacks = getCoinStacks(stack);
        return Optional.of(new ItemValueTooltipData(itemStacks));
    }
}
