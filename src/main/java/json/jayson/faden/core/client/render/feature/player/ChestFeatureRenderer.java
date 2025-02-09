package json.jayson.faden.core.client.render.feature.player;

import json.jayson.faden.core.client.interfaces.IModelTransformation;
import json.jayson.faden.core.client.render.feature.player.mixin.PlayerEntityRendererHelper;
import json.jayson.faden.core.common.npc.entity.NPCEntity;
import json.jayson.faden.core.common.race.FadenCoreRace;
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
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.BipedEntityRenderState;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.RotationAxis;

import java.util.ArrayList;

public class ChestFeatureRenderer<S extends BipedEntityRenderState, M extends BipedEntityModel<S>, A extends BipedEntityModel<S>> extends FeatureRenderer<S, M> {


    public ChestFeatureRenderer(FeatureRendererContext<S, M> context) {
        super(context);
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, S state, float limbAngle, float limbDistance) {
        if(!FadenCoreOptions.getConfig().ENABLE_PLAYER_RACE_COSMETICS) return;
        LivingEntity entity = MinecraftClient.getInstance().player.getWorld().getEntityById(state.id);
        MinecraftClient.getInstance().player.getWorld().getEntityById(state);
        if(!entity.isInvisible()) {
            FadenCoreRace fadenCoreRace = null;
            String raceSub = "";
            PlayerData.RaceDataCosmetics raceDataCosmetics = null;
            if(entity instanceof AbstractClientPlayerEntity) {
                PlayerData data = ClientPlayerDatas.getPlayerData(entity.getUuid());
                if (data.getRaceSaveData().hasRace()) {
                    fadenCoreRace = data.getRaceSaveData().getRace();
                    raceSub = data.getRaceSaveData().getRaceSub();
                    raceDataCosmetics = data.getRaceSaveData().getCosmetics();
                }
            } if (entity instanceof NPCEntity npc) {
                if(npc.getNpc() != null) {
                    if(npc.getNpc().getRace().isPresent()) fadenCoreRace = npc.getNpc().getRace().get();
                    raceSub = npc.getNpc().getRaceSub();
                    raceDataCosmetics = npc.getNpc().getRaceCosmetics();
                }
            }

            if (fadenCoreRace != null && raceDataCosmetics != null && !raceSub.isBlank()) {
                ArrayList<RaceCosmetic> cosmetics = fadenCoreRace.getCosmeticPalette().getCosmetics(raceSub);
                for (String s : raceDataCosmetics.getChest()) {
                    RaceCosmetic cosmetic = fadenCoreRace.getCosmeticPalette().getCosmetic(cosmetics, RaceCosmeticSlot.CHEST, s);
                    if (cosmetic == null) continue;
                    matrices.push();
                    BakedModel model = MinecraftClient.getInstance().getBakedModelManager().getModel(cosmetic.getModel());
                    if (!entity.isSneaking()) {
                        if(model.getTransformation() instanceof IModelTransformation transformation && transformation.getCosmetic() != null) transformation.getCosmetic().apply(false, matrices);
                        matrices.translate(-0.31, 0.82f, 0.45);
                        matrices.scale(0.625F, -0.625F, -0.625F);
                    } else {
                        if (entity.isOnGround()) {
                            if(model.getTransformation() instanceof IModelTransformation transformation && transformation.getCosmetic() != null) transformation.getCosmetic().apply(false, matrices);
                            matrices.translate(-0.31, 0.82f, 0.45);
                            matrices.scale(0.625F, -0.625F, -0.625F);
                            matrices.multiply(RotationAxis.POSITIVE_X.rotationDegrees(25));
                            matrices.translate(0, 0.11, -0.32);
                        } else {
                            if(model.getTransformation() instanceof IModelTransformation transformation && transformation.getCosmetic() != null) transformation.getCosmetic().apply(false, matrices);
                            matrices.translate(-0.31, 0.82f, 0.45);
                            matrices.scale(0.625F, -0.625F, -0.625F);
                        }
                    }
                    FadenCoreRenderUtil.renderBakedModel(matrices, vertexConsumers, model, (int) (light * 0.5f));
                    matrices.pop();
                }
            }
        }
    }
}
