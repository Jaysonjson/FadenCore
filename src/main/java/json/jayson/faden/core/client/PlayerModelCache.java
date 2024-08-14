package json.jayson.faden.core.client;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.model.ElytraEntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PlayerEntityModel;

public class PlayerModelCache {

    public static PlayerEntityModel slimModel = null;
    public static PlayerEntityModel wideModel = null;

    public static ElytraEntityModel elytraEntityModel = null;

    public static PlayerEntityModel makeSlimModel() {
        return new PlayerEntityModel(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(EntityModelLayers.PLAYER_SLIM), true);
    }

    public static PlayerEntityModel makeWideModel() {
        return new PlayerEntityModel(MinecraftClient.getInstance().getEntityModelLoader().getModelPart(EntityModelLayers.PLAYER), false);
    }

}
