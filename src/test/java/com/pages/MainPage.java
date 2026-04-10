package com.pages;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.TimeoutError;
import com.pages.help.HelpPage;
import com.pages.services.ServicesPage;
import io.qameta.allure.Step;

import static com.utils.BasePageFactory.openPage;

public class MainPage extends BasePage {

    // Локаторы (инициализируем в конструкторе)
    private final Locator ecoBonuses;             // отображение экобонусов
    private final Locator buttonHelp;             // кнопка "Помощь"
    private final Locator buttonServices;         // кнопка "Услуги"
    private final Locator buttonMyApplications;   // кнопка "Мои заявки"
    private final Locator currentBonuses;

    public MainPage(Page page) {
        super(page);
        this.ecoBonuses = page.locator("text='Экобонусы'");
        this.buttonHelp = page.locator("text='Помощь'");
        this.buttonServices = page.locator("//div[@class='MuiBox-root css-j7qwjs']//p[text()='Услуги']");
        this.buttonMyApplications = page.locator("text='Мои заявки'");
        this.currentBonuses = page.locator("//div[@class = 'css-3v2363']");
    }

    @Override
    @Step("МиниАпп 'Услуги' отображается")
    public boolean isPageLoaded() {

        String locatorName = "Пусто";
        // Ждем появления кнопок (с таймаутом 10 секунд)
        try {
            locatorName = "ecoBonuses";
            ecoBonuses.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "buttonHelp";
            buttonHelp.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "buttonServices";
            buttonServices.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "buttonMyApplications";
            buttonMyApplications.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "quantityBonus";
            currentBonuses.waitFor(new Locator.WaitForOptions().setTimeout(10000));

            return true; // если исключения не было, элемент найден
        } catch (TimeoutError e) {
            System.out.println("Элемент не найден: " + locatorName);
            return false;
        } catch (Exception e) {
            System.out.println("Другая ошибка для элемента " + locatorName + ": " + e.getMessage());
            return false;
        }
    }

    @Step("Открываем блок 'Помощь'")
    public HelpPage tapToHelp() {
        return openPage(buttonHelp, page, HelpPage.class);
    }

    @Step("Открываем блок 'Услуги'")
    public ServicesPage tapToServices() {
        return openPage(buttonServices, page, ServicesPage.class);
    }

    @Step("Фиксируаем текущее кол-во бонусов")
    public int getCurrentBonuses() {
        currentBonuses.textContent();
        return 0;
    }
}
