package net.fuchsia.common.cape;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.fuchsia.FadenCore;
import net.fuchsia.common.race.skin.client.SkinTexture;
import net.fuchsia.common.race.skin.provider.SkinProvider;
import net.fuchsia.util.FadenCoreIdentifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.nio.file.Files;

public class FadenCape {

    private Identifier texture;
    private Identifier id;
    private Text name;
    private Text description;
    private boolean loaded = false;
    private byte[] textureData = null;

    public FadenCape(Identifier id, Text name, Text description) {
        this.texture = Identifier.of(id.getNamespace(), "cape/" + id.getPath());
        this.id = id;
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

    public String getId() {
        return id.getPath();
    }

    public void setTextureData(byte[] textureData) {
        this.textureData = textureData;
    }

    @Environment(EnvType.CLIENT)
    public void load() {
        if(!loaded) {
            try {
                if(textureData == null) {
                    textureData = SkinProvider.readSkin(Files.newInputStream(FabricLoader.getInstance().getModContainer(id.getNamespace()).get().findPath("assets/" + id.getNamespace() + "/textures/cape/" + id.getPath() + ".png").get()));
                }
                SkinTexture skinTexture = new SkinTexture(texture);
                skinTexture.setSkinData(textureData);
                MinecraftClient.getInstance().getTextureManager().registerTexture(texture, skinTexture);
                MinecraftClient.getInstance().getTextureManager().bindTexture(texture);
                textureData = null;
            } catch (IOException e) {
                FadenCore.LOGGER.error("{} failed to load", "Cape " + getId(), e);
            }
            loaded = true;
        }
    }

}
