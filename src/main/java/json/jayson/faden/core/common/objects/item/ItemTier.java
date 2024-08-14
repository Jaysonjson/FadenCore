package json.jayson.faden.core.common.objects.item;

import json.jayson.faden.core.util.FadenCoreIdentifier;
import net.minecraft.util.Identifier;

public enum ItemTier {

    COMMON(FadenCoreIdentifier.create("textures/item_tier/common.png"), 0xFFFFFFFF,1, 1),
    UNCOMMON(FadenCoreIdentifier.create("textures/item_tier/uncommon.png"), 0xFFFFFFFF,1.3f, 1.1f),
    EPIC(FadenCoreIdentifier.create("textures/item_tier/epic.png"), 0xFFFFFFFF,1.7f, 1.2f),
    RARE(FadenCoreIdentifier.create("textures/item_tier/rare.png"), 0xFFFFFFFF,2.0f, 1.3f),
    LEGENDARY(FadenCoreIdentifier.create("textures/item_tier/legendary.png"), 0xFFFFFFFF,2.3f, 1.4f),
    MYTHIC(FadenCoreIdentifier.create("textures/item_tier/mythic.png"), 0xFFFFFFFF,2.6f, 1.5f);

    Identifier icon;
    int color;
    float durabilityMultiplier;
    float sellValueMultiplier;
    ItemTier(Identifier icon, int color, float durabilityMultiplier, float sellValueMultiplier) {
        this.icon = icon;
        this.color = color;
        this.durabilityMultiplier = durabilityMultiplier;
        this.sellValueMultiplier = sellValueMultiplier;
    }

    public int getColor() {
        return color;
    }

    public Identifier getIcon() {
        return icon;
    }

    public float getDurabilityMultiplier() {
        return durabilityMultiplier;
    }

    public float getSellValueMultiplier() {
        return sellValueMultiplier;
    }
}
