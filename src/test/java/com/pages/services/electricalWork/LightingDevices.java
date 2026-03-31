package com.pages.services.electricalWork;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.pages.BasePage;
import java.nio.file.Paths;
import static com.utils.BasePageFactory.openPage;

public class LightingDevices extends BasePage {

    // Локаторы (инициализируем в конструкторе)
    private final Locator onMainButton;                             // кнопка "На главную"
    private final Locator buttonBack;                               // кнопка "Назад"
    private final Locator mainHeader;                               // главный заголовок "Приборы освещения"
    private final Locator header;                                   // заголовок
    private final Locator buttonFurther;                            // кнопка "Далее"
    private final Locator lampInstallation;                         // кнопка "Установка бра"

    public LightingDevices(Page page) {
        super(page);
        this.onMainButton = nameLocator("На главную");
        this.buttonBack = page.locator("//div[contains(@id, 'header')]//button[contains(@class, 'MuiIconButton-root')]");
        this.mainHeader = nameLocator("Приборы освещения");
        this.header = nameLocator("Электромонтажные работы");
        this.buttonFurther = nameLocator("Далее");
        this.lampInstallation = nameLocator("Установка бра");
    }

    @Override
    public boolean isPageLoaded() {
        String locatorName = "Пусто";
        // Ждем появления кнопок (с таймаутом 10 секунд)
        try {
            locatorName = "onMainButton";
            onMainButton.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "buttonBack";
            buttonBack.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "buttonFurther";
            buttonFurther.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "mainHeader";
            mainHeader.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "header";
            header.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "lampInstallation";
            lampInstallation.waitFor(new Locator.WaitForOptions().setTimeout(10000));

            return true; // если исключения не было, элемент найден
        } catch (Exception e) {
            System.out.println("Элемент не найден: " + locatorName);
            // скриншот, в случае падения теста
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/TypeElectricalWorkPage/LightingDevices/isPageLoaded_error.png")));
            return false; // элемент не найден за 10 секунд
        }
    }

    // открыть страницу Приборы освещения
    public LampInstallation tapToLampInstallation() {
        lampInstallation.click();
        return openPage(buttonFurther, page, LampInstallation.class);
    }
}
