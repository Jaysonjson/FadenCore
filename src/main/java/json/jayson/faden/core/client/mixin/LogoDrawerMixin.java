package json.jayson.faden.core.client.mixin;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.LogoDrawer;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.util.math.ColorHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static net.minecraft.client.gui.LogoDrawer.*;

@Mixin(LogoDrawer.class)
public class LogoDrawerMixin {

    @Inject(method = "draw(Lnet/minecraft/client/gui/DrawContext;IFI)V", at = @At(value = "HEAD"), cancellable = true)
    public void render(DrawContext context, int screenWidth, float alpha, int y, CallbackInfo ci) {
        int i = screenWidth / 2 - 128;
        float f = 1.0F;
        int j = ColorHelper.getWhite(f);
        context.drawTexture(RenderLayer::getGuiTextured, LOGO_TEXTURE, i, y, 0.0F, 0.0F, 256, 44, 256, 58);
        int k = screenWidth / 2 - 64;
        int l = y + 44 - 7;
        context.drawTexture(RenderLayer::getGuiTextured, EDITION_TEXTURE,j, k, 0.0F, 0.0F, 128, 14, 128, 16);

        ci.cancel();
    }


}
