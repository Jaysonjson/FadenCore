package json.jayson.faden.core.client.mixin;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import json.jayson.faden.core.client.interfaces.IModelTransformation;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.render.model.json.Transformation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Type;

@Mixin(ModelTransformation.Deserializer.class)
public abstract class ModelTransformationDeserializerMixin {

    @Inject(at = @At("RETURN"), method = "deserialize(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lnet/minecraft/client/render/model/json/ModelTransformation;", cancellable = true)
    private void deseralize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext, CallbackInfoReturnable<ModelTransformation> cir) {
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        ModelTransformation modelTransformation = cir.getReturnValue();
        if(modelTransformation instanceof IModelTransformation modelTransformation1) {
            modelTransformation1.setCosmetic(jsonObject.has("cosmetic") ? jsonDeserializationContext.deserialize(jsonObject.get("cosmetic"), Transformation.class) : Transformation.IDENTITY);
        }
        cir.setReturnValue(modelTransformation);
    }
}
