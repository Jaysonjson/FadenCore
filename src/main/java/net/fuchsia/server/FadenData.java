package net.fuchsia.server;

import net.fabricmc.loader.api.FabricLoader;
import net.fuchsia.Faden;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

public class FadenData {

    public int BUY_MULTIPLIER = 9;

    public void save() {
        try {
            FileUtils.writeStringToFile(new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/" + Faden.MC_VERSION + "/faden.json"), Faden.GSON.toJson(Faden.DATA), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load() {
        File dataFile = new File(FabricLoader.getInstance().getGameDir().toString() + "/faden/cache/" + Faden.MC_VERSION + "/faden.json");
        if(dataFile.exists()) {
            try {
                Faden.DATA = Faden.GSON.fromJson(new FileReader(dataFile), FadenData.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            save();
        }
    }



}
