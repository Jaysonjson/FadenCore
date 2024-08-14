package json.jayson.faden.core.common.race.cosmetic;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.util.Identifier;

public class RaceCosmetic {
    @Environment(EnvType.CLIENT)
    private ModelIdentifier model;
    private String modelId = "";
    private RaceCosmeticSlot slot;
    private String id = "default";
    private String type = "";

    public RaceCosmetic(String modelId, RaceCosmeticSlot slot) {
        this.modelId = modelId;
        this.slot = slot;
    }

    public RaceCosmetic(String modelId, RaceCosmeticSlot slot, String id) {
        this.modelId = modelId;
        this.slot = slot;
        this.id = id;
    }

    public RaceCosmetic(String modelId, RaceCosmeticSlot slot, String id, String type) {
        this.modelId = modelId;
        this.slot = slot;
        this.id = id;
        this.type = type;
    }

    public String getType() {
        return type;
    }

    public String getId() {
        return id;
    }

    @Environment(EnvType.CLIENT)
    public ModelIdentifier getModel() {
        return new ModelIdentifier(Identifier.of(modelId), "inventory");
    }

    public RaceCosmeticSlot getSlot() {
        return slot;
    }
}
