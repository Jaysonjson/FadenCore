package json.jayson.client.mixin;

import json.jayson.skin.client.ClientSkinCache;
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
        if(ClientSkinCache.getClientSkins().containsKey(abstractClientPlayerEntity.getUuid())) {
            cir.setReturnValue(ClientSkinCache.getClientSkins().get(abstractClientPlayerEntity.getUuid()));
        }
    }

    @ModifyVariable(method = "renderArm", at = @At("STORE"), ordinal = 0)
    private Identifier injected(Identifier x) {
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
        if(ClientSkinCache.getClientSkins().containsKey(player.getUuid())) {
            return ClientSkinCache.getClientSkins().get(player.getUuid());
        }
        return x;
    }
}