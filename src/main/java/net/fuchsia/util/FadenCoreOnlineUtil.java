package net.fuchsia.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import com.google.gson.reflect.TypeToken;

import net.fuchsia.FadenCore;

public class FadenCoreOnlineUtil {

    public static String getJSONData(String requestURL) {
        try (Scanner scanner = new Scanner(new URL(requestURL).openStream(), StandardCharsets.UTF_8.toString())) {
            scanner.useDelimiter("\\A");
            return scanner.hasNext() ? scanner.next() : "";
        } catch (IOException e) {
            FadenCore.LOGGER.error("Error fetching JSON data", e);
        }
        return "{}";
    }

    public static <T> T getDataOrCache(String requestURL, File file, String checksum) {
        try {
            if (!file.exists() || !isChecksumValid(file, checksum)) {
                String json = fetchAndCacheData(requestURL, file);
                return FadenCore.GSON.fromJson(json, new TypeToken<T>(){}.getType());
            } else {
                return readDataFromFile(file);
            }
        } catch (Exception e) {
            FadenCore.LOGGER.error("Error fetching or caching data", e);
        }
        return null;
    }

    private static boolean isChecksumValid(File file, String checksum) {
        try (InputStream inputStream = new FileInputStream(file)) {
            return FadenCoreCheckSum.checkSum(inputStream).equals(checksum);
        } catch (IOException e) {
            FadenCore.LOGGER.error("Error checking checksum", e);
        }
        return false;
    }

    private static void writeToFile(File file, String data) {
        try (FileWriter writer = new FileWriter(file)) {
            writer.write(data);
        } catch (IOException e) {
            FadenCore.LOGGER.error("Error writing to file", e);
        }
    }

    private static String fetchAndCacheData(String requestURL, File file) {
        String json = getJSONData(requestURL);
        writeToFile(file, json);
        return json;
    }

    private static <T> T readDataFromFile(File file) {
        try (FileReader fileReader = new FileReader(file)) {
            return FadenCore.GSON.fromJson(fileReader, new TypeToken<T>(){}.getType());
        } catch (IOException e) {
            FadenCore.LOGGER.error("Error reading data from file", e);
        }
        return null;
    }

    public static String getJSONDataOrCache(String requestURL, File file, String checksum) {
        try {
            String json = "{}";
            if (!file.exists() || !isChecksumValid(file, checksum)) {
                json = fetchAndCacheData(requestURL, file);
            } else {
                json = readDataFromFile(file);
            }
            return json;
        } catch (Exception e) {
            FadenCore.LOGGER.error("Error fetching or caching JSON data", e);
        }
        return "{}";
    }
}
