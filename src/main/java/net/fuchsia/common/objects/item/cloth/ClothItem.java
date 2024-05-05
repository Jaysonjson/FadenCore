package net.fuchsia.common.objects.item.cloth;

import net.fuchsia.common.objects.item.FadenItem;
import net.fuchsia.common.slot.ClothSlot;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;

public class ClothItem extends FadenItem implements Cloth {
    private String file;
    private Identifier texture;
    private Identifier textureWide;
    private boolean slim = false;
    public ClothItem(Settings settings, String file) {
        super(settings.maxCount(1));
        this.file = file;
        this.texture = FadenIdentifier.create("cloth/" + file);
        this.textureWide = FadenIdentifier.create("cloth/" + file + "_wide");
    }

    public String getFile() {
        return file;
    }

    public boolean isSlim() {
        return slim;
    }

    public Identifier getTexture() {
        return texture;
    }

    public Identifier getTextureWide() {
        return textureWide;
    }

    @Override
    public ClothSlot getClothType() {
        return ClothSlot.CHEST;
    }
}
