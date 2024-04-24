package net.fuchsia.client.mixin;

import net.fuchsia.common.cape.FadenCape;
import net.fuchsia.common.cape.FadenCapes;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ElytraFeatureRenderer.class)
public class ElytraFeatureRendererMixin {

    @ModifyVariable(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V", at = @At("STORE"), ordinal = 0)
    private SkinTextures injected(SkinTextures x, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, LivingEntity livingEntity, float f, float g, float h, float j, float k, float l) {
        FadenCape cape = FadenCapes.getCapeForPlayer(livingEntity.getUuid());
        if(cape != null) {
            return new SkinTextures(cape.getTexture(), null, cape.getTexture(), cape.getTexture(), null, false);
        }
        return x;
    }

}
