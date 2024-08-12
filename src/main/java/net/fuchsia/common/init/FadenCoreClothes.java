package net.fuchsia.common.init;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.fuchsia.FadenCore;
import net.fuchsia.common.init.items.FadenCoreItems;
import net.fuchsia.common.objects.item.cloth.ClothItem;
import net.fuchsia.common.race.skin.client.SkinTexture;
import net.fuchsia.common.race.skin.provider.SkinProvider;
import net.fuchsia.datagen.DataItemModel;
import net.fuchsia.datagen.holders.FadenDataItem;
import net.fuchsia.util.FadenCoreIdentifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class FadenCoreClothes {
    public static List<ClothItem> CLOTHES = new ArrayList<>();

    public static <T extends ClothItem> T registerItem(String name, T item, String texture, DataItemModel itemModel) {
        T i = Registry.register(Registries.ITEM, FadenCoreIdentifier.create(name), item);
        FadenCoreItems.ITEMS.add(new FadenDataItem(i, texture, itemModel, null));
        CLOTHES.add(i);
        return i;
    }

    public static <T extends ClothItem> T registerItem(String name, T item, String texture) {
        return registerItem(name, item, texture, DataItemModel.GENERATED);
    }

    private static boolean loaded = false;
    @Environment(EnvType.CLIENT)
    public static void load() {
        if(!loaded) {
            try {
                FadenCore.LOGGER.debug("STARTING CLOTH LOADING");
                for (ClothItem cloth : CLOTHES) {
                    Identifier id = Registries.ITEM.getId(cloth);
                    byte[] data = SkinProvider.readSkin(Files.newInputStream(FabricLoader.getInstance().getModContainer(id.getNamespace()).get().findPath("assets/" + id.getNamespace() + "/textures/cloth/" + cloth.getFile() + ".png").get()));
                    SkinTexture skinTexture = new SkinTexture(cloth.getTexture());
                    skinTexture.setSkinData(data);
                    FadenCore.LOGGER.debug("BINDING {}", cloth.getTexture());
                    MinecraftClient.getInstance().getTextureManager().registerTexture(cloth.getTexture(), skinTexture);
                    MinecraftClient.getInstance().getTextureManager().bindTexture(cloth.getTexture());

                    data = SkinProvider.readSkin(Files.newInputStream(FabricLoader.getInstance().getModContainer(id.getNamespace()).get().findPath("assets/" + id.getNamespace() + "/textures/cloth/" + cloth.getFile() + "_wide.png").get()));
                    skinTexture = new SkinTexture(cloth.getTextureWide());
                    skinTexture.setSkinData(data);
                    MinecraftClient.getInstance().getTextureManager().registerTexture(cloth.getTextureWide(), skinTexture);
                    MinecraftClient.getInstance().getTextureManager().bindTexture(cloth.getTextureWide());
                }
                FadenCore.LOGGER.debug("FINISHED CLOTH LOADING");
            } catch (IOException e) {
                System.out.println("ERROR CLOTH");
                e.printStackTrace();
            }
            loaded = true;
        }
    }
}
