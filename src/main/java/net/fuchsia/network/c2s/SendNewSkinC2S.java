package net.fuchsia.network.c2s;

import net.fuchsia.network.FadenNetwork;
import net.fuchsia.skin.provider.SkinProvider;
import net.fuchsia.skin.server.ServerSkinCache;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;

import java.util.Base64;
import java.util.UUID;

public class SendNewSkinC2S {

    public static void receive(MinecraftServer server, ServerPlayerEntity player, ServerPlayNetworkHandler handler, PacketByteBuf packetByteBuf, PacketSender responseSender) {
        UUID uuid = packetByteBuf.readUuid();
        byte[] skinData = Base64.getDecoder().decode(packetByteBuf.readString());
        ServerSkinCache.ServerSkin serverSkin = ServerSkinCache.addSkinToServerCache(SkinProvider.getSkinIdentifier(uuid), uuid, skinData);

        if(serverSkin != null) {
            for (ServerPlayerEntity serverPlayerEntity : server.getPlayerManager().getPlayerList()) {
                FadenNetwork.Server.sendSingletonSkin(serverPlayerEntity, serverSkin);
            }
        }
    }

}

