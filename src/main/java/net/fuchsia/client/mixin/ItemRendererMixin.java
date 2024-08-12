package net.fuchsia.client.mixin;

import net.fuchsia.client.FadenCoreClient;
import net.fuchsia.client.registry.FadenItemModelRegistry;
import net.minecraft.client.render.item.ItemRenderer;
import net.minecraft.client.render.model.BakedModel;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.ModelIdentifier;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(ItemRenderer.class)
public class ItemRendererMixin {

    @ModifyVariable(method = "renderItem(Lnet/minecraft/item/ItemStack;Lnet/minecraft/client/render/model/json/ModelTransformationMode;ZLnet/minecraft/client/util/math/MatrixStack;Lnet/minecraft/client/render/VertexConsumerProvider;IILnet/minecraft/client/render/model/BakedModel;)V", at = @At(value = "HEAD"), argsOnly = true)
    public BakedModel renderItem(BakedModel model, ItemStack itemStack, ModelTransformationMode mode) {
        if(FadenCoreClient.getItemModels().hasModel(itemStack.getItem())) {
            FadenItemModelRegistry.ModelData data = FadenCoreClient.getItemModels().getModel(itemStack.getItem());
            if(mode != data.getMode()) {
                Identifier itemId = Registries.ITEM.getId(itemStack.getItem());
                return ((ItemRendererAccessor) this).faden$getModels().getModelManager().getModel(new ModelIdentifier(Identifier.of(itemId.getNamespace(), data.getPath().isEmpty() ? "model/" + itemId.getPath() : data.getPath()), data.getVariant()));
            }
        }
        return model;
    }
}

