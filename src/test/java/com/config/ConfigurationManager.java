package com.config;


public class ConfigurationManager {
    private static final Configuration CONFIG = new Configuration();

    private ConfigurationManager() {
    }

    public static String getBrowser() {
        return CONFIG.getBrowser();
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(CONFIG.getProperty("headless", "false"));
    }

    public static int getTimeout() {
        return Integer.parseInt(CONFIG.getProperty("timeout", "30000"));
    }

    public static int getSlowMotion() {
        return Integer.parseInt(CONFIG.getProperty("slow.motion", "500"));
    }

    public static String getBaseUrl() {
        return CONFIG.getProperty("base.url", "http://someurl.ru");
    }

    public static boolean isVideoEnabled() {
        return Boolean.parseBoolean(CONFIG.getProperty("video", "false"));
    }

    public static String getAllureResultsDir() {
        return CONFIG.getProperty("allure.results.directory", "target/allure-results");
    }
}
