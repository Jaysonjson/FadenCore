package net.fuchsia.client.render.feature;

import net.fuchsia.common.slot.ClothSlot;
import net.fuchsia.IClothInventory;
import net.fuchsia.client.IPlayerEntityRenderer;
import net.fuchsia.common.objects.item.cloth.ClothItem;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.util.Identifier;
import org.joml.Vector3f;

public class ClothFeatureRenderer <T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> extends FeatureRenderer<T, M> {

    private IPlayerEntityRenderer playerEntityRenderer;
    //USING THE DEFAULT PLAYER MODEL AND JUST RE-RENDERING IT IS SO STUPID, BUT IT WORKS LMAO
    public ClothFeatureRenderer(FeatureRendererContext<T, M> context, IPlayerEntityRenderer playerEntityRenderer) {
        super(context);
        this.playerEntityRenderer = playerEntityRenderer;
    }

    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
        this.renderArmor(matrixStack, vertexConsumerProvider, livingEntity, EquipmentSlot.HEAD, i);
        this.renderArmor(matrixStack, vertexConsumerProvider, livingEntity, EquipmentSlot.CHEST, i);
        this.renderArmor(matrixStack, vertexConsumerProvider, livingEntity, EquipmentSlot.LEGS, i);
        this.renderArmor(matrixStack, vertexConsumerProvider, livingEntity, EquipmentSlot.FEET, i);
    }

    private void renderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T entity, EquipmentSlot armorSlot, int light) {
        if(entity instanceof PlayerEntity player) {
            IClothInventory playerInventory = (IClothInventory) player.getInventory();
            ItemStack itemStack = playerInventory.getClothOrArmor(armorSlot, ClothSlot.fromEquipment(armorSlot));
            Item item = itemStack.getItem();
            if (item instanceof ClothItem clothItem) {
                PlayerEntityModel playerEntityModel = playerEntityRenderer.getPlayerModel();
                this.getContextModel().copyBipedStateTo(playerEntityModel);
                this.setVisible(playerEntityModel, armorSlot);
                this.renderArmorParts(matrices, vertexConsumers, light, playerEntityModel, 1, 1, 1, playerEntityRenderer.slim() ? clothItem.getTexture() : clothItem.getTextureWide());
            }
        }
    }

    protected void setVisible(PlayerEntityModel bipedModel, EquipmentSlot slot) {
        bipedModel.setVisible(false);
        switch (slot) {
            case HEAD:
                bipedModel.head.visible = true;
                bipedModel.hat.visible = true;
                bipedModel.ear.visible = true;
                break;
            case CHEST:
                bipedModel.body.visible = true;
                bipedModel.rightArm.visible = true;
                bipedModel.leftArm.visible = true;
                bipedModel.rightSleeve.visible = true;
                bipedModel.leftSleeve.visible = true;
                bipedModel.jacket.visible = true;
                bipedModel.cloak.visible = true;
                break;
            case LEGS:
                bipedModel.body.visible = true;
                bipedModel.rightLeg.visible = true;
                bipedModel.leftLeg.visible = true;
                break;
            case FEET:
                bipedModel.rightLeg.visible = true;
                bipedModel.leftLeg.visible = true;
                bipedModel.leftPants.visible = true;
                bipedModel.rightPants.visible = true;
        }
    }

    private void renderArmorParts(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, PlayerEntityModel model, float red, float green, float blue, Identifier overlay) {
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getArmorCutoutNoCull(overlay));
        matrices.push();
        matrices.scale(1.05f, 1.05f, 1.05f);
        matrices.translate(0f, -0.01f, 0.0f);
        model.leftArm.translate(new Vector3f(-0.25f, 0f, 0f));
        model.rightArm.translate(new Vector3f(0.25f, 0f, 0f));
        model.leftSleeve.translate(new Vector3f(-0.25f, 0f, 0f));
        model.rightSleeve.translate(new Vector3f(0.25f, 0f, 0f));
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, red, green, blue, 1.0F);
        matrices.pop();
    }
}
