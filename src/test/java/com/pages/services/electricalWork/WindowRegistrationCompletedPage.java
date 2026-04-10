package com.pages.services.electricalWork;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.pages.BasePage;
import com.pages.MainPage;
import io.qameta.allure.Step;

import java.nio.file.Paths;

import static com.utils.BasePageFactory.openPage;

public class WindowRegistrationCompletedPage extends BasePage {

    // Локаторы (инициализируем в конструкторе)
    private final Locator checkRegistration;          // виды услуг
    private final Locator buttonBack;                 // кнопка "Назад"
    private final Locator onMainButton;               // кнопка "На главную"
    private final Locator goToServiceButton;          // кнопка "Мои заявки"

    public WindowRegistrationCompletedPage(Page page) {
        super(page);
        this.buttonBack = page.locator("//div[contains(@id, 'header')]//button[contains(@class, 'MuiIconButton-root')]");
        this.onMainButton = nameLocator("На главную");
        this.goToServiceButton = nameLocator("Перейти к группе услуг");
        this.checkRegistration = nameLocator("Заявка оформлена");
    }

    @Override
    @Step("Проверка отображение объектов успешного оформления услуги")
    public boolean isPageLoaded() {
        String locatorName = "Пусто";
        // Ждем появления кнопок (с таймаутом 10 секунд)
        try {
            locatorName = "goToServiceButton";
            goToServiceButton.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "onMainButton";
            onMainButton.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "buttonBack";
            buttonBack.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "checkRegistration";
            checkRegistration.waitFor(new Locator.WaitForOptions().setTimeout(10000));

            return true; // если исключения не было, элемент найден
        } catch (Exception e) {
            System.out.println("Элемент не найден: " + locatorName);
            // скриншот, в случае падения теста
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/ServicesPage/WindowRegistrationCompletedPage_error.png")));
            return false; // элемент не найден за 10 секунд
        }
    }

    public MainPage backToMainPage() {
        return openPage(onMainButton, page, MainPage.class);
    }
}
