package json.jayson.faden.core.client.mixin;

import json.jayson.faden.core.common.chat.FadenCoreChatType;
import json.jayson.faden.core.mixin_interfaces.IChatMessagePacket;
import json.jayson.faden.core.registry.FadenCoreRegistry;
import json.jayson.faden.core.server.PlayerData;
import json.jayson.faden.core.util.PlayerDataUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.packet.s2c.play.ChatMessageS2CPacket;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {

    @Inject(method = "onChatMessage", at = @At("TAIL"))
    public void write(ChatMessageS2CPacket packet, CallbackInfo ci) {
        if(packet instanceof IChatMessagePacket iChatMessagePacket) {
            if(!iChatMessagePacket.getChatType().isEmpty()) {
                FadenCoreChatType coreChatType = FadenCoreRegistry.CHAT.get(Identifier.of(iChatMessagePacket.getChatType()));
                if(coreChatType != null) {
                    PlayerData data = PlayerDataUtil.getClientOrServer(MinecraftClient.getInstance().player.getUuid());
                    if(data.getChatType().equalsIgnoreCase(coreChatType.getIdentifier().toString())) {
                        data.setChatType("");
                    }
                }
            }
        }
    }

}
