package net.fuchsia.client.mixin;

import net.fuchsia.race.skin.client.ClientRaceSkinCache;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin {

    @Inject(at = @At("HEAD"), method = "getTexture(Lnet/minecraft/client/network/AbstractClientPlayerEntity;)Lnet/minecraft/util/Identifier;", cancellable = true)
    private void getTextureAbstractPlayer(AbstractClientPlayerEntity abstractClientPlayerEntity, CallbackInfoReturnable<Identifier> cir) {
        if(ClientRaceSkinCache.getPlayerSkins().containsKey(abstractClientPlayerEntity.getUuid())) {
            cir.setReturnValue(ClientRaceSkinCache.getPlayerSkins().get(abstractClientPlayerEntity.getUuid()));
        }
    }

    @ModifyVariable(method = "renderArm", at = @At("STORE"), ordinal = 0)
    private Identifier injected(Identifier x) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if(ClientRaceSkinCache.getPlayerSkins().containsKey(player.getUuid())) {
            return ClientRaceSkinCache.getPlayerSkins().get(player.getUuid());
        }
        return x;
    }
}