package json.jayson.faden.core.client.data.listeners;

import json.jayson.faden.core.FadenCore;
import json.jayson.faden.core.common.objects.cloth.FadenCoreCloth;
import json.jayson.faden.core.common.race.skin.client.SkinTexture;
import json.jayson.faden.core.common.race.skin.provider.SkinProvider;
import json.jayson.faden.core.registry.FadenCoreRegistry;
import json.jayson.faden.core.util.FadenCoreIdentifier;
import net.fabricmc.fabric.api.resource.SimpleSynchronousResourceReloadListener;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.nio.file.Files;

public class ClothResourceListener implements SimpleSynchronousResourceReloadListener {

    public static void loadTexture(Identifier id) throws IOException {
        ModContainer modContainer = null;
        if(FabricLoader.getInstance().getModContainer(id.getNamespace()).isPresent()) {
            modContainer = FabricLoader.getInstance().getModContainer(id.getNamespace()).get();
            if(modContainer.findPath("assets/" + id.getNamespace() + "/textures/" + id.getPath() + ".png").isPresent()) {
                byte[] data = SkinProvider.readSkin(Files.newInputStream(modContainer.findPath("assets/" + id.getNamespace() + "/textures/" + id.getPath() + ".png").get()));
                SkinTexture skinTexture = new SkinTexture(id);
                skinTexture.setSkinData(data);
                //Destroy just in case
                MinecraftClient.getInstance().getTextureManager().destroyTexture(id);
                MinecraftClient.getInstance().getTextureManager().registerTexture(id, skinTexture);
                MinecraftClient.getInstance().getTextureManager().bindTexture(id);
            } else {
                System.out.println("Path {assets/" + id.getNamespace() + "/textures/" + id.getPath() + ".png} not Present!");
            }
        } else {
            System.out.println("Mod Container {" + id.getNamespace() + "} not Present!");
        }
    }

    @Override
    public Identifier getFabricId() {
        return FadenCoreIdentifier.create("cloth");
    }

    @Override
    public void reload(ResourceManager manager) {
        try {
            FadenCore.LOGGER.debug("STARTING CLOTH LOADING");
            for (FadenCoreCloth cloth : FadenCoreRegistry.CLOTH) {
                if(cloth.getTexture().getLeft() != null) {
                    loadTexture(cloth.getTexture().getLeft());
                }
                if(cloth.getTexture().getRight() != null) {
                    loadTexture(cloth.getTexture().getRight());
                }
            }
            FadenCore.LOGGER.debug("FINISHED CLOTH LOADING");
        } catch (IOException e) {
            System.out.println("ERROR CLOTH");
            e.printStackTrace();
        }
    }
}
