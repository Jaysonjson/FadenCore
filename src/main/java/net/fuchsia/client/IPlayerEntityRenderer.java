package net.fuchsia.client;

import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.entity.model.PlayerEntityModel;

public interface IPlayerEntityRenderer {

    PlayerEntityModel<AbstractClientPlayerEntity> getPlayerModel();
    boolean slim();
}
