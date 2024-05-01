package net.fuchsia.client.mixin;

import com.mojang.datafixers.util.Pair;
import net.fuchsia.ClothSlot;
import net.fuchsia.IClothInventory;
import net.fuchsia.ISlot;
import net.fuchsia.common.objects.item.Cloth;
import net.fuchsia.util.FadenIdentifier;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.PlayerScreenHandler;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.collection.DefaultedList;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InventoryScreen.class)
public abstract class PlayerInventoryScreenMixin extends AbstractInventoryScreen<PlayerScreenHandler> implements RecipeBookProvider  {

    private static Identifier CLOTH_BUTTON = FadenIdentifier.create("textures/gui/cloth_button.png");
    private static Identifier CLOTH_BUTTON_SELECTED = FadenIdentifier.create("textures/gui/cloth_button_hovered.png");
    boolean selected = false;
    boolean clothEnabled = false;

    public PlayerInventoryScreenMixin(PlayerScreenHandler screenHandler, PlayerInventory playerInventory, Text text) {
        super(screenHandler, playerInventory, text);
    }

    @Inject(method = "<init>*", at = @At("TAIL"))
    public void constructorHead(PlayerEntity player, CallbackInfo ci) {
        for (int i = 46; i < handler.slots.size(); i++) {
            ISlot slot = (ISlot) handler.slots.get(i);
            slot.setEnabled(false);
        }

        for (int i = 5; i < 9; i++) {
            ISlot slot = (ISlot) handler.slots.get(i);
            slot.setEnabled(true);
        }

    }


    @Inject(at = @At("TAIL"), method = "render")
    private void render(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        selected = false;
        int x = context.getScaledWindowWidth() / 2 - 12;
        int y = context.getScaledWindowHeight() / 2 - 75;
        if(mouseX >= x && mouseX < x + 16 && mouseY >= y && mouseY < y + 16) {
            selected = true;
        }
        context.drawTexture(selected ? CLOTH_BUTTON_SELECTED : CLOTH_BUTTON, x, y, 0, 0, 16, 16,16, 16);
    }

    @Inject(at = @At("TAIL"), method = "mouseClicked")
    private void render(double mouseX, double mouseY, int button, CallbackInfoReturnable<Boolean> cir) {
        if(selected) {
           clothEnabled = !clothEnabled;
            for (int i = 5; i < 9; i++) {
                ISlot slot = (ISlot) handler.slots.get(i);
                slot.setEnabled(!clothEnabled);
            }

            for (int i = 46; i < handler.slots.size(); i++) {
                ISlot slot = (ISlot) handler.slots.get(i);
                slot.setEnabled(clothEnabled);
            }
        }
    }
}
