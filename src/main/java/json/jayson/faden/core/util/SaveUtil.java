package json.jayson.faden.core.util;

import json.jayson.faden.core.server.ServerPlayerDatas;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.MinecraftClient;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.integrated.IntegratedServer;
import net.minecraft.util.WorldSavePath;
import net.minecraft.world.World;

public class SaveUtil {

    public static String getCurrentSaveName(IntegratedServer server) {
        return server.getSavePath(WorldSavePath.ROOT).getParent().getFileName().toString();
    }

    public static String getCurrentSaveName(MinecraftServer server) {
        return server.getSavePath(WorldSavePath.ROOT).getParent().getFileName().toString();
    }

    public static String getCurrentSaveName() {
        return getCurrentSaveName(ServerPlayerDatas.SERVER);
    }

    public static String getCurrentSaveFull(MinecraftServer server) {
        return server.getSavePath(WorldSavePath.ROOT) + "/fadencore/";
    }

    public static String getCurrentSaveFull() {
        return getCurrentSaveFull(ServerPlayerDatas.SERVER);
    }


    @Environment(EnvType.CLIENT)
    public static String getCurrentSaveFullClient() {
        if(MinecraftClient.getInstance().isInSingleplayer()) {
            return getCurrentSaveFull(MinecraftClient.getInstance().getServer());
        }
        return FabricLoader.getInstance().getGameDir() + "/" + MinecraftClient.getInstance().getCurrentServerEntry().address.replaceAll(".","") + "/fadencore/";
    }
}
