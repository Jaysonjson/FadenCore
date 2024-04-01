package json.jayson.client.mixin;

import json.jayson.common.init.FadenItems;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.tooltip.TooltipBackgroundRenderer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(value = TooltipBackgroundRenderer.class, priority = 1001)
public class TooltipBackgroundRendererMixin {

    @Inject(at = @At("HEAD"), method = "render")
    private static void render(DrawContext context, int x, int y, int width, int height, int z, CallbackInfo ci) {
        System.out.println("YES");
        ci.cancel();
    }


    @Inject(at = @At("HEAD"), method = "renderBorder")
    private static void render2(DrawContext context, int x, int y, int width, int height, int z, int startColor, int endColor, CallbackInfo ci) {
        System.out.println("YES");
        ci.cancel();
    }
}
