package com.pages.services;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.pages.BasePage;
import com.pages.services.electricalWork.TypeElectricalWorkPage;
import io.qameta.allure.Step;

import java.nio.file.Paths;
import static com.utils.BasePageFactory.openPage;

public class ServicesPage extends BasePage {

    // Локаторы (инициализируем в конструкторе)
    private final Locator electricalWork;        // виды услуг
    private final Locator headerServices;        // заголовок "Услуги"
    private final Locator onMainButton;          // кнопка "На главную"
    private final Locator buttonBack;            // кнопка "Мои заявки"

    public ServicesPage(Page page) {
        super(page);
        this.headerServices = page.locator("//div[contains(@class, 'css-1xu0p80')]");
        this.onMainButton = nameLocator("На главную");
        this.buttonBack = page.locator("//div[contains(@id, 'header')]//button[contains(@class, 'MuiIconButton-root')]");
        this.electricalWork = nameLocator("Электромонтажные работы");
    }

    @Override
    @Step("Проверка отображение объектов окна услуг")
    public boolean isPageLoaded() {
        String locatorName = "Пусто";
        // Ждем появления кнопок (с таймаутом 10 секунд)
        try {
            locatorName = "headerServices";
            headerServices.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "onMainButton";
            onMainButton.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "buttonBack";
            buttonBack.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "electricalWork";
            electricalWork.waitFor(new Locator.WaitForOptions().setTimeout(10000));

            return true; // если исключения не было, элемент найден
        } catch (Exception e) {
            System.out.println("Элемент не найден: " + locatorName);
            // скриншот, в случае падения теста
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/ServicesPage/isPageLoaded.png")));
            return false; // элемент не найден за 10 секунд
        }
    }

    // открыть страницу с электромонтажными работами
    public TypeElectricalWorkPage tapToConcreteService() {
        electricalWork.click();
        return openPage(electricalWork, page, TypeElectricalWorkPage.class);
    }
}
