package json.jayson.faden.core.client.render.feature.player;

import json.jayson.faden.core.client.interfaces.IModelTransformation;
import json.jayson.faden.core.common.npc.entity.NPCEntity;
import json.jayson.faden.core.common.race.Race;
import json.jayson.faden.core.common.race.cosmetic.RaceCosmetic;
import json.jayson.faden.core.common.race.cosmetic.RaceCosmeticSlot;
import json.jayson.faden.core.config.FadenCoreOptions;
import json.jayson.faden.core.server.PlayerData;
import json.jayson.faden.core.server.client.ClientPlayerDatas;
import json.jayson.faden.core.util.FadenCoreRenderUtil;
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
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.RotationAxis;

import java.util.ArrayList;

public class HeadFeatureRenderer<T extends LivingEntity> extends FeatureRenderer<T, PlayerEntityModel<T>> {
    public HeadFeatureRenderer(FeatureRendererContext<T, PlayerEntityModel<T>> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, T entity, float limbAngle, float limbDistance, float tickDelta, float animationProgress, float headYaw, float headPitch) {
        if(!FadenCoreOptions.getConfig().ENABLE_PLAYER_RACE_COSMETICS) return;
        if(!entity.isInvisible()) {
            Race race = null;
            String raceSub = "";
            PlayerData.RaceDataCosmetics raceDataCosmetics = null;
            if(entity instanceof AbstractClientPlayerEntity) {
                PlayerData data = ClientPlayerDatas.getPlayerData(entity.getUuid());
                if (data.getRaceSaveData().hasRace()) {
                    race = data.getRaceSaveData().getRace();
                    raceSub = data.getRaceSaveData().getRaceSub();
                    raceDataCosmetics = data.getRaceSaveData().getCosmetics();
                }
            } if (entity instanceof NPCEntity npc) {
                if(npc.getNpc() != null) {
                    if(npc.getNpc().getRace().isPresent()) race = npc.getNpc().getRace().get();
                    raceSub = npc.getNpc().getRaceSub();
                    raceDataCosmetics = npc.getNpc().getRaceCosmetics();
                }
            }

            if (race != null && raceDataCosmetics != null && !raceSub.isBlank()) {
                ArrayList<RaceCosmetic> cosmetics = race.getCosmeticPalette().getCosmetics(raceSub);
                for (String s : raceDataCosmetics.getHead()) {
                    RaceCosmetic cosmetic = race.getCosmeticPalette().getCosmetic(cosmetics, RaceCosmeticSlot.HEAD, s);
                    if (cosmetic == null) continue;
                    matrices.push();
                    BakedModel model = MinecraftClient.getInstance().getBakedModelManager().getModel(cosmetic.getModel());
                    ((ModelWithHead) this.getContextModel()).getHead().rotate(matrices);
                    if(model.getTransformation() instanceof IModelTransformation transformation && transformation.getCosmetic() != null) transformation.getCosmetic().apply(false, matrices);
                    translate(matrices);
                    FadenCoreRenderUtil.renderBakedModel(matrices, vertexConsumers, model, (int) (light * 0.5f));
                    matrices.pop();
                }
            }

        }
    }


    public static void translate(MatrixStack matrices) {
        matrices.translate(0.31F, 0F, -0.30F);
        matrices.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180.0F));
        matrices.scale(0.625F, -0.625F, -0.625F);
    }
}

