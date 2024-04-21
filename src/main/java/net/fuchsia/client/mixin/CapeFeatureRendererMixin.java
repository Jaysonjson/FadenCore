package net.fuchsia.client.mixin;

import net.fuchsia.util.FadenIdentifier;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.CapeFeatureRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(CapeFeatureRenderer.class)
public class CapeFeatureRendererMixin {

    @ModifyVariable(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/client/network/AbstractClientPlayerEntity;FFFFFF)V", at = @At("STORE"), ordinal = 0)
    private VertexConsumer injected(VertexConsumer x, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, AbstractClientPlayerEntity abstractClientPlayerEntity, float f, float g, float h, float j, float k, float l) {
        //TODO: CODE FOR CAPES
        if(abstractClientPlayerEntity.getUuid() == null) {
            return vertexConsumerProvider.getBuffer(RenderLayer.getEntitySolid(FadenIdentifier.create("cape/fuchsia")));
        }
        return x;
    }


}
