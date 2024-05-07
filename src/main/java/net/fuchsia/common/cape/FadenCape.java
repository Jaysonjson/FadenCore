package net.fuchsia.common.cape;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fuchsia.Faden;
import net.fuchsia.common.race.skin.client.SkinTexture;
import net.fuchsia.common.race.skin.provider.SkinProvider;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.io.IOException;
import java.nio.file.Files;

public class FadenCape {

    private Identifier texture;
    private String id;
    private Text name;
    private boolean loaded = false;

    public FadenCape(String id, Text name) {
        this.texture = FadenIdentifier.create("cape/" + id);
        this.id = id;
        this.name = name;
    }

    public Text getName() {
        return name;
    }

    public Identifier getTexture() {
        return texture;
    }

    public String getId() {
        return id;
    }

    @Environment(EnvType.CLIENT)
    public void load() {
        if(!loaded) {
            try {
                byte[] data = SkinProvider.readSkin(Files.newInputStream(Faden.CONTAINER.findPath("assets/faden/textures/capes/" + id + ".png").get()));
                SkinTexture skinTexture = new SkinTexture(texture);
                skinTexture.setSkinData(data);
                MinecraftClient.getInstance().getTextureManager().registerTexture(texture, skinTexture);
                MinecraftClient.getInstance().getTextureManager().bindTexture(texture);
            } catch (IOException e) {
                e.printStackTrace();
            }
            loaded = true;
        }
    }

}
