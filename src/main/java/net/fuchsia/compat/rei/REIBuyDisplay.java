package net.fuchsia.compat.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.plugin.common.displays.beacon.DefaultBeaconDisplay;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;

import java.util.Collections;
import java.util.List;

public class REIBuyDisplay extends BasicDisplay {


    public int amount;
    public ItemConvertible out;
    public REIBuyDisplay(List<EntryIngredient> inputs, ItemConvertible out, int amount) {
        super(inputs, Collections.singletonList(EntryIngredients.of(out)));
        this.amount = amount;
        this.out = out;
    }

    public EntryIngredient getEntries() {
        return getInputEntries().get(0);
    }

    @Override
    public List<EntryIngredient> getOutputEntries() {
        return super.getOutputEntries();
    }

    public static <T extends DefaultBeaconDisplay> BasicDisplay.Serializer<T> serializer(BasicDisplay.Serializer.SimpleRecipeLessConstructor<T> constructor) {
        return BasicDisplay.Serializer.ofSimpleRecipeLess(constructor);
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return FadenREIServerPlugin.BUY_DISPLAY;
    }
}
