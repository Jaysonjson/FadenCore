package json.jayson.faden.core.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
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

    @ModifyVariable(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/PlayerEntityRenderState;FF)V", at = @At("STORE"), ordinal = 0)
    private VertexConsumer injected(VertexConsumer consumer, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, PlayerEntityRenderState playerEntityRenderState, float f, float g) {
        if(MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().player.getWorld().getEntityById(playerEntityRenderState.id) instanceof AbstractClientPlayerEntity player) {
            FadenCoreCape cape = FadenCoreCapeUtil.getCapeForPlayer(player.getUuid());
            if (cape != null) {
                return vertexConsumerProvider.getBuffer(RenderLayer.getEntitySolid(cape.getTexture()));
            }
        }
        return consumer;
    }

    @ModifyVariable(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/render/entity/state/PlayerEntityRenderState;FF)V", at = @At("STORE"), ordinal = 0)
    private SkinTextures injected(SkinTextures consumer, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, PlayerEntityRenderState playerEntityRenderState, float f, float g) {
        if(MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().player.getWorld().getEntityById(playerEntityRenderState.id) instanceof AbstractClientPlayerEntity player) {
            FadenCoreCape cape = FadenCoreCapeUtil.getCapeForPlayer(player.getUuid());
            if (cape != null) {
                return new SkinTextures(cape.getTexture(), null, cape.getTexture(), null, null, false);
            }
        }
        return consumer;
    }


}
