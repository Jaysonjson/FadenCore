package net.fuchsia.client.mixin;

import net.fuchsia.client.render.feature.ChestFeatureRenderer;
import net.fuchsia.client.render.feature.HeadFeatureRenderer;
import net.fuchsia.common.race.data.ClientRaceCache;
import net.fuchsia.common.race.data.RaceData;
import net.fuchsia.config.FadenConfig;
import net.fuchsia.common.race.skin.client.ClientRaceSkinCache;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
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
    private AbstractClientPlayerEntity player = null;

    protected PlayerEntityRendererMixin(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Inject(method = "<init>*", at = @At("TAIL"))
    public void constructorHead(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo ci) {
        renderer.addFeature(new ChestFeatureRenderer(renderer));
        renderer.addFeature(new HeadFeatureRenderer(renderer));
        this.slimModel = new PlayerEntityModel(ctx.getPart(EntityModelLayers.PLAYER_SLIM), true);
        this.wideModel = new PlayerEntityModel(ctx.getPart(EntityModelLayers.PLAYER), false);
    }

    @Inject(at = @At("HEAD"), method = "getTexture(Lnet/minecraft/client/network/AbstractClientPlayerEntity;)Lnet/minecraft/util/Identifier;", cancellable = true)
    private void getTextureAbstractPlayer(AbstractClientPlayerEntity abstractClientPlayerEntity, CallbackInfoReturnable<Identifier> cir) {
        player = abstractClientPlayerEntity;
        if(FadenConfig.ENABLE_PLAYER_RACE_SKINS) {
        	if(ClientRaceSkinCache.getPlayerSkins().containsKey(abstractClientPlayerEntity.getUuid())) {
        		cir.setReturnValue(ClientRaceSkinCache.getPlayerSkins().get(abstractClientPlayerEntity.getUuid()));
        	}
        }
    }

    @Override
    public void getModel(CallbackInfoReturnable<EntityModel> cir) {
        if(player != null) {
            RaceData data = ClientRaceCache.get(player.getUuid());
            if(data.getRace() != null) {
                if(data.getRace().slim()) {
                    this.model = slimModel;
                    cir.setReturnValue(slimModel);
                } else {
                    this.model = wideModel;
                    cir.setReturnValue(wideModel);
                }
            }
        }
    }

    @ModifyVariable(method = "renderArm", at = @At("STORE"), ordinal = 0)
    private Identifier injected(Identifier x) {
    	if(FadenConfig.ENABLE_PLAYER_RACE_SKINS) {
    		ClientPlayerEntity player = MinecraftClient.getInstance().player;
    		if(ClientRaceSkinCache.getPlayerSkins().containsKey(player.getUuid())) {
    			return ClientRaceSkinCache.getPlayerSkins().get(player.getUuid());
    		}
    	}
        return x;
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