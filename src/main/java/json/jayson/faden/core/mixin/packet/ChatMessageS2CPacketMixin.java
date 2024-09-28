package json.jayson.faden.core.mixin.packet;

import json.jayson.faden.core.mixin_interfaces.IChatMessagePacket;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.network.RegistryByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(net.minecraft.network.packet.s2c.play.ChatMessageS2CPacket.class)
public class ChatMessageS2CPacketMixin implements IChatMessagePacket {

    @Unique
    public String chatType = "";

    @Inject(method = "<init>*", at = @At("TAIL"))
    public void constructorTail(RegistryByteBuf buf, CallbackInfo ci) {
        this.chatType = buf.readString();
    }

    @Inject(method = "write", at = @At("TAIL"))
    public void write(RegistryByteBuf buf, CallbackInfo ci) {
        buf.writeString("test");
    }

    @Override
    public String getChatType() {
        return chatType;
    }
}
