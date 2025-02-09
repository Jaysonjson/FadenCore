package json.jayson.faden.core.client.mixin;

import json.jayson.faden.core.client.PlayerModelCache;
import json.jayson.faden.core.client.render.feature.player.mixin.PlayerEntityRendererHelper;
import json.jayson.faden.core.common.race.FadenCoreRace;
import json.jayson.faden.core.client.IPlayerEntityRenderer;
import json.jayson.faden.core.client.render.feature.player.ChestFeatureRenderer;
import json.jayson.faden.core.client.render.feature.player.ClothFeatureRenderer;
import json.jayson.faden.core.client.render.feature.player.HeadFeatureRenderer;
import json.jayson.faden.core.common.race.skin.client.ClientRaceSkinCache;
import json.jayson.faden.core.config.FadenCoreOptions;
import json.jayson.faden.core.server.PlayerData;
import json.jayson.faden.core.server.client.ClientPlayerDatas;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRendererMixin<AbstractClientPlayerEntity, PlayerEntityRenderState, PlayerEntityModel> implements IPlayerEntityRenderer {
    @Unique
    private PlayerEntityRenderer renderer = ((PlayerEntityRenderer) ((Object) this));
    //RECENTLY MADE STATIC, IF BROKE: REMOVE STATIC TO FIX!

    @Unique
    private boolean slim = false;
    @Unique
    private PlayerEntityModel slimModel;
    @Unique
    private PlayerEntityModel wideModel;
    @Unique
    private boolean defaultSlim = false;
    protected PlayerEntityRendererMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Inject(method = "<init>*", at = @At("TAIL"))
    public void constructorHead(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo ci) {
        renderer.addFeature(new ChestFeatureRenderer<>(renderer));
        renderer.addFeature(new HeadFeatureRenderer<>(renderer));
        renderer.addFeature(new ClothFeatureRenderer<>(this, this));
        slimModel = PlayerModelCache.makeSlimModel();
        wideModel = PlayerModelCache.makeWideModel();
        defaultSlim = slim;
    }

    @Inject(at = @At("HEAD"), method = "getTexture(Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;)Lnet/minecraft/util/Identifier;", cancellable = true)
    private void getTextureAbstractPlayer(PlayerEntityRenderState playerEntityRenderState, CallbackInfoReturnable<Identifier> cir) {
        if(FadenCoreOptions.getConfig().ENABLE_PLAYER_RACE_SKINS) {
            AbstractClientPlayerEntity player = PlayerEntityRendererHelper.getPlayer(playerEntityRenderState);
            if(player != null) {
                if (ClientRaceSkinCache.hasSkin(player.getUuid())) {
                    cir.setReturnValue(ClientRaceSkinCache.getSkin(player.getUuid()));
                }
            }
        }
    }

    @Inject(at = @At("TAIL"), method = "setupTransforms(Lnet/minecraft/client/render/entity/state/PlayerEntityRenderState;Lnet/minecraft/client/util/math/MatrixStack;FF)V")
    private void setModelPose(PlayerEntityRenderState playerEntityRenderState, MatrixStack matrixStack, float f, float g, CallbackInfo ci) {
        PlayerEntityRendererHelper.hideSecondLayer(playerEntityRenderState, this.model);
    }

    @ModifyVariable(method = "renderArm", at = @At("STORE"), ordinal = 0)
    private Identifier renderArm(Identifier x, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Identifier skinTexture, ModelPart arm, boolean sleeveVisible) {
        Identifier identifier = x;
        if(FadenCoreOptions.getConfig().ENABLE_PLAYER_RACE_SKINS) {
            ClientPlayerEntity clientPlayer = MinecraftClient.getInstance().player;
            if (ClientRaceSkinCache.hasSkin(clientPlayer.getUuid())) {
                identifier = ClientRaceSkinCache.getSkin(clientPlayer.getUuid());
            }
        }
        return identifier;
    }

    @Inject(at = @At("TAIL"), method = "renderArm")
    private void renderClothArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Identifier skinTexture, ModelPart arm, boolean sleeveVisible, CallbackInfo ci) {
        PlayerEntityRendererHelper.renderArms(arm, slim, this.model, matrices, vertexConsumers, light);
    }

    @Inject(at = @At("HEAD"), method = "renderArm")
    private void renderArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, Identifier skinTexture, ModelPart arm, boolean sleeveVisible, CallbackInfo ci) {
        if(FadenCoreOptions.getConfig().ENABLE_PLAYER_RACE_SKINS) {
            this.model = getPlayerModel(MinecraftClient.getInstance().player.getUuid());
        }
    }

    @Override
    public boolean isSlim() {
        return slim;
    }

    @Override
    public PlayerEntityModel getPlayerModel(UUID playerUuid) {
        PlayerData data = ClientPlayerDatas.getPlayerData(playerUuid);
        PlayerEntityModel playerEntityModel = defaultSlim ? slimModel : wideModel;
        if (data.getRaceSaveData().getRace() != null) {
            FadenCoreRace fadenCoreRace = data.getRaceSaveData().getRace();
            switch (fadenCoreRace.getModelType()) {
                case SLIM -> {
                    slim = true;
                    return slimModel;
                }

                case WIDE -> {
                    slim = false;
                    return wideModel;
                }

                case BOTH -> {
                    Identifier id = ClientRaceSkinCache.getSkin(MinecraftClient.getInstance().player.getUuid());
                    if (id.toString().toLowerCase().contains("_slim")) {
                        slim = true;
                        return slimModel;
                    } else if (id.toString().toLowerCase().contains("_wide")) {
                        slim = false;
                        return wideModel;
                    }
                }

            }
        }
        return playerEntityModel;
    }


    //TODO REMOVED SIZE INJECTION DUE TO 1.21.4 PORT, MAYBE REMOVE IT AND JUST USE MCS ATTRIBUTES?
    /*@Inject(at = @At("HEAD"), method = "render(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", cancellable = true)
    private void size(AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        matrixStack.push();
        PlayerData data = ClientPlayerDatas.getPlayerData(abstractClientPlayerEntity.getUuid());
        if(data.getRaceSaveData().hasRace()) {
            FadenCoreRace fadenCoreRace = data.getRaceSaveData().getRace();
            matrixStack.scale(fadenCoreRace.getSize().x, fadenCoreRace.getSize().y, fadenCoreRace.getSize().z);
        }
    }

    @Inject(at = @At("TAIL"), method = "render(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", cancellable = true)
    private void sizeEnd(AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        matrixStack.pop();
    }*/

}