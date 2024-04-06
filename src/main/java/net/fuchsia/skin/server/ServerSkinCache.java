package net.fuchsia.skin.server;

import net.minecraft.util.Identifier;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ServerSkinCache {

    private static List<ServerSkin> SKINS = new ArrayList<>();


    public static ServerSkin addSkinToServerCache(Identifier identifier, UUID uuid, byte[] skinData) {
        try {
            ServerSkin serverSkin = new ServerSkin();
            serverSkin.skinData = skinData;
            serverSkin.uuid = uuid;
            serverSkin.identifier = identifier;
            if (serverSkin.skinData != null) {
                SKINS.add(serverSkin);
            }
            return serverSkin;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static ServerSkin getServerSkin(UUID uuid) {
        for (ServerSkin serverSkin : getServerSkinCache()) {
            if(serverSkin.uuid.equals(uuid)) {
                return serverSkin;
            }
        }
        return null;
    }

    public static List<ServerSkin> getServerSkinCache() {
        return SKINS;
    }

    public static class ServerSkin {
        public byte[] skinData;
        public Identifier identifier;
        public UUID uuid;
    }

}
