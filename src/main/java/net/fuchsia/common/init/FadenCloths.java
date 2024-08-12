package net.fuchsia.common.init;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fuchsia.Faden;
import net.fuchsia.common.init.items.FadenItems;
import net.fuchsia.common.objects.item.cloth.ClothItem;
import net.fuchsia.common.race.skin.client.SkinTexture;
import net.fuchsia.common.race.skin.provider.SkinProvider;
import net.fuchsia.datagen.DataItemModel;
import net.fuchsia.datagen.holders.FadenDataItem;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FadenCloths {
    public static List<ClothItem> CLOTHES = new ArrayList<>();

    public static ClothItem TEST_CLOTH = registerItem("test_cloth", new ClothItem(new Item.Settings(), "test_cloth"), "ingots/silver_ingot");

    private static <T extends ClothItem> T registerItem(String name, T item, String texture, DataItemModel itemModel) {
        T i = Registry.register(Registries.ITEM, FadenIdentifier.create(name), item);
        FadenItems.ITEMS.add(new FadenDataItem(i, texture, itemModel, FadenTabs.FADEN_CLOTHES));
        CLOTHES.add(i);
        return i;
    }

    private static <T extends ClothItem> T registerItem(String name, T item, String texture) {
        return registerItem(name, item, texture, DataItemModel.GENERATED);
    }

    private static boolean loaded = false;
    @Environment(EnvType.CLIENT)
    public static void load() {
        if(!loaded) {
            try {
                Faden.LOGGER.debug("STARTING CLOTH LOADING");
                for (ClothItem cloth : CLOTHES) {
                    byte[] data = SkinProvider.readSkin(Files.newInputStream(Faden.CONTAINER.findPath("assets/faden/textures/cloth/" + cloth.getFile() + ".png").get()));
                    SkinTexture skinTexture = new SkinTexture(cloth.getTexture());
                    skinTexture.setSkinData(data);
                    Faden.LOGGER.debug("BINDING {}", cloth.getTexture());
                    MinecraftClient.getInstance().getTextureManager().registerTexture(cloth.getTexture(), skinTexture);
                    MinecraftClient.getInstance().getTextureManager().bindTexture(cloth.getTexture());

                    data = SkinProvider.readSkin(Files.newInputStream(Faden.CONTAINER.findPath("assets/faden/textures/cloth/" + cloth.getFile() + "_wide.png").get()));
                    skinTexture = new SkinTexture(cloth.getTextureWide());
                    skinTexture.setSkinData(data);
                    MinecraftClient.getInstance().getTextureManager().registerTexture(cloth.getTextureWide(), skinTexture);
                    MinecraftClient.getInstance().getTextureManager().bindTexture(cloth.getTextureWide());
                }
                Faden.LOGGER.debug("FINISHED CLOTH LOADING");
            } catch (IOException e) {
                System.out.println("ERROR CLOTH");
                e.printStackTrace();
            }
            loaded = true;
        }
    }

    public static void register() {}

}
