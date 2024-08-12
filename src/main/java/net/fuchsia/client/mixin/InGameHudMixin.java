package net.fuchsia.client.mixin;

import net.fuchsia.config.FadenOptions;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public class InGameHudMixin {

    InGameHud hud = (InGameHud) (Object)this;

    /*@ModifyVariable(method = "renderArmor", at = @At(value = "HEAD"), argsOnly = true, ordinal = 4)
    private static int renderArmor(int value) {
        if(FadenOptions.getConfig().FADEN_HEALTH) {
            return value - 10;
        }
        return value;
    }*/

    @Inject(at = @At("HEAD"), method = "renderHealthBar", cancellable = true)
    public void renderHealthBar(DrawContext context, PlayerEntity player, int x, int y, int lines, int regeneratingHeartIndex, float maxHealth, int lastHealth, int health, int absorption, boolean blinking, CallbackInfo ci) {
        if(FadenOptions.getConfig().FADEN_HEALTH) {
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "renderFood", cancellable = true)
    public void renderHealthBar(DrawContext context, PlayerEntity player, int top, int left, CallbackInfo ci) {
        if(FadenOptions.getConfig().FADEN_HEALTH) {
            ci.cancel();
        }
    }

    @Inject(at = @At("HEAD"), method = "renderArmor", cancellable = true)
    private static void renderArmor(DrawContext context, PlayerEntity player, int i, int j, int k, int x, CallbackInfo ci) {
        if(FadenOptions.getConfig().FADEN_HEALTH) {
            ci.cancel();
        }
    }


}
