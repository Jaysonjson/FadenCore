package json.jayson.faden.core.common.objects.item.cloth;

import json.jayson.faden.core.common.objects.item.FadenItem;
import json.jayson.faden.core.common.slot.ClothSlot;
import json.jayson.faden.core.util.FadenCoreIdentifier;
import net.minecraft.util.Identifier;

public class ClothItem extends FadenItem implements Cloth {
    private String file;
    private Identifier texture;
    private Identifier textureWide;
    private boolean slim = false;
    public ClothItem(Settings settings, String file) {
        super(settings.maxCount(1));
        this.file = file;
        this.texture = FadenCoreIdentifier.create("cloth/" + file);
        this.textureWide = FadenCoreIdentifier.create("cloth/" + file + "_wide");
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
