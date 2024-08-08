package net.fuchsia.client.render.feature;

import net.fuchsia.common.race.cosmetic.RaceCosmetic;
import net.fuchsia.common.race.cosmetic.RaceCosmeticSlot;
import net.fuchsia.server.PlayerData;
import net.fuchsia.server.client.ClientPlayerDatas;
import net.fuchsia.util.FadenRenderUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;

public class ChestFeatureRenderer extends FeatureRenderer<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> {
    public ChestFeatureRenderer(FeatureRendererContext<AbstractClientPlayerEntity, PlayerEntityModel<AbstractClientPlayerEntity>> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, AbstractClientPlayerEntity entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if(!entity.isInvisible()) {
            PlayerData data = ClientPlayerDatas.getPlayerData(entity.getUuid());
            if (data != null && data.getRaceSaveData().getRace() != null) {
                for (RaceCosmetic cosmetic : data.getRaceSaveData().getRace().getCosmeticPalette().getCosmetics(data.getRaceSaveData().getRaceSub())) {
                    if (cosmetic.getSlot() == RaceCosmeticSlot.CHEST && data.getRaceSaveData().getCosmetics().getChest().contains(cosmetic.getId())) {
                        matrices.push();
                        BakedModel model = MinecraftClient.getInstance().getBakedModelManager().getModel(cosmetic.getModel());
                        if(!entity.isSneaking()) {
                            model.getTransformation().getTransformation(ModelTransformationMode.HEAD).apply(false, matrices);
                            matrices.translate(-0.31, 0.82f, 0.45);
                            matrices.scale(0.625F, -0.625F, -0.625F);
                        } else {
                            if(entity.isOnGround()) {
                                model.getTransformation().getTransformation(ModelTransformationMode.HEAD).apply(false, matrices);
                                matrices.translate(-0.31, 0.82f, 0.45);
                                matrices.scale(0.625F, -0.625F, -0.625F);
                                matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(25));
                                matrices.translate(0, 0.11, -0.32);
                            } else {
                                model.getTransformation().getTransformation(ModelTransformationMode.HEAD).apply(false, matrices);
                                matrices.translate(-0.31, 0.82f, 0.45);
                                matrices.scale(0.625F, -0.625F, -0.625F);
                            }
                        }
                        FadenRenderUtil.renderBakedModel(matrices, vertexConsumers, model, (int) (light * 0.5f));
                        matrices.pop();
                    }
                }
            }
        }
    }
}
