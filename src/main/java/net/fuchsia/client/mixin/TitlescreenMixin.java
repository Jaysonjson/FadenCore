package net.fuchsia.client.mixin;

import net.fuchsia.client.FadenClient;
import net.minecraft.client.gui.screen.SplashTextRenderer;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fuchsia.common.init.FadenSoundEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;

import java.util.Random;

@Mixin(TitleScreen.class)
public class TitlescreenMixin {
    @Shadow @Nullable private SplashTextRenderer splashText;
    @Unique
    private static final SoundInstance music = new PositionedSoundInstance(FadenSoundEvents.FADEN.getId(), SoundCategory.MUSIC, 0.1F, 1.0F, SoundInstance.createRandom(), true, 0, SoundInstance.AttenuationType.NONE, 0.0, 0.0, 0.0, true);

    @Unique
    public boolean splashSet = false;


    @Inject(method = "init", at = @At(value = "TAIL"))
    public void init(CallbackInfo ci) {
        if(!splashSet) {
            splashText = new SplashTextRenderer(FadenClient.SPLASHES.get(new Random().nextInt(FadenClient.SPLASHES.size())));
            splashSet = true;
        }
    }


    @Inject(method = "tick", at = @At(value = "TAIL"))
    public void tick(CallbackInfo ci) {
        if(!MinecraftClient.getInstance().getSoundManager().isPlaying(music)) {
            MinecraftClient.getInstance().getSoundManager().play(music);
        }
    }

}
