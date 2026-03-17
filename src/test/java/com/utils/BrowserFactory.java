package com.utils;

import com.config.ConfigurationManager;
import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.Playwright;

public enum BrowserFactory {

    CHROMIUM {
        @Override
        public Browser createInstance(final Playwright playwright) {
            return playwright.chromium().launch(options());
        }
    },
    FIREFOX {
        @Override
        public Browser createInstance(final Playwright playwright) {
            return playwright.firefox().launch(options());
        }
    },
    WEBKIT {
        @Override
        public Browser createInstance(final Playwright playwright) {
            return playwright.webkit().launch(options());
        }
    };

    public BrowserType.LaunchOptions options() {
        return new BrowserType.LaunchOptions()
                .setHeadless(ConfigurationManager.isHeadless())
                .setSlowMo(ConfigurationManager.getSlowMotion())
                .setTimeout(ConfigurationManager.getTimeout());
    }

    public abstract Browser createInstance(final Playwright playwright);

    // Метод для получения фабрики по имени браузера из конфига
    public static BrowserFactory fromConfig() {
        String browserName = ConfigurationManager.getBrowser();
        return fromString(browserName);
    }

    // Метод для получения фабрики по строке
    public static BrowserFactory fromString(String browserName) {
        try {
            return valueOf(browserName.toUpperCase());
        } catch (IllegalArgumentException e) {
            System.err.println("Unknown browser: " + browserName + ", using CHROMIUM");
            return CHROMIUM;
        }
    }
}
