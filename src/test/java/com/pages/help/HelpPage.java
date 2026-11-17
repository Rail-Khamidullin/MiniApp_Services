package com.pages.help;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import com.pages.BasePage;
import com.pages.services.electricalWork.RegistrationApplicationPage;
import io.qameta.allure.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Paths;
import static com.utils.BasePageFactory.openPage;

public class HelpPage extends BasePage {

    // логирование
    private static final Logger logger = LoggerFactory.getLogger(RegistrationApplicationPage.class);

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
    public boolean isPageLoaded(int timeout) {
        String locatorName = "Пусто";
        // Ждем появления кнопок (с таймаутом 10 секунд)
        try {
            locatorName = "headerHelp";
            headerHelp.waitFor(new Locator.WaitForOptions().setTimeout(timeout));
            locatorName = "askedQuestions";
            askedQuestions.waitFor(new Locator.WaitForOptions().setTimeout(timeout));
            locatorName = "aboutApp";
            legalInformation.waitFor(new Locator.WaitForOptions().setTimeout(timeout));
            locatorName = "onMainButton";
            onMainButton.waitFor(new Locator.WaitForOptions().setTimeout(timeout));

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

    @Step("Подсчёт будущего количества бонусов после оформления услуги")
    public static Double futureBonuses(Locator quantityBonus, Locator useBonus, Locator willAddBonus) {
        if (quantityBonus.count() > 0 || useBonus.count() > 0 || willAddBonus.count() > 0) {
            try {
                Double allBonuses = Double.parseDouble(quantityBonus.textContent());   // текущее кол-во бонусов
                Double useBonuses = Double.parseDouble(useBonus.textContent());        // используемое кол-во бонусов
//                Double newBonuses = Double.parseDouble(willAddBonus.textContent());  // кол-во бонусов кэшбэком
//                Double current = allBonuses - useBonuses + newBonuses;
                Double current = allBonuses - useBonuses;
                logger.info("Registration = {}", current);
                return current;
            } catch (Exception e) {
                return 0.0;
            }
        }
        return 0.0;
    }
}
