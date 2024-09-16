package json.jayson.faden.core.server;

import json.jayson.faden.core.util.SaveUtil;

import java.io.File;

public class FadenCoreData {


    public void save() {
        try {
            //FileUtils.writeStringToFile(new File(SaveUtil.getCurrentSaveFull() + "/fadencore.json"), FadenCore.GSON.toJson(FadenCore.DATA), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load() {
        File dataFile = new File(SaveUtil.getFolder() + "/fadencore.json");
        if(dataFile.exists()) {
            try {
                //FadenCore.DATA = FadenCore.GSON.fromJson(new FileReader(dataFile), FadenCoreData.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            save();
        }
    }



}
