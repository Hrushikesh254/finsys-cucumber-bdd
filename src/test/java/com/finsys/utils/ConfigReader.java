package com.finsys.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * ConfigReader - Reads configuration properties from config.properties
 */
public class ConfigReader {

    private static Properties properties;
    private static final String CONFIG_FILE = "src/test/resources/config.properties";

    static {
        try {
            FileInputStream fis = new FileInputStream(CONFIG_FILE);
            properties = new Properties();
            properties.load(fis);
            fis.close();
        } catch (IOException e) {
            throw new RuntimeException("Failed to load config.properties: " + e.getMessage());
        }
    }

    public static String getProperty(String key) {
        String value = properties.getProperty(key);
        if (value == null) {
            throw new RuntimeException("Property '" + key + "' not found in config.properties");
        }
        return value.trim();
    }

    public static String getBaseUrl() {
        return getProperty("base.url");
    }

    public static String getBrowser() {
        return getProperty("browser");
    }

    public static String getUsername() {
        return getProperty("app.username");
    }

    public static String getPassword() {
        return getProperty("app.password");
    }

    public static long getImplicitWait() {
        return Long.parseLong(getProperty("implicit.wait"));
    }

    public static long getExplicitWait() {
        return Long.parseLong(getProperty("explicit.wait"));
    }
}
