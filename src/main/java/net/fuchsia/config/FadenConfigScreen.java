package net.fuchsia.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.gui.entries.BooleanListEntry;
import net.fabricmc.loader.api.FabricLoader;
import net.fuchsia.Faden;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class FadenConfigScreen {

    public static Screen create(Screen parent) {
        ConfigBuilder builder = ConfigBuilder.create()
                .setParentScreen(parent)
                .setTitle(Text.translatable("config.faden.title"));

        ConfigEntryBuilder configEntryBuilder = builder.entryBuilder();

        BooleanListEntry enablePlayerRaceSkins = configEntryBuilder
                .startBooleanToggle(Text.translatable("config.faden.enable_player_race_skins"), FadenOptions.getConfig().ENABLE_PLAYER_RACE_SKINS)
                .setDefaultValue(FadenOptions.getConfig().ENABLE_PLAYER_RACE_SKINS)
                .build();

        BooleanListEntry enableCustomCapes = configEntryBuilder
                .startBooleanToggle(Text.translatable("config.faden.enable_custom_capes"), FadenOptions.getConfig().CUSTOM_CAPES)
                .setDefaultValue(FadenOptions.getConfig().CUSTOM_CAPES)
                .build();

        BooleanListEntry enableVanillaBlur = configEntryBuilder
                .startBooleanToggle(Text.translatable("config.faden.enable_vanilla_blur"), FadenOptions.getConfig().VANILLA_BLUR)
                .setDefaultValue(FadenOptions.getConfig().VANILLA_BLUR)
                .build();

        ConfigCategory race = builder.getOrCreateCategory(Text.translatable("config.faden.category.race"));
        race.addEntry(enablePlayerRaceSkins);

        ConfigCategory inter = builder.getOrCreateCategory(Text.translatable("config.faden.category.interface"));
        inter.addEntry(enableVanillaBlur);

        ConfigCategory vanilla = builder.getOrCreateCategory(Text.translatable("config.faden.category.vanilla"));
        vanilla.addEntry(enableVanillaBlur);

        ConfigCategory faden = builder.getOrCreateCategory(Text.literal("Faden"));
        faden.addEntry(enablePlayerRaceSkins);
        faden.addEntry(enableCustomCapes);


        builder.setSavingRunnable(() -> {
            FadenOptions.setConfig(new FadenConfig(enablePlayerRaceSkins.getValue(),  enableVanillaBlur.getValue(), enableCustomCapes.getValue()));
            try {
                FileUtils.writeStringToFile(new File(FabricLoader.getInstance().getConfigDir().toFile() + "/faden.json"), Faden.GSON.toJson(FadenOptions.getConfig()), StandardCharsets.UTF_8);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        return builder.build();
    }

}
