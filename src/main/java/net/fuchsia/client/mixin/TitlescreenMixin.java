package net.fuchsia.client.mixin;

import net.fuchsia.common.init.FadenSoundEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.sound.SoundSystem;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TitleScreen.class)
public class TitlescreenMixin {
    @Unique
    private static final SoundInstance music = PositionedSoundInstance.music(FadenSoundEvents.FADEN);

    /*
    * TODO: Dont do this, was only for testing, running this in the render tick isnt _maybe_ that good of an idea.
    * */
    @Inject(method = "render", at = @At(value = "TAIL"))
    public void init(CallbackInfo ci) {
        MinecraftClient.getInstance().getSoundManager().stop(PositionedSoundInstance.music(SoundEvents.MUSIC_MENU.value()));
        if(!MinecraftClient.getInstance().getSoundManager().isPlaying(music)) {
            MinecraftClient.getInstance().getSoundManager().play(music);
        }
    }

}
