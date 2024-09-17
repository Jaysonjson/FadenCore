package json.jayson.faden.core.client.mixin;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import json.jayson.faden.core.client.interfaces.IModelTransformation;
import net.minecraft.client.render.model.json.JsonUnbakedModel;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.render.model.json.Transformation;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Type;

@Mixin(JsonUnbakedModel.class)
public abstract class JsonUnbakedModelMixin {

    @Shadow protected abstract Transformation getTransformation(ModelTransformationMode renderMode);

    @Shadow @Final private ModelTransformation transformations;

    @Shadow public String id;

    @Shadow @Nullable protected JsonUnbakedModel parent;

    @Inject(at = @At("RETURN"), method = "getTransformations", cancellable = true)
    private void deseralize(CallbackInfoReturnable<ModelTransformation> cir) {
        ModelTransformation modelTransformation = cir.getReturnValue();
        ModelTransformation trs = parent == null ? transformations : parent.getTransformations();
        if(trs instanceof IModelTransformation modelTransformation1) {
            if(modelTransformation instanceof IModelTransformation modelTransformation2) {
                modelTransformation2.setCosmetic(modelTransformation1.getCosmetic());
            }
        }
        cir.setReturnValue(modelTransformation);
    }

}
