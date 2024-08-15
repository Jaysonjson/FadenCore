package net.fuchsia.client.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fuchsia.config.FadenCoreOptions;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.CraftingScreen;
import net.minecraft.text.Text;

@Mixin(CraftingScreen.class)
public class CraftingTableScreenMixin extends Screen {

    protected CraftingTableScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    private void size(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if(FadenCoreOptions.getConfig().VANILLA_BLUR) {
            applyBlur(delta);
        }
    }

}
