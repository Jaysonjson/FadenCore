package json.jayson.faden.core.client.mixin;

import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import json.jayson.faden.core.client.PlayerModelCache;
import json.jayson.faden.core.common.cape.FadenCoreCape;
import json.jayson.faden.core.util.FadenCoreCapeUtil;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.ElytraFeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.ElytraEntityModel;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.render.entity.model.EntityModelLoader;
import net.minecraft.client.util.SkinTextures;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;

@Mixin(ElytraFeatureRenderer.class)
public class ElytraFeatureRendererMixin<T extends LivingEntity, M extends EntityModel<T>> {

    @Shadow @Final private ElytraEntityModel<T> elytra;

    @ModifyVariable(method = "render(Lnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;ILnet/minecraft/entity/LivingEntity;FFFFFF)V", at = @At("STORE"), ordinal = 0)
    private SkinTextures injected(SkinTextures x, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, LivingEntity livingEntity, float f, float g, float h, float j, float k, float l) {
        FadenCoreCape cape = FadenCoreCapeUtil.getCapeForPlayer(livingEntity.getUuid());
        if(cape != null) {
            return new SkinTextures(cape.getTexture(), null, cape.getTexture(), cape.getTexture(), null, false);
        }
        return x;
    }

    @Inject(method = "<init>*", at = @At("TAIL"))
    public void constructorHead(FeatureRendererContext context, EntityModelLoader loader, CallbackInfo ci) {
        if(PlayerModelCache.elytraEntityModel == null) {
            PlayerModelCache.elytraEntityModel = elytra;
        }
    }

}
