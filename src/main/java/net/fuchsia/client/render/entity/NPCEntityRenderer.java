package net.fuchsia.client.render.entity;

import net.fuchsia.client.PlayerModelCache;
import net.fuchsia.client.render.feature.player.ChestFeatureRenderer;
import net.fuchsia.client.render.feature.player.HeadFeatureRenderer;
import net.fuchsia.common.npc.NPCEntity;
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
            return entity.getNpc().getTexture().getTexture();
        }
        return Identifier.of("missing");
    }

    @Override
    public void render(NPCEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        Vector3f scale = new Vector3f(0.9375f, 0.9375f, 0.9375f);
        if(livingEntity.getNpc() != null) {
            model = livingEntity.getNpc().getTexture().isSlim() ? slimModel : wideModel;
            scale = livingEntity.getNpc().getRace().size();
        }

        if(model != null) {
            matrixStack.push();
            matrixStack.scale(scale.x, scale.y, scale.z);
            super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
            matrixStack.pop();
        }
    }
}
