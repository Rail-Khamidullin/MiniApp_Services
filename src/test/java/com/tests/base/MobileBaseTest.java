package com.tests.base;

import com.config.Configuration;
import com.microsoft.playwright.*;
import com.utils.TokenAuthorization;
import org.junit.jupiter.api.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class MobileBaseTest {
    protected Playwright playwright;
    protected Browser browser;
    protected BrowserContext context;
    protected Page page;
    public static String TOKEN;

    private static final Configuration CONFIG = new Configuration();

    @BeforeAll
    public void beforeAll() {
        playwright = Playwright.create();
        browser = playwright.chromium().launch(new BrowserType.LaunchOptions()
                .setHeadless(false));
        TOKEN = TokenAuthorization.getToken();
    }

    @BeforeEach
    public void setUp() {
        context = browser.newContext(new Browser.NewContextOptions()
                .setUserAgent("Mozilla/5.0 (iPhone; CPU iPhone OS 17_0 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/17.0 Mobile/15E148 Safari/604.1")
                .setViewportSize(390, 844)
                .setDeviceScaleFactor(3)
                .setHasTouch(true)
                .setIsMobile(true)
                .setLocale("ru-RU")
                .setTimezoneId("Europe/Moscow")
        );
        page = context.newPage();
        page.navigate(getBaseUrl());
    }

    private static String getBaseUrl() {
        return CONFIG.getProperty("base.url", "http://someurl.ru");
    }

    @AfterEach
    public void tearDown() {
        if (page != null) page.close();
        if (context != null) context.close();
    }

    @AfterAll
    public void afterAll() {
        if (browser != null) browser.close();
        if (playwright != null) playwright.close();
    }
}
