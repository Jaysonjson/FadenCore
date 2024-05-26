package net.fuchsia.client.render.entity;

import net.fuchsia.Faden;
import net.fuchsia.common.npc.NPCEntity;
import net.fuchsia.common.race.Race;
import net.fuchsia.common.race.RaceSkinMap;
import net.fuchsia.common.race.skin.client.ClientRaceSkinCache;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.model.EntityModelLayers;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.Identifier;

public class NPCEntityRenderer extends LivingEntityRenderer<NPCEntity, PlayerEntityModel<NPCEntity>> {

    public NPCEntityRenderer(EntityRendererFactory.Context ctx, boolean slim) {
        super(ctx, new PlayerEntityModel(ctx.getPart(slim ? EntityModelLayers.PLAYER_SLIM : EntityModelLayers.PLAYER), slim), 0.5f);
    }

    @Override
    public Identifier getTexture(NPCEntity entity) {
        return (Identifier) ClientRaceSkinCache.getSKINS().values().toArray()[Faden.RANDOM.nextInt(ClientRaceSkinCache.getSKINS().values().size())];
    }

    @Override
    public void render(NPCEntity livingEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        matrixStack.push();
        matrixStack.scale(0.94f, 0.94f, 0.94f);
        super.render(livingEntity, f, g, matrixStack, vertexConsumerProvider, i);
        matrixStack.pop();
    }
}
