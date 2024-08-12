package net.fuchsia.client.mixin;

import net.fuchsia.mixin_interfaces.ExtraInventory;
import net.fuchsia.common.slot.ISlot;
import net.fuchsia.util.FadenCoreIdentifier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InventoryScreen.class)
public abstract class PlayerInventoryScreenMixin extends AbstractInventoryScreen<PlayerScreenHandler> implements RecipeBookProvider  {

    private static Identifier CLOTH_BUTTON = FadenCoreIdentifier.create("textures/gui/cloth_button.png");
    private static Identifier CLOTH_BUTTON_SELECTED = FadenCoreIdentifier.create("textures/gui/cloth_button_hovered.png");

    private static Identifier GEAR_BUTTON = FadenCoreIdentifier.create("textures/gui/gear_button.png");
    private static Identifier GEAR_BUTTON_SELECTED = FadenCoreIdentifier.create("textures/gui/gear_button_hovered.png");

    boolean clothSelected = false;
    boolean gearSelected = false;
    boolean clothEnabled = false;
    boolean gearEnabled = false;

    public PlayerInventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    @Inject(method = "<init>*", at = @At("TAIL"))
    public void constructorHead(PlayerEntity player, CallbackInfo ci) {
        toggleClothes(false);
        toggleGear(false);
        toggleArmor(true);

    }


    @Inject(at = @At("TAIL"), method = "render")
    private void render(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        clothSelected = false;
        gearSelected = false;
        int x = context.getScaledWindowWidth() / 2 - 11;
        int y = context.getScaledWindowHeight() / 2 - 75;
        if(mouseX >= x && mouseX < x + 16 && mouseY >= y && mouseY < y + 16) {
            clothSelected = true;
            context.drawTooltip(MinecraftClient.getInstance().textRenderer, Text.translatable("tooltip.faden.toggle_cloth"), mouseX, mouseY);
        }

        if(mouseX >= x && mouseX < x + 16 && mouseY >= (y + 16) && mouseY < (y + 32)) {
            gearSelected = true;
            context.drawTooltip(MinecraftClient.getInstance().textRenderer, Text.translatable("tooltip.faden.toggle_gear"), mouseX, mouseY);
        }
        context.drawTexture(clothSelected ? CLOTH_BUTTON_SELECTED : CLOTH_BUTTON, x, y, 0, 0, 16, 16,16, 16);
        context.drawTexture(gearSelected ? GEAR_BUTTON_SELECTED : GEAR_BUTTON, x, y + 16, 0, 0, 16, 16,16, 16);
    }

    @Inject(at = @At("TAIL"), method = "mouseClicked")
    private void mouseClicked(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if(clothSelected || gearSelected) {
            if(clothSelected) {
                clothEnabled = !clothEnabled;
                gearEnabled = false;
            } else {
                gearEnabled = !gearEnabled;
                clothEnabled = false;
            }

            toggleGear(gearEnabled);
            toggleClothes(clothEnabled);
            toggleArmor(!clothEnabled && !gearEnabled);
        }
    }

    public void toggleArmor(boolean bool) {
        for (int i = 5; i < 9; i++) {
            ISlot slot = (ISlot) handler.slots.get(i);
            slot.setEnabled(bool);
        }
    }

    public void toggleClothes(boolean bool) {
        for (int i = ExtraInventory.CLOTH_START; i < ExtraInventory.CLOTH_END; i++) {
            ISlot slot = (ISlot) handler.slots.get(i);
            slot.setEnabled(bool);
        }
    }

    public void toggleGear(boolean bool) {
        for (int i = ExtraInventory.GEAR_START - 1; i < ExtraInventory.GEAR_END - 1; i++) {
            ISlot slot = (ISlot) handler.slots.get(i);
            slot.setEnabled(bool);
        }
    }
}
