package json.jayson.faden.core.client.mixin;

import json.jayson.faden.core.client.interfaces.IModelTransformation;
import net.minecraft.client.render.model.json.ModelTransformation;
import net.minecraft.client.render.model.json.Transformation;
import net.minecraft.item.ModelTransformationMode;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ModelTransformation.class)
public class ModelTransformationMixin implements IModelTransformation {

    @Unique
    public Transformation cosmetic;


    @Override
    public Transformation getCosmetic() {
        return cosmetic;
    }


    @Inject(at = @At("RETURN"), method = "<init>")
    private void init(Transformation thirdPersonLeftHand, Transformation thirdPersonRightHand, Transformation firstPersonLeftHand, Transformation firstPersonRightHand, Transformation head, Transformation gui, Transformation ground, Transformation fixed, CallbackInfo ci) {
        if(renderMode instanceof IModelTransformation modelTransformation) {
            this.cosmetic = modelTransformation.getCosmetic();
        }
    }

    @Override
    public void setCosmetic(Transformation cos) {
        this.cosmetic = cos;
    }
}
