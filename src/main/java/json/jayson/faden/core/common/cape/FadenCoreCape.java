package json.jayson.faden.core.common.cape;

import json.jayson.faden.core.registry.FadenCoreRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import json.jayson.faden.core.FadenCore;
import json.jayson.faden.core.common.race.skin.client.SkinTexture;
import json.jayson.faden.core.common.race.skin.provider.SkinProvider;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.nio.file.Files;

public class FadenCoreCape {

    private Identifier texture;
    private Text name;
    private Text description;
    private boolean loaded = false;
    private byte[] textureData = null;

    public FadenCoreCape(Text name, Text description) {
        this.name = name;
        this.description = description;
    }

    public Text getName() {
        return name;
    }

    public Text getDescription() {
        return description;
    }

    public Identifier getTexture() {
        return texture;
    }

    public Identifier getIdentifier() {
        return FadenCoreRegistry.CAPE.getId(this);
    }


    public void setTextureData(byte[] textureData) {
        this.textureData = textureData;
    }

    @Environment(EnvType.CLIENT)
    public void load() {
        if(!loaded) {
            this.texture = Identifier.of(getIdentifier().getNamespace(), "textures/cape/" + getIdentifier().getPath() + ".png");
            try {
                if(textureData == null) {
                    textureData = SkinProvider.readSkin(Files.newInputStream(FabricLoader.getInstance().getModContainer(getIdentifier().getNamespace()).get().findPath("assets/" + getIdentifier().getNamespace() + "/textures/cape/" + getIdentifier().getPath() + ".png").get()));
                }
                SkinTexture skinTexture = new SkinTexture(texture);
                skinTexture.setSkinData(textureData);
                MinecraftClient.getInstance().getTextureManager().registerTexture(texture, skinTexture);
                MinecraftClient.getInstance().getTextureManager().bindTexture(texture);
                textureData = null;
            } catch (IOException e) {
                FadenCore.LOGGER.error("{} failed to load", "Cape " + getName().getString(), e);
            }
            loaded = true;
        }
    }

}
