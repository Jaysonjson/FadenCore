package json.jayson.faden.core.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import json.jayson.faden.core.client.screen.CapeSelectScreen;
import json.jayson.faden.core.common.cape.FadenCoreCape;
import json.jayson.faden.core.util.FadenCoreCapeUtil;
import json.jayson.faden.core.config.FadenCoreOptions;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.CapeFeatureRenderer;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.client.util.math.MatrixStack;

@Mixin(CapeFeatureRenderer.class)
public class CapeFeatureRendererMixin {

    @ModifyVariable(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/network/AbstractClientPlayerEntity;FFFFFF)V", at = @At("STORE"), ordinal = 0)
    private VertexConsumer injected(VertexConsumer x, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g, float h, float j, float k, float l) {

        if(CapeSelectScreen.PRE_SELECTED_CAPE != null) {
            return vertexConsumerProvider.getBuffer(RenderLayer.getEntitySolid(CapeSelectScreen.PRE_SELECTED_CAPE.getTexture()));
        }

        if(FadenCoreOptions.getConfig().CUSTOM_CAPES) {
            FadenCoreCape cape = FadenCoreCapeUtil.getCapeForPlayer(abstractClientPlayerEntity.getUuid());
            if (cape != null) {
                return vertexConsumerProvider.getBuffer(RenderLayer.getEntitySolid(cape.getTexture()));
            }
        }
        return x;
    }


    @ModifyVariable(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/network/AbstractClientPlayerEntity;FFFFFF)V", at = @At("STORE"), ordinal = 0)
    private SkinTextures injected(SkinTextures x, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g, float h, float j, float k, float l) {
        if(CapeSelectScreen.PRE_SELECTED_CAPE != null) {
            return new SkinTextures(CapeSelectScreen.PRE_SELECTED_CAPE.getTexture(), null, CapeSelectScreen.PRE_SELECTED_CAPE.getTexture(), null, null, false);
        }

        if(FadenCoreOptions.getConfig().CUSTOM_CAPES) {
            FadenCoreCape cape = FadenCoreCapeUtil.getCapeForPlayer(abstractClientPlayerEntity.getUuid());
            if (cape != null) {
                return new SkinTextures(cape.getTexture(), null, cape.getTexture(), null, null, false);
            }
        }
        return x;
    }


}
