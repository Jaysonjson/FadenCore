package net.fuchsia.common.race.cosmetic;

import net.minecraft.client.util.ModelIdentifier;

public class RaceCosmetic {

    private ModelIdentifier model;
    private RaceCosmeticType type;


    public RaceCosmetic(ModelIdentifier model, RaceCosmeticType type) {
        this.model = model;
        this.type = type;
    }


    public ModelIdentifier getModel() {
        return model;
    }

    public RaceCosmeticType getType() {
        return type;
    }
}
