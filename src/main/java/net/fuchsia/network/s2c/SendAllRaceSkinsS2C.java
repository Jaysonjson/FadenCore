package net.fuchsia.network.s2c;

import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fuchsia.race.skin.client.ClientRaceSkinCache;
import net.fuchsia.race.skin.provider.SkinProvider;
import net.fuchsia.race.skin.server.ServerSkinCache;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.PacketByteBuf;

import java.io.*;
import java.util.Base64;
import java.util.HashMap;
import java.util.UUID;

public class SendAllRaceSkinsS2C {

    public static void receive(MinecraftClient client, ClientPlayNetworkHandler clientPlayNetworkHandler, PacketByteBuf packetByteBuf, PacketSender packetSender) {
        try {
            ByteArrayInputStream byteOut = new ByteArrayInputStream(Base64.getDecoder().decode(packetByteBuf.readString()));
            ObjectInputStream out = new ObjectInputStream(byteOut);
            HashMap<UUID, String> map = (HashMap<UUID, String>) out.readObject();
            byteOut.close();
            out.close();
            for (UUID uuid : map.keySet()) {
                ClientRaceSkinCache.getPlayerSkins().put(uuid, SkinProvider.getSkinIdentifier(map.get(uuid)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
