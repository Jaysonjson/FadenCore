package net.fuchsia.common.objects.item;

public enum ItemTier {

    COMMON(1, 1),
    UNCOMMON(1.3f, 1.1f),
    EPIC(1.5f, 1.2f),
    RARE(1.7f, 1.3f),
    LEGENDARY(1.9f, 1.4f),
    MYTHIC(2.1f, 1.5f);

    float durabilityMultiplier;
    float sellValueMultiplier;
    ItemTier(float durabilityMultiplier, float sellValueMultiplier) {
        this.durabilityMultiplier = durabilityMultiplier;
        this.sellValueMultiplier = sellValueMultiplier;
    }

    public float getDurabilityMultiplier() {
        return durabilityMultiplier;
    }

    public float getSellValueMultiplier() {
        return sellValueMultiplier;
    }
}
