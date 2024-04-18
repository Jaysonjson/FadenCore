package net.fuchsia.client.mixin;

import net.fuchsia.client.render.feature.ChestFeatureRenderer;
import net.fuchsia.config.FadenConfig;
import net.fuchsia.common.race.skin.client.ClientRaceSkinCache;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.render.entity.LivingEntityRenderer;
import net.minecraft.client.render.entity.PlayerEntityRenderer;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PlayerEntityRenderer.class)
public abstract class PlayerEntityRendererMixin {
    private PlayerEntityRenderer renderer = ((PlayerEntityRenderer) ((Object) this));

    @Inject(method = "<init>*", at = @At("TAIL"))
    public void constructorHead(EntityRendererFactory.Context ctx, boolean slim, CallbackInfo ci) {
        renderer.addFeature(new ChestFeatureRenderer(renderer));
    }

    @Inject(at = @At("HEAD"), method = "getTexture(Lnet/minecraft/client/network/AbstractClientPlayerEntity;)Lnet/minecraft/util/Identifier;", cancellable = true)
    private void getTextureAbstractPlayer(AbstractClientPlayerEntity abstractClientPlayerEntity, CallbackInfoReturnable<Identifier> cir) {
        if(FadenConfig.ENABLE_PLAYER_RACE_SKINS) {
        	if(ClientRaceSkinCache.getPlayerSkins().containsKey(abstractClientPlayerEntity.getUuid())) {
        		cir.setReturnValue(ClientRaceSkinCache.getPlayerSkins().get(abstractClientPlayerEntity.getUuid()));
        	}
        }
    }

    @ModifyVariable(method = "renderArm", at = @At("STORE"), ordinal = 0)
    private Identifier injected(Identifier x) {
    	if(FadenConfig.ENABLE_PLAYER_RACE_SKINS) {
    		ClientPlayerEntity player = MinecraftClient.getInstance().player;
    		if(ClientRaceSkinCache.getPlayerSkins().containsKey(player.getUuid())) {
    			return ClientRaceSkinCache.getPlayerSkins().get(player.getUuid());
    		}
    	}
        return x;
    }
}