package json.jayson.faden.core.client;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.model.PlayerEntityModel;

import java.util.UUID;

public interface IPlayerEntityRenderer {

    PlayerEntityModel<AbstractClientPlayerEntity> getPlayerModel(UUID playerUUID);
    boolean isSlim();
}
