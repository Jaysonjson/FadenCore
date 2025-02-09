package json.jayson.faden.core.client.render.feature.player.mixin;

import json.jayson.faden.core.common.cloth.FadenCoreCloth;
import json.jayson.faden.core.common.objects.item.IClothItem;
import json.jayson.faden.core.common.slot.ClothSlot;
import json.jayson.faden.core.mixin_interfaces.IClothInventory;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.OverlayTexture;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.model.PlayerEntityModel;
import net.minecraft.client.render.entity.state.PlayerEntityRenderState;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class PlayerEntityRendererHelper {

    public static void renderArms(ModelPart arm, boolean slim, PlayerEntityModel playerEntityModel, MatrixStack matrices, VertexConsumerProvider vertexConsumers, int light) {
        IClothInventory playerInventory = (IClothInventory) MinecraftClient.getInstance().player.getInventory();
        ItemStack itemStack = playerInventory.getClothOrArmor(EquipmentSlot.CHEST, ClothSlot.CHEST);
        Item item = itemStack.getItem();
        FadenCoreCloth cloth = null;
        if (item instanceof IClothItem clothItem) cloth = clothItem.getCloth();
        if(cloth != null) {
            arm.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityCutout(slim ? cloth.getTexture().getLeft() : cloth.getTexture().getRight())), light, OverlayTexture.DEFAULT_UV);
            playerEntityModel.rightSleeve.visible = true;
            playerEntityModel.rightSleeve.render(matrices, vertexConsumers.getBuffer(RenderLayer.getEntityCutout(slim ? cloth.getTexture().getLeft() : cloth.getTexture().getRight())), light, OverlayTexture.DEFAULT_UV);
            playerEntityModel.rightSleeve.visible = cloth.renderDefaultSecondLayer();
        }
    }

    public static void hideSecondLayer(PlayerEntityRenderState playerEntityRenderState, PlayerEntityModel model) {
        AbstractClientPlayerEntity player = getPlayer(playerEntityRenderState);
        if(player == null) return;
        IClothInventory playerInventory = (IClothInventory) player.getInventory();
        ItemStack itemStack = playerInventory.getClothOrArmor(EquipmentSlot.CHEST, ClothSlot.CHEST);
        Item item = itemStack.getItem();
        if (item instanceof IClothItem clothItem) {
            FadenCoreCloth cloth = clothItem.getCloth();
            if(!cloth.renderDefaultSecondLayer()) {
                model.leftSleeve.visible = false;
                model.rightSleeve.visible = false;
                model.jacket.visible = false;
                model.rightPants.visible = false;
                model.leftPants.visible = false;
            }
        }
    }

    @Nullable
    public static AbstractClientPlayerEntity getPlayer(PlayerEntityRenderState playerEntityRenderState) {
        if (MinecraftClient.getInstance().player != null && MinecraftClient.getInstance().player.getWorld().getEntityById(playerEntityRenderState.id) instanceof AbstractClientPlayerEntity player) return player;
        return null;
    }
}
