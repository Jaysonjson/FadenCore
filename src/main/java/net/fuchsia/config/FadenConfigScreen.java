package net.fuchsia.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.BooleanListEntry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public class FadenConfigScreen {

    public static Screen create(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.translatable("config.faden.title"));

        ConfigEntryBuilder configEntryBuilder = builder.entryBuilder();

        BooleanListEntry enablePlayerRaceSkins = configEntryBuilder
                .startBooleanToggle(Text.translatable("config.faden.enable_player_race_skins"), FadenConfig.ENABLE_PLAYER_RACE_SKINS)
                .setDefaultValue(FadenConfig.ENABLE_PLAYER_RACE_SKINS)
                .build();

        ConfigCategory race = builder.getOrCreateCategory(Text.translatable("config.faden.category.race"));
        race.addEntry(enablePlayerRaceSkins);

        return builder.build();
    }

}
