package net.fuchsia.client.overlay;

import com.mojang.blaze3d.systems.RenderSystem;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fuchsia.common.race.data.ClientRaceCache;
import net.fuchsia.common.race.data.RaceData;
import net.fuchsia.config.FadenOptions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.item.CompassAnglePredicateProvider;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.item.CompassItem;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class StatsOverlay implements HudRenderCallback {

    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {
        if(FadenOptions.getConfig().FADEN_HEALTH && MinecraftClient.isHudEnabled()) {
            ClientPlayerEntity player = MinecraftClient.getInstance().player;
            RaceData data = ClientRaceCache.getCache().getOrDefault(player.getUuid(), null);
            if(data != null && data.getRace() != null && data.getRace().getIcon() != null) {
                drawContext.drawGuiTexture(data.getRace().getIcon(), 25, 10, 16, 16);
            }

            drawContext.drawTexture(Identifier.of("textures/gui/sprites/hud/armor_full.png"), 25, 22, 0, 0, 4, 4, 4, 4);
            drawContext.drawTexture(Identifier.of("textures/gui/sprites/hud/heart/full.png"), 25, 10, 0, 0, 4, 4, 4, 4);
            drawContext.drawTexture(Identifier.of("textures/gui/sprites/hud/food_full.png"), 25, 16, 0, 0, 4, 4, 4, 4);

            drawBar(drawContext, (int) player.getHealth() * 2, (int) player.getMaxHealth() * 2, 0xABe35146, 10);
            drawBar(drawContext, player.getHungerManager().getFoodLevel() * 2, 20 * 2, 0xABe3a44b, 16);
            drawBar(drawContext, player.getArmor() * 2, 40, 0xABa3a3a3, 22);
            InventoryScreen.drawEntity(drawContext, 3, 1, 25, 35, 15, 0.0625F, 15, MinecraftClient.getInstance().player.getPitch(tickCounter.getTickDelta(true)), MinecraftClient.getInstance().player);

            //drawContext.drawTextWithShadow(MinecraftClient.getInstance().textRenderer, Text.literal("Test"), (int)((double)drawContext.getScaledWindowWidth() / 2 * player.getRotationVector().getZ()), 15, 0xFFFFFFFF);
        }
    }

    public void drawBar(DrawContext drawContext, int current, int max, int color, int y) {
        drawContext.fill(30, y, 30 + max, y + 4, 0xAB454545);
        drawContext.fill(30, y, 30 + current, y + 4, color);
    }
}
