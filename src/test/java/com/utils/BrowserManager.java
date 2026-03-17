package com.utils;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;
import lombok.Getter;
import java.nio.file.Paths;

@Getter
public class BrowserManager implements AutoCloseable {
    private Playwright playwright;
    private Browser browser;
    private BrowserContext context;
    private Page page;

    public BrowserManager() {
        startBrowser();
    }

    private void startBrowser() {
        playwright = Playwright.create();
        BrowserFactory factory = BrowserFactory.fromConfig();
        browser = factory.createInstance(playwright);
    }

    public Page createPage() {
        page = browser.newPage();
        return page;
    }

    public Page createPage(String url) {
        Page newPage = createPage();
        newPage.navigate(url);
        return newPage;
    }

    public void takeScreenshot(String testName) {
        if (page != null) {
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get("target/screenshots/" + testName + ".png")));
        }
    }

    public void closePage() {
        if (page != null) {
            page.close();
            page = null;
        }
        if (context != null) {
            context.close();
            context = null;
        }
    }

    @Override
    public void close() {
        closePage();
        if (browser != null) {
            browser.close();
            browser = null;
        }
        if (playwright != null) {
            playwright.close();
            playwright = null;
        }
    }

    public Page getPage() {
        if (page == null) {
            page = createPage();
        }
        return page;
    }
}
