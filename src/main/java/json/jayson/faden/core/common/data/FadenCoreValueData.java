package json.jayson.faden.core.common.data;

import json.jayson.faden.core.FadenCore;
import json.jayson.faden.core.util.SaveUtil;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;

public class FadenCoreValueData {

    public int buyMultiplier = 9;
    public int valueMultiplier = 1;

    public void save() {
        if(!FadenCore.MODULES.itemValues) return;
        try {
            FileUtils.writeStringToFile(new File(SaveUtil.getFolder() + "/item_value_data.json"), FadenCore.GSON.toJson(FadenCore.DATA), StandardCharsets.UTF_8);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void load() {
        if(!FadenCore.MODULES.itemValues) return;
        File dataFile = new File(SaveUtil.getFolder() + "/item_value_data.json");
        if(dataFile.exists()) {
            try {
                FadenCore.VALUE_DATA = FadenCore.GSON.fromJson(new FileReader(dataFile), FadenCoreValueData.class);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            save();
        }
    }

}
