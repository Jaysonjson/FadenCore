package net.fuchsia.client.render.feature;

import com.google.common.collect.Maps;
import net.fuchsia.common.objects.item.ClothItem;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.client.render.*;
import net.minecraft.client.render.entity.feature.FeatureRenderer;
import net.minecraft.client.render.entity.feature.FeatureRendererContext;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.client.render.model.BakedModelManager;
import net.minecraft.client.texture.Sprite;
import net.minecraft.client.texture.SpriteAtlasTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.DyedColorComponent;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.item.trim.ArmorTrim;
import net.minecraft.item.trim.ArmorTrimPattern;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;

import java.util.Iterator;
import java.util.Map;

public class ClothFeatureRenderer <T extends LivingEntity, M extends BipedEntityModel<T>, A extends BipedEntityModel<T>> extends FeatureRenderer<T, M> {
    private A innerModel;

    //USING THE DEFAULT PLAYER MODEL AND JUST RE-RENDERING IT IS SO STUPID, BUT IT WORKS LMAO
    public ClothFeatureRenderer(FeatureRendererContext<T, M> context, A innerModel, BakedModelManager bakery) {
        super(context);
        this.innerModel = innerModel;
    }

    public void render(MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i, T livingEntity, float f, float g, float h, float j, float k, float l) {
        this.renderArmor(matrixStack, vertexConsumerProvider, livingEntity, EquipmentSlot.CHEST, i);
        this.renderArmor(matrixStack, vertexConsumerProvider, livingEntity, EquipmentSlot.LEGS, i);
        this.renderArmor(matrixStack, vertexConsumerProvider, livingEntity, EquipmentSlot.FEET, i);
        this.renderArmor(matrixStack, vertexConsumerProvider, livingEntity, EquipmentSlot.HEAD, i);
    }

    private void renderArmor(MatrixStack matrices, VertexConsumerProvider vertexConsumers, T entity, EquipmentSlot armorSlot, int light) {
        ItemStack itemStack = entity.getEquippedStack(armorSlot);
        Item item = itemStack.getItem();
        if (item instanceof ClothItem clothItem) {
            this.getContextModel().copyBipedStateTo(innerModel);
            this.setVisible(innerModel, armorSlot);
            this.renderArmorParts(matrices, vertexConsumers, light, innerModel, 1, 1, 1, ArmorMaterials.GOLD.value().layers().get(0).getTexture(false));
        }
    }

    public void setInnerModel(A model) {
        this.innerModel = model;
    }

    protected void setVisible(A bipedModel, EquipmentSlot slot) {
        bipedModel.setVisible(false);
        switch (slot) {
            case HEAD:
                bipedModel.head.visible = true;
                bipedModel.hat.visible = true;
                break;
            case CHEST:
                bipedModel.body.visible = true;
                bipedModel.rightArm.visible = true;
                bipedModel.leftArm.visible = true;
                break;
            case LEGS:
                bipedModel.body.visible = true;
                bipedModel.rightLeg.visible = true;
                bipedModel.leftLeg.visible = true;
                break;
            case FEET:
                bipedModel.rightLeg.visible = true;
                bipedModel.leftLeg.visible = true;
        }

    }

    private void renderArmorParts(MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light, A model, float red, float green, float blue, Identifier overlay) {
        VertexConsumer vertexConsumer = vertexConsumers.getBuffer(RenderLayer.getArmorCutoutNoCull(overlay));
        matrices.push();
        matrices.scale(1.025f, 1.025f, 1.025f);
        model.render(matrices, vertexConsumer, light, OverlayTexture.DEFAULT_UV, red, green, blue, 1.0F);
        matrices.pop();
    }

    private A getModel() {
        return this.innerModel;
    }

    private boolean usesInnerModel(EquipmentSlot slot) {
        return slot == EquipmentSlot.LEGS;
    }
}
