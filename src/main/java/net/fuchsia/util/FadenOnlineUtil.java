package net.fuchsia.util;

import com.google.gson.reflect.TypeToken;
import net.fuchsia.Faden;
import net.fuchsia.common.cape.FadenCapes;
import net.fuchsia.common.cape.online.OnlineCape;
import net.fuchsia.common.cape.online.OnlineCapeCache;
import net.fuchsia.config.FadenOptions;

import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;
import java.util.UUID;

public class FadenOnlineUtil {

    public static String getJSONData(String requestURL) {
        try (Scanner scanner = new Scanner(new URL(requestURL).openStream(), StandardCharsets.UTF_8.toString()))
        {
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "{}";
    }

    public static <T> T getDataOrCache(String requestURL, File file, String checksum) {
        try {
            T t = null;
            String json = "{}";
            if(!file.exists()) {
                //USE GITHUB JSON INSTEAD OF GSON, SO THE CHECKSUM MATCHES
                json = FadenOnlineUtil.getJSONData(requestURL);
                t = Faden.GSON.fromJson(json, new TypeToken<T>(){}.getType());
                FileWriter writer = new FileWriter(file);
                writer.write(json);
                writer.close();
            } else {
                InputStream inputStream = new FileInputStream(file);
                t = Faden.GSON.fromJson(new FileReader(file), new TypeToken<T>(){}.getType());
                if(!FadenCheckSum.checkSum(inputStream).equals(checksum)) {
                    Faden.LOGGER.warn("Mismatched Checksum for " + file + " - retrieving data again");
                    json = FadenOnlineUtil.getJSONData(requestURL);
                    t = Faden.GSON.fromJson(json, new TypeToken<T>(){}.getType());
                }
                inputStream.close();
                FileWriter writer = new FileWriter(file);
                writer.write(json);
                writer.close();
            }
            return t;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getJSONDataOrCache(String requestURL, File file, String checksum) {
        try {
            String json = "{}";
            if(!file.exists()) {
                //USE GITHUB JSON INSTEAD OF GSON, SO THE CHECKSUM MATCHES
                json = FadenOnlineUtil.getJSONData(requestURL);
                FileWriter writer = new FileWriter(file);
                writer.write(json);
                writer.close();
            }

            if(!file.exists() || json.equalsIgnoreCase("{}")){
                InputStream inputStream = new FileInputStream(file);
                if(!FadenCheckSum.checkSum(inputStream).equals(checksum) || json.equalsIgnoreCase("{}")) {
                    Faden.LOGGER.warn("Mismatched Checksum for " + file + " - retrieving data again");
                    json = FadenOnlineUtil.getJSONData(requestURL);
                    FileWriter writer = new FileWriter(file);
                    writer.write(json);
                    writer.close();
                }
                inputStream.close();
            }

            return json;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "{}";
    }


}
