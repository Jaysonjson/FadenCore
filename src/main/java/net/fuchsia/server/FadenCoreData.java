package net.fuchsia.server;

import net.fabricmc.loader.api.FabricLoader;
import net.fuchsia.FadenCore;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;

public class FadenCoreData {

    public int BUY_MULTIPLIER = 9;

    public void save() {
        try {
            FileUtils.writeStringToFile(new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/" + FadenCore.MC_VERSION + "/faden.json"), FadenCore.GSON.toJson(FadenCore.DATA), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load() {
        File dataFile = new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/" + FadenCore.MC_VERSION + "/faden.json");
        if(dataFile.exists()) {
            try {
                FadenCore.DATA = FadenCore.GSON.fromJson(new FileReader(dataFile), FadenCoreData.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            save();
        }
    }



}
