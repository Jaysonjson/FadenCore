package net.fuchsia.common.cape;

import net.fuchsia.Faden;
import net.fuchsia.common.race.skin.client.SkinTexture;
import net.fuchsia.common.race.skin.provider.SkinProvider;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;

import java.io.IOException;

public class FadenCape {

    private Identifier texture;
    private String id;
    private boolean loaded = false;

    public FadenCape(String id) {
        this.texture = FadenIdentifier.create("cape/" + id);
        this.id = id;
    }

    public Identifier getTexture() {
        return texture;
    }

    public String getId() {
        return id;
    }

    public void load() {
        if(!loaded) {
            try {
                byte[] data = SkinProvider.readSkin(Faden.CONTAINER.findPath("assets/faden/textures/capes/" + id + ".png").get().toFile());
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
