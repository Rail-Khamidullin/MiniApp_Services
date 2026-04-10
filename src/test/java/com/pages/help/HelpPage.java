package com.pages.help;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import com.pages.BasePage;
import io.qameta.allure.Step;
import java.nio.file.Paths;
import static com.utils.BasePageFactory.openPage;

public class HelpPage extends BasePage {

    private final Locator headerHelp;           // заголовок "Помощь"
    private final Locator askedQuestions;       // кнопка "Часто задаваемые вопросы"
    private final Locator legalInformation;     // кнопка "Правовая информация"
    private final Locator onMainButton;         // кнопка "На главную"

    public HelpPage(Page page) {
        super(page);
        this.headerHelp = page.locator("text='Помощь'");
        this.askedQuestions = page.locator("text='Часто задаваемые вопросы'");
        this.legalInformation = page.locator("text='Правовая информация'");
        this.onMainButton = page.locator("text='На главную'");
    }

    @Override
    @Step("Отображение элементов страницы Помощь")
    public boolean isPageLoaded() {
        String locatorName = "Пусто";
        // Ждем появления кнопок (с таймаутом 10 секунд)
        try {
            locatorName = "headerHelp";
            headerHelp.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "askedQuestions";
            askedQuestions.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "aboutApp";
            legalInformation.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "onMainButton";
            onMainButton.waitFor(new Locator.WaitForOptions().setTimeout(10000));

            return true; // если исключения не было, элемент найден
        } catch (Exception e) {
            System.out.println("Элемент не найден: " + locatorName);
            // скриншот, в случае падения теста
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/HelpPage/'" + locatorName + "'error.png")));
            return false; // элемент не найден за 10 секунд
        }
    }

    // класс "Часто задаваемые вопросы"
    public AskedQuestionsPage openAskedQuestions() {
        try {
            return openPage(askedQuestions, page, AskedQuestionsPage.class);
        } catch (
                TimeoutError e) {
            System.out.println("Элемент 'Часто задаваемые вопросы' не найден на главной странице");
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/HelpPage/openAskedQuestions_error.png")));
            throw new AssertionError("Не удалось открыть страницу Часто задаваемые вопросы", e);
        }
    }

    // класс "Правовая информация"
    public AboutAppPage openAboutApp() {
        try {
            return openPage(legalInformation, page, AboutAppPage.class);
        } catch (
                TimeoutError e) {
            System.out.println("Элемент 'Правовая информация' не найден на главной странице");
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/HelpPage/openAboutApp_error.png")));
            throw new AssertionError("Не удалось открыть страницу 'Правовая информация'", e);
        }
    }
}
