package json.jayson.faden.core.client.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.LogoDrawer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.client.gui.LogoDrawer.*;

@Mixin(LogoDrawer.class)
public class LogoDrawerMixin {

    @Inject(method = "draw(Lnet/minecraft/client/gui/DrawContext;IFI)V", at = @At(value = "HEAD"), cancellable = true)
    public void render(DrawContext context, int screenWidth, float alpha, int y, CallbackInfo ci) {
        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.enableBlend();
        int i = screenWidth / 2 - 128;
        context.drawTexture(LOGO_TEXTURE, i, y, 0.0F, 0.0F, 256, 44, 256, 58);
        int j = screenWidth / 2 - 64;
        int k = y + 44 - 7;
        context.drawTexture(EDITION_TEXTURE, j, k, 0.0F, 0.0F, 128, 14, 128, 16);
        context.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.disableBlend();
        ci.cancel();
    }


}
