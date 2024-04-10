package net.fuchsia.client.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundManager;
import net.minecraft.sound.SoundEvents;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(SoundManager.class)
public class SoundManagerMixin {
    @Inject(at = @At("HEAD"), method = "play(Lnet/minecraft/client/sound/SoundInstance;)V", cancellable = true)
    private void injectPlayDirect(SoundInstance sound, CallbackInfo ci) {
        if(MinecraftClient.getInstance().currentScreen instanceof TitleScreen && sound.getId().equals(SoundEvents.MUSIC_MENU.registryKey().getValue())) ci.cancel();
    }

    @Inject(at = @At("HEAD"), method = "play(Lnet/minecraft/client/sound/SoundInstance;I)V", cancellable = true)
    private void injectPlayDelayed(SoundInstance sound, int delay, CallbackInfo ci) {
        if(MinecraftClient.getInstance().currentScreen instanceof TitleScreen && sound.getId().equals(SoundEvents.MUSIC_MENU.registryKey().getValue())) ci.cancel();
    }
}