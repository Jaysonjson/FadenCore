package net.fuchsia.common.objects.race.cosmetic;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.client.util.ModelIdentifier;

public class RaceCosmetic {
    @Environment(EnvType.CLIENT)
    private ModelIdentifier model;
    private String modelId = "";
    private RaceCosmeticType type;
    private String id = "default";

    public RaceCosmetic(String modelId, RaceCosmeticType type) {
        this.modelId = modelId;
        this.type = type;
    }

    public RaceCosmetic(String modelId, RaceCosmeticType type, String id) {
        this.modelId = modelId;
        this.type = type;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    @Environment(EnvType.CLIENT)
    public ModelIdentifier getModel() {
        return FadenIdentifier.modelId(modelId);
    }

    public RaceCosmeticType getType() {
        return type;
    }
}
