package net.fuchsia.skin.client;

import net.fuchsia.Faden;
import net.fuchsia.skin.provider.SkinProvider;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class ClientSkinRetrieveThread implements Runnable {
    @Override
    public void run() {
        Path path = ClientSkinCache.getSkinFolder();
        File folder = path.toFile();
        if(!folder.exists()) folder.mkdirs();

        for (File file : folder.listFiles()) {
            if(file.length() / 1000.0f > ClientSkinCache.MAX_SKIN_FILE_SIZE) {
                Faden.LOGGER.warn("Skin " + file.getName() + " bigger than allowed File Size! (" + file.length() / 1000.0f + "kb/" + ClientSkinCache.MAX_SKIN_FILE_SIZE + "kb)");
            } else {
                try {
                    ClientSkinCache.addLocalSkin(SkinProvider.readSkin(file));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
