package net.fuchsia.compat.rei;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplaySerializerRegistry;
import me.shedaniel.rei.api.common.plugins.REIServerPlugin;
import me.shedaniel.rei.api.common.transfer.info.MenuInfoRegistry;
import net.fuchsia.util.FadenIdentifier;

public class FadenREIServerPlugin implements REIServerPlugin {

    public static final CategoryIdentifier<REIBuyDisplay> BUY_DISPLAY = CategoryIdentifier.of(FadenIdentifier.create("buy_display"));


    @Override
    public void registerMenuInfo(MenuInfoRegistry registry) {
    }

    @Override
    public void registerDisplaySerializer(DisplaySerializerRegistry registry) {
    }
}
