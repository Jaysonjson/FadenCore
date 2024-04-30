package net.fuchsia.client.mixin;

import net.fuchsia.util.FadenIdentifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InventoryScreen.class)
public class PlayerInventoryMixin {

    private static Identifier CLOTH_BUTTON = FadenIdentifier.create("gui/cloth_button.png");

    @Inject(at = @At("HEAD"), method = "render", cancellable = true)
    private void render(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        context.drawGuiTexture(CLOTH_BUTTON, 0, 0, 16, 16);
    }

}
