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
import net.minecraft.client.render.model.json.Transformation;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;

public class HeadFeatureRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public HeadFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        RaceData raceData = ClientRaceCache.get(entity.getUuid());
        if(raceData.getRace() != null) {
            for (RaceCosmetic cosmetic : raceData.getRace().getCosmeticPalette().getCosmetics(raceData.getSubId())) {
                if(cosmetic.getType() == RaceCosmeticType.HEAD) {
                    matrices.push();
                    BakedModel model = MinecraftClient.getInstance().getBakedModelManager().getModel(cosmetic.getModel());
                    ((ModelWithHead) this.getContextModel()).getHead().rotate(matrices);
                    Transformation head = model.getTransformation().head;
                    matrices.translate(-head.translation.x + 0.63, -head.translation.y - 0.42f, -head.translation.z - 1);
                    matrices.multiply(RotationAxis.of(model.getTransformation().head.rotation).rotationDegrees(0));
                    matrices.scale(head.scale.x - 0.5f, head.scale.y - 0.5f, head.scale.z - 0.5f);
                    matrices.multiply(RotationAxis.POSITIVE_Z.rotationDegrees(180));
                    FadenRenderUtil.renderBakedModel(matrices, vertexConsumers, model, light);
                }
            }
        }
    }
}

