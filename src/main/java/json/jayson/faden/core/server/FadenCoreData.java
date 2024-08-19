package json.jayson.faden.core.server;

import json.jayson.faden.core.util.SaveUtil;
import net.fabricmc.loader.api.FabricLoader;
import json.jayson.faden.core.FadenCore;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;

public class FadenCoreData {

    public int BUY_MULTIPLIER = 9;

    public void save() {
        try {
            FileUtils.writeStringToFile(new File(SaveUtil.getCurrentSaveFull() + "/fadencore.json"), FadenCore.GSON.toJson(FadenCore.DATA), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load() {
        File dataFile = new File(SaveUtil.getCurrentSaveFull() + "/fadencore.json");
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
