package net.fuchsia.client.mixin;

import net.fuchsia.common.race.data.ClientRaceCache;
import net.fuchsia.common.race.data.RaceData;
import net.fuchsia.config.FadenConfig;
import net.fuchsia.config.FadenOptions;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.CraftingScreen;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(CraftingScreen.class)
public class CraftingTableScreenMixin extends Screen {

    protected CraftingTableScreenMixin(Text title) {
        super(title);
    }

    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    private void size(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        if(FadenOptions.getConfig().VANILLA_BLUR) {
            applyBlur(delta);
        }
    }

}
