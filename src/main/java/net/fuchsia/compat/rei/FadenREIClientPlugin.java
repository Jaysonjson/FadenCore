package net.fuchsia.compat.rei;

import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fuchsia.server.FadenData;
import net.fuchsia.common.data.ItemValues;
import net.fuchsia.common.objects.CoinMap;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

public class FadenREIClientPlugin implements REIClientPlugin {

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new REIBuyCategory());
        Collection<ItemConvertible> coins = new ArrayList<>();
        for (Item value : CoinMap.COINS.values()) {
            coins.add(value);
        }
        //registry.addWorkstations(BUY_DISPLAY, EntryIngredients.ofItems(CoinMap.COINS.values()));
        registry.addWorkstations(FadenREIServerPlugin.BUY_DISPLAY, EntryIngredients.ofItems(coins));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        //NEED TO LOOK BETTER INTO REI AND CHANGE THIS
        for (Item item : ItemValues.VALUES.keySet()) {
            int amount = ItemValues.VALUES.get(item);
            Collection<ItemStack> stacks = new ArrayList<>();
            Map<Item, Integer> map = CoinMap.generateCoinItemStacks(amount * FadenData.BUY_MULTIPLIER);
            for (Item item1 : map.keySet()) {
                stacks.add(new ItemStack(item1, map.get(item1)));
            }
            registry.add(new REIBuyDisplay(Collections.singletonList(EntryIngredients.ofItemStacks(stacks)), item, amount * FadenData.BUY_MULTIPLIER));

        }
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {

    }

}
