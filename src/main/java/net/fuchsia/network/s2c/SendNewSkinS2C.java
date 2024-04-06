package net.fuchsia.network.s2c;

import net.fuchsia.skin.client.ClientSkinCache;
import net.fuchsia.skin.client.SkinTexture;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;

import java.util.Base64;
import java.util.UUID;

public class SendNewSkinS2C {

    public static void receive(MinecraftClient client, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf packetByteBuf, PacketSender packetSender) {
        UUID uuid = packetByteBuf.readUuid();
        Identifier identifier = packetByteBuf.readIdentifier();
        SkinTexture skinTexture = new SkinTexture(identifier, uuid);
        skinTexture.setSkinData(Base64.getDecoder().decode(packetByteBuf.readString()));
        ClientSkinCache.removeClientSkin(uuid);
        ClientSkinCache.addClientSkin(uuid, skinTexture);
    }


}
