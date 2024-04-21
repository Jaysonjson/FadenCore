package net.fuchsia.client.render.feature;

import net.fuchsia.common.race.cosmetic.RaceCosmetic;
import net.fuchsia.common.race.cosmetic.RaceCosmeticType;
import net.fuchsia.common.race.data.ClientRaceCache;
import net.fuchsia.common.race.data.RaceData;
import net.fuchsia.util.FadenRenderUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.ModelWithHead;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;

public class HeadFeatureRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public HeadFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if(!entity.isInvisible()) {
            RaceData raceData = ClientRaceCache.get(entity.getUuid());
            if (raceData.getRace() != null) {
                for (RaceCosmetic cosmetic : raceData.getRace().getCosmeticPalette().getCosmetics(raceData.getSubId())) {
                    if (cosmetic.getType() == RaceCosmeticType.HEAD) {
                        matrices.push();
                        BakedModel model = MinecraftClient.getInstance().getBakedModelManager().getModel(cosmetic.getModel());
                        ((ModelWithHead) this.getContextModel()).getHead().rotate(matrices);
                        model.getTransformation().getTransformation(ModelTransformationMode.HEAD).apply(false, matrices);
                        translate(matrices);
                        FadenRenderUtil.renderBakedModel(matrices, vertexConsumers, model, (int) (light * 0.5f));
                        matrices.pop();
                    }
                }
            }
        }
    }


    public static void translate(MatrixStack matrices) {
        matrices.translate(0.31F, -0.99F, -0.30F);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));
        matrices.scale(0.625F, -0.625F, -0.625F);
    }
}

