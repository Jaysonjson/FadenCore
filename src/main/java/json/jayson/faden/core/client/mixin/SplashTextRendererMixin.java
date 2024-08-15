package json.jayson.faden.core.client.mixin;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RotationAxis;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(SplashTextRenderer.class)
public class SplashTextRendererMixin {

    @Inject(method = "render", at = @At(value = "HEAD"), cancellable = true)
    public void render(DrawContext context, int screenWidth, TextRenderer textRenderer, int alpha, CallbackInfo ci) {
        context.getMatrices().push();
        context.getMatrices().translate((float)screenWidth / 2.0F + 97.0F, 69.0F, 0.0F);
        context.getMatrices().multiply(RotationAxis.POSITIVE_Z.rotationDegrees(-20.0F));
        float f = 1.8F - MathHelper.abs(MathHelper.sin((float)(Util.getMeasuringTimeMs() % 1000L) / 1000.0F * 6.2831855F) * 0.1F);
        f = f * 100.0F / (float)(textRenderer.getWidth(((SplashTextRenderer) ((Object) this)).text) + 50);
        context.getMatrices().scale(f, f, f);
        context.drawCenteredTextWithShadow(textRenderer, ((SplashTextRenderer) ((Object) this)).text, 0, -8, 16776960 | alpha);
        context.getMatrices().pop();
        ci.cancel();
    }

}
