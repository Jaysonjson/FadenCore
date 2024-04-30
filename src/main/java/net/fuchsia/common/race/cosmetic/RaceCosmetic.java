package net.fuchsia.common.race.cosmetic;

import net.minecraft.client.util.ModelIdentifier;

public class RaceCosmetic {

    private ModelIdentifier model;
    private RaceCosmeticType type;
    private String id = "default";

    public RaceCosmetic(ModelIdentifier model, RaceCosmeticType type) {
        this.model = model;
        this.type = type;
    }

    public RaceCosmetic(ModelIdentifier model, RaceCosmeticType type, String id) {
        this.model = model;
        this.type = type;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public ModelIdentifier getModel() {
        return model;
    }

    public RaceCosmeticType getType() {
        return type;
    }
}
