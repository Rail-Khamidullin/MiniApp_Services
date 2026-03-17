package com.config;

import java.io.InputStream;
import java.util.Properties;

public class Configuration {
    private static final Properties props = new Properties();

    static {
        try (InputStream input = Configuration.class.getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input == null) {
                System.out.println("config.properties not found in classpath");
                // Устанавливаем значения по умолчанию
                setDefaultProperties();
            } else {
                props.load(input);
            }
        } catch (Exception e) {
            e.printStackTrace();
            setDefaultProperties();
        }
    }

    private static void setDefaultProperties() {
        props.setProperty("browser", "chromium");
        props.setProperty("headless", "false");
        props.setProperty("timeout", "30000");
    }

    public static String getBrowser() {
        return props.getProperty("browser");
    }

    public static String getProperty(String key, String defaultValue) {
        return props.getProperty(key, defaultValue);
    }
}
