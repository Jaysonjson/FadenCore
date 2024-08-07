package net.fuchsia.client.overlay;

import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.fuchsia.Faden;
import net.fuchsia.common.init.FadenMusicInstances;
import net.fuchsia.common.objects.music_instance.ClientMusicInstance;
import net.fuchsia.common.objects.music_instance.InstrumentedMusic;
import net.fuchsia.config.FadenConfig;
import net.fuchsia.config.FadenOptions;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.render.RenderTickCounter;

public class InstrumentMusicOverlay implements HudRenderCallback {

    public static ClientMusicInstance CLIENT_MUSIC_INSTANCE = null;
    public static float SHOW_TICKS = 0;

    @Override
    public void onHudRender(DrawContext drawContext, RenderTickCounter tickCounter) {
        if(FadenOptions.getConfig().SHOW_PLAYING_MUSIC && CLIENT_MUSIC_INSTANCE != null && 255 >= SHOW_TICKS) {
            InstrumentedMusic music = FadenMusicInstances.getMusic(CLIENT_MUSIC_INSTANCE.getInstance().getMusicId());
            if(music != null) {
                int xLength = Math.max(MinecraftClient.getInstance().textRenderer.getWidth(music.getAuthor()), MinecraftClient.getInstance().textRenderer.getWidth(music.getName())) + 5;
                drawContext.drawText(MinecraftClient.getInstance().textRenderer, music.getAuthor(), drawContext.getScaledWindowWidth() - xLength, 5, 0xFFFFFFFF, true);
                drawContext.drawText(MinecraftClient.getInstance().textRenderer, music.getName(), drawContext.getScaledWindowWidth() - xLength, 15, 0xFFFFFFFF, true);
            }
            SHOW_TICKS += tickCounter.getTickDelta(true);
        }
    }

}
