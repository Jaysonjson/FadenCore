package json.jayson.faden.core.client.mixin;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import json.jayson.faden.core.client.interfaces.IModelTransformation;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.render.model.json.Transformation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.lang.reflect.Type;

@Mixin(ModelTransformation.class)
public class ModelTransformationMixin implements IModelTransformation {

    @Unique
    public Transformation cosmetic;


    @Override
    public Transformation getCosmetic() {
        return cosmetic;
    }


    @Inject(at = @At("RETURN"), method = "<init>(Lnet/minecraft/client/render/model/json/ModelTransformation;)V")
    private void init(ModelTransformation other, CallbackInfo ci) {
        if(other instanceof IModelTransformation modelTransformation) {
            this.cosmetic = modelTransformation.getCosmetic();
        }
    }

    @Override
    public void setCosmetic(Transformation cos) {
        this.cosmetic = cos;
    }
}
