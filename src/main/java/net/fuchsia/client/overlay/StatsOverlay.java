package net.fuchsia.client.overlay;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fuchsia.config.FadenOptions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.item.CompassAnglePredicateProvider;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.item.CompassItem;
import net.minecraft.text.Text;

public class StatsOverlay implements HudRenderCallback {

    @Override
    public void onHudRender(DrawContext drawContext, float tickDelta) {
        if(FadenOptions.getConfig().FADEN_HEALTH) {
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            drawBar(drawContext, (int) player.getHealth() * 2, (int) player.getMaxHealth() * 2, 0xABe35146, 10);
            drawBar(drawContext, player.getHungerManager().getFoodLevel() * 2, 20 * 2, 0xABe3a44b, 15);
            InventoryScreen.drawEntity(drawContext, 3, 1, 25, 35, 15, 0.0625F, 15, MinecraftClient.getInstance().player.getPitch(tickDelta), MinecraftClient.getInstance().player);
            drawContext.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, Text.literal("Test"), (int)((double)drawContext.getScaledWindowWidth() / 2 * player.getRotationVector().getZ()), 15, 0xFFFFFFFF);
            CompassAnglePredicateProvider.CompassTarget
        }
    }

    public void drawBar(DrawContext drawContext, int current, int max, int color, int y) {
        drawContext.fill(25, y, 25 + max, y + 3, 0xAB454545);
        drawContext.fill(25, y, 25 + current, y + 3, color);
    }

    public void renderBlur(float delta) {
        float g = 50f;
        if (MinecraftClient.getInstance().gameRenderer.blurPostProcessor != null && g >= 1.0F) {
            RenderSystem.enableBlend();
            MinecraftClient.getInstance().gameRenderer.blurPostProcessor.setUniforms("Radius", g);
            MinecraftClient.getInstance().gameRenderer.blurPostProcessor.render(delta);
            RenderSystem.disableBlend();
        }
        MinecraftClient.getInstance().getFramebuffer().beginWrite(false);
    }

}
