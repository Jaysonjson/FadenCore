package net.fuchsia.client.mixin;

import com.sun.jna.platform.win32.OaIdl;
import net.fuchsia.client.render.feature.ChestFeatureRenderer;
import net.fuchsia.client.render.feature.ClothFeatureRenderer;
import net.fuchsia.client.render.feature.HeadFeatureRenderer;
import net.fuchsia.common.race.RaceModelType;
import net.fuchsia.common.race.data.ClientRaceCache;
import net.fuchsia.common.race.data.RaceData;
import net.fuchsia.config.FadenConfig;
import net.fuchsia.common.race.skin.client.ClientRaceSkinCache;
import net.fuchsia.config.FadenOptions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.feature.ArmorFeatureRenderer;
import net.minecraft.client.render.entity.model.ArmorEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin extends LivingEntityRendererMixin<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    private PlayerEntityRenderer renderer = ((PlayerEntityRenderer) ((Object) this));
    private PlayerEntityModel slimModel = null;
    private PlayerEntityModel wideModel = null;
    private ClothFeatureRenderer clothFeatureRenderer;
    protected PlayerEntityRendererMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Inject(method = "<init>*", at = @At("TAIL"))
    public void constructorHead(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo ci) {
        renderer.addFeature(new ChestFeatureRenderer(renderer));
        renderer.addFeature(new HeadFeatureRenderer(renderer));
        this.slimModel = new PlayerEntityModel(ctx.getPart(EntityModelLayers.PLAYER_SLIM), true);
        this.wideModel = new PlayerEntityModel(ctx.getPart(EntityModelLayers.PLAYER), false);
        clothFeatureRenderer = new ClothFeatureRenderer(this, slimModel, ctx.getModelManager());
        renderer.addFeature(clothFeatureRenderer);
    }

    @Inject(at = @At("HEAD"), method = "getTexture(Lnet/minecraft/client/network/AbstractClientPlayerEntity;)Lnet/minecraft/util/Identifier;", cancellable = true)
    private void getTextureAbstractPlayer(AbstractClientPlayerEntity abstractClientPlayerEntity, CallbackInfoReturnable<Identifier> cir) {
        if(FadenOptions.getConfig().ENABLE_PLAYER_RACE_SKINS) {
        	if(ClientRaceSkinCache.getPlayerSkins().containsKey(abstractClientPlayerEntity.getUuid())) {
        		cir.setReturnValue(ClientRaceSkinCache.getPlayerSkins().get(abstractClientPlayerEntity.getUuid()));
        	}
        }
    }


    @Inject(at = @At("HEAD"), method = "render(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", cancellable = true)
    private void render2(AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        if(FadenOptions.getConfig().ENABLE_PLAYER_RACE_SKINS) {
            RaceData data = ClientRaceCache.get(abstractClientPlayerEntity.getUuid());
            if (data.getRace() != null) {
                switch (data.getRace().model()) {
                    case SLIM -> {
                        this.model = slimModel;
                        clothFeatureRenderer.setInnerModel(this.model);
                    }

                    case WIDE -> {
                        this.model = wideModel;
                        clothFeatureRenderer.setInnerModel(this.model);
                    }

                    case BOTH -> {
                        Identifier id = ClientRaceSkinCache.getPlayerSkins().getOrDefault(abstractClientPlayerEntity.getUuid(), new Identifier("empty"));
                        if (id.toString().toLowerCase().contains("_slim")) {
                            this.model = slimModel;
                            clothFeatureRenderer.setInnerModel(this.model);
                        } else if (id.toString().toLowerCase().contains("_wide")) {
                            this.model = wideModel;
                            clothFeatureRenderer.setInnerModel(this.model);
                        }
                    }

                }
            }
        }
    }



    //DOES NOT WORK AS INTENDED; ONLY IN SINGLEPLAER
    /*@Override
    public void getModel(CallbackInfoReturnable<EntityModel> cir) {
        if(FadenOptions.getConfig().ENABLE_PLAYER_RACE_SKINS) {
            RaceData data = ClientRaceCache.get(MinecraftClient.getInstance().player.getUuid());
            if (data.getRace() != null) {
                switch (data.getRace().model()) {
                    case SLIM -> {
                        this.model = slimModel;
                        clothFeatureRenderer.setInnerModel(this.model);
                        cir.setReturnValue(slimModel);
                    }

                    case WIDE -> {
                        this.model = wideModel;
                        clothFeatureRenderer.setInnerModel(this.model);
                        cir.setReturnValue(wideModel);
                    }

                    case BOTH -> {
                        Identifier id = ClientRaceSkinCache.getPlayerSkins().getOrDefault(MinecraftClient.getInstance().player.getUuid(), new Identifier("empty"));
                        if (id.toString().toLowerCase().contains("_slim")) {
                            this.model = slimModel;
                            clothFeatureRenderer.setInnerModel(this.model);
                            cir.setReturnValue(slimModel);
                        } else if (id.toString().toLowerCase().contains("_wide")) {
                            this.model = wideModel;
                            clothFeatureRenderer.setInnerModel(this.model);
                            cir.setReturnValue(wideModel);
                        }
                    }

                }
            }
        }
    }*/

    @ModifyVariable(method = "renderArm", at = @At("STORE"), ordinal = 0)
    private Identifier injected(Identifier x, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, ModelPart arm, ModelPart sleeve) {
        if(FadenOptions.getConfig().ENABLE_PLAYER_RACE_SKINS) {
            ClientPlayerEntity clientPlayer = MinecraftClient.getInstance().player;
            if (ClientRaceSkinCache.getPlayerSkins().containsKey(clientPlayer.getUuid())) {
                return ClientRaceSkinCache.getPlayerSkins().get(clientPlayer.getUuid());
            }
        }
        return x;
    }

    @Inject(at = @At("HEAD"), method = "renderArm", cancellable = true)
    private void renderArm(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity player, ModelPart arm, ModelPart sleeve, CallbackInfo ci) {

        if(FadenOptions.getConfig().ENABLE_PLAYER_RACE_SKINS) {
            RaceData data = ClientRaceCache.get(MinecraftClient.getInstance().player.getUuid());
            PlayerEntityModel<AbstractClientPlayerEntity> playerEntityModel = null;
            if (data.getRace() != null) {
                switch (data.getRace().model()) {
                    case SLIM -> {
                        playerEntityModel = slimModel;
                    }

                    case WIDE -> {
                        playerEntityModel = wideModel;
                    }

                    case BOTH -> {
                        Identifier id = ClientRaceSkinCache.getPlayerSkins().getOrDefault(MinecraftClient.getInstance().player.getUuid(), new Identifier("empty"));
                        if (id.toString().toLowerCase().contains("_slim")) {
                            playerEntityModel = slimModel;
                        } else if (id.toString().toLowerCase().contains("_wide")) {
                            playerEntityModel = wideModel;
                        }
                    }

                }
            }
            if(playerEntityModel != null) {
                playerEntityModel.handSwingProgress = 0.0F;
                playerEntityModel.sneaking = false;
                playerEntityModel.leaningPitch = 0.0F;
                playerEntityModel.setAngles(player, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F);
                Arm playerArm = MinecraftClient.getInstance().options.getMainArm().getValue();
                if(playerArm == Arm.RIGHT) {
                    playerEntityModel.rightArm.pitch = 0.0F;
                } else {
                    playerEntityModel.leftArm.pitch = 0.0F;
                }
                Identifier identifier = player.getSkinTextures().texture();
                if (ClientRaceSkinCache.getPlayerSkins().containsKey(MinecraftClient.getInstance().player.getUuid())) {
                    identifier = ClientRaceSkinCache.getPlayerSkins().get(MinecraftClient.getInstance().player.getUuid());
                }

                if(playerArm == Arm.RIGHT) {
                    playerEntityModel.rightArm.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntitySolid(identifier)), light, OverlayTexture.DEFAULT_UV);
                    playerEntityModel.rightSleeve.pitch = 0.0F;
                    playerEntityModel.rightSleeve.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(identifier)), light, OverlayTexture.DEFAULT_UV);
                } else {
                    playerEntityModel.leftArm.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntitySolid(identifier)), light, OverlayTexture.DEFAULT_UV);
                    playerEntityModel.leftSleeve.pitch = 0.0F;
                    playerEntityModel.leftArm.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityTranslucent(identifier)), light, OverlayTexture.DEFAULT_UV);
                }

                ci.cancel();
            }
        }
    }

    @Inject(at = @At("HEAD"), method = "render(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", cancellable = true)
    private void size(AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        matrixStack.push();
        if(ClientRaceCache.getCache().containsKey(abstractClientPlayerEntity.getUuid())) {
            RaceData data = ClientRaceCache.get(abstractClientPlayerEntity.getUuid());
            matrixStack.scale(data.getRace().size().x, data.getRace().size().y, data.getRace().size().z);
        }
    }

    @Inject(at = @At("TAIL"), method = "render(Lnet/minecraft/client/network/AbstractClientPlayerEntity;FFLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;I)V", cancellable = true)
    private void sizeEnd(AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, CallbackInfo ci) {
        matrixStack.pop();
    }

}