package json.jayson.faden.core.client.render.entity;

import json.jayson.faden.core.client.PlayerModelCache;
import json.jayson.faden.core.client.render.feature.player.ChestFeatureRenderer;
import json.jayson.faden.core.client.render.feature.player.HeadFeatureRenderer;
import json.jayson.faden.core.common.npc.entity.NPCEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;
import org.joml.Vector3f;

public class NPCEntityRenderer extends LivingEntityRenderer<NPCEntity, PlayerEntityModel<NPCEntity>> {

    /* NPCS need both and cant use the model cache, when the player sneaks, the NPC sneaks */
    PlayerEntityModel slimModel = PlayerModelCache.makeSlimModel();
    PlayerEntityModel wideModel = PlayerModelCache.makeWideModel();

    public NPCEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx, PlayerModelCache.slimModel, 0.5f);
        addFeature(new ChestFeatureRenderer(this));
        addFeature(new HeadFeatureRenderer(this));
    }

    @Override
    public Identifier getTexture(NPCEntity entity) {
        if(entity.getNpc() != null) {
            return entity.getNpc().getTexture().getLocation();
        }
        return Identifier.of("missing");
    }

    @Override
    public void render(NPCEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        Vector3f scale = new Vector3f(0.9375f, 0.9375f, 0.9375f);
        if(livingEntity.getNpc() != null) {
            model = livingEntity.getNpc().getTexture().isSlim() ? slimModel : wideModel;
            if(livingEntity.getNpc().getRace().isPresent()) {
                scale = livingEntity.getNpc().getRace().get().getSize();
            }
        }

        if(model != null) {
            matrixStack.push();
            matrixStack.scale(scale.x, scale.y, scale.z);
            super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
            matrixStack.pop();
        }
    }
}
