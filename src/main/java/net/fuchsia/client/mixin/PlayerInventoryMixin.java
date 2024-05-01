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

    private static Identifier CLOTH_BUTTON = FadenIdentifier.create("textures/gui/cloth_button.png");
    private static Identifier CLOTH_BUTTON_SELECTED = FadenIdentifier.create("textures/gui/cloth_button_hovered.png");
    boolean selected = false;

    @Inject(at = @At("TAIL"), method = "render", cancellable = true)
    private void render(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        selected = false;
        int x = context.getScaledWindowWidth() / 2 - 12;
        int y = context.getScaledWindowHeight() / 2 - 75;
        if(mouseX >= x && mouseX < x + 16 && mouseY >= y && mouseY < y + 16) {
            selected = true;
        }
        context.drawTexture(selected ? CLOTH_BUTTON_SELECTED : CLOTH_BUTTON, x, y, 0, 0, 16, 16,16, 16);
    }

}
