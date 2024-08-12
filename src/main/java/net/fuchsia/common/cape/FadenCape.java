package net.fuchsia.common.cape;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
    private String id;
    private Text name;
    private Text description;
    private boolean loaded = false;
    private byte[] textureData = null;

    public FadenCape(String id, Text name, Text description) {
        this.texture = FadenCoreIdentifier.create("cape/" + id);
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
        return id;
    }

    public void setTextureData(byte[] textureData) {
        this.textureData = textureData;
    }

    @Environment(EnvType.CLIENT)
    public void load() {
        if(!loaded) {
            try {
                if(textureData == null) {
                    textureData = SkinProvider.readSkin(Files.newInputStream(FadenCore.CONTAINER.findPath("assets/faden/textures/cape/" + id + ".png").get()));
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
