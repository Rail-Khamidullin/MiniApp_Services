package com.pages.services.electricalWork;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.pages.BasePage;
import java.nio.file.Paths;
import static com.utils.BasePageFactory.openPage;

public class TypeElectricalWorkPage extends BasePage {

    // Локаторы (инициализируем в конструкторе)
    private final Locator onMainButton;                               // кнопка "На главную"
    private final Locator buttonBack;                                 // кнопка "Назад"
    private final Locator buttonFurther;                              // кнопка "Далее"
    private final Locator buttonLightingDevices;                      // кнопка "Приборы освещения"
    private final Locator buttonDiagnostics;                          // кнопка "Диагностика электропроводки"
    private final Locator buttonSwitches;                             // кнопка "Выключатели и розетки"
    private final Locator buttonConsultation;                         // кнопка "Консультация по электромонтажным работам"

    public TypeElectricalWorkPage(Page page) {
        super(page);
        this.onMainButton = nameLocator("На главную");
        this.buttonBack = page.locator("//div[contains(@id, 'header')]//button[contains(@class, 'MuiIconButton-root')]");
        this.buttonFurther = nameLocator("Далее");
        this.buttonLightingDevices = nameLocator("Приборы освещения");
        this.buttonDiagnostics = nameLocator("Диагностика электропроводки");
        this.buttonSwitches = nameLocator("Выключатели и розетки");
        this.buttonConsultation = nameLocator("Консультация по электромонтажным работам");
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
            locatorName = "buttonLightingDevices";
            buttonLightingDevices.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "buttonDiagnostics";
            buttonDiagnostics.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "buttonSwitches";
            buttonSwitches.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "buttonConsultation";
            buttonConsultation.waitFor(new Locator.WaitForOptions().setTimeout(10000));

            return true; // если исключения не было, элемент найден
        } catch (Exception e) {
            System.out.println("Элемент не найден: " + locatorName);
            // скриншот, в случае падения теста
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/TypeElectricalWorkPage/isPageLoaded_error.png")));
            return false; // элемент не найден за 10 секунд
        }
    }

    // открыть страницу Приборы освещения
    public LightingDevices tapToLightingDevices() {
        buttonLightingDevices.click();
        return openPage(buttonFurther, page, LightingDevices.class);
    }
}
