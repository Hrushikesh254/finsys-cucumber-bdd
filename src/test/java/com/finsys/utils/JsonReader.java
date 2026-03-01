package com.finsys.utils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.InputStreamReader;
import java.io.Reader;
import java.lang.reflect.Type;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;

/**
 * JsonReader - Reads XPaths from en.json file
 */
public class JsonReader {

    private static Map<String, String> locators;

    static {
        loadLocators("en.json");
    }

    private static void loadLocators(String fileName) {
        try (Reader reader = new InputStreamReader(
                Objects.requireNonNull(JsonReader.class.getClassLoader().getResourceAsStream(fileName)),
                StandardCharsets.UTF_8)) {

            Gson gson = new Gson();
            Type type = new TypeToken<Map<String, String>>() {
            }.getType();
            locators = gson.fromJson(reader, type);

        } catch (Exception e) {
            throw new RuntimeException("Failed to load locators from " + fileName, e);
        }
    }

    /**
     * Gets XPath by its key from the JSON file
     * 
     * @param key The locator key
     * @return The XPath string
     */
    public static String getXpath(String key) {
        if (locators == null) {
            throw new RuntimeException("Locators map is not initialized. Check if en.json exists and is valid.");
        }
        String xpath = locators.get(key);
        if (xpath == null) {
            throw new RuntimeException("Locator key '" + key + "' not found in en.json");
        }
        return xpath;
    }
}
