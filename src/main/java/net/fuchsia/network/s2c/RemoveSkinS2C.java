package net.fuchsia.network.s2c;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fuchsia.common.race.skin.client.ClientRaceSkinCache;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

import java.util.UUID;

public class RemoveSkinS2C {

    public static void receive(MinecraftClient client, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf packetByteBuf, PacketSender packetSender) {
        UUID uuid = packetByteBuf.readUuid();
        ClientRaceSkinCache.getPlayerSkins().remove(uuid);
    }

}
