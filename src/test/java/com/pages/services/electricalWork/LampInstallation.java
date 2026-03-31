package com.pages.services.electricalWork;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.pages.BasePage;
import java.nio.file.Paths;
import static com.data.TextServices.*;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class LampInstallation extends BasePage {

    // Локаторы (инициализируем в конструкторе)
    private final Locator onMainButton;                        // кнопка "На главную"
    private final Locator buttonBack;                          // кнопка "Назад"
    private final Locator mainHeader;                          // главный заголовок "Приборы освещения"
    private final Locator header;                              // заголовок
    private final Locator submitButton;                        // кнопка "Оформить заявку"
    private final Locator descriptionBlock;                    // блок с описанием услуги
    private final Locator disclaimerBlock;                     // дисклеймер снизу
    private final Locator priceBlock;                          // блок с ценой

    public LampInstallation(Page page) {
        super(page);
        this.onMainButton = nameLocator("На главную");
        this.buttonBack = page.locator("//div[contains(@id, 'header')]//button[contains(@class, 'MuiIconButton-root')]");
        this.mainHeader = nameLocator("Установка бра");
        this.header = nameLocator("Об услуге");
        this.submitButton = nameLocator("Оформить заявку");
        this.descriptionBlock = page.locator("//div[contains(@class, 'MuiBox-root')]//div[@class='css-sdsg2e']");
        this.disclaimerBlock = page.locator("//div[contains(@class, 'MuiBox-root')]/p[contains(@class, 'MuiTypography-body2')]");
        this.priceBlock = page.locator("//div[contains(@class, 'MuiBox-root')]//div[contains(@class, 'MuiStack-root')]");
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
            locatorName = "mainHeader";
            mainHeader.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "header";
            header.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "descriptionBlock";
            descriptionBlock.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "submitButton";
            submitButton.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "disclaimerBlock";
            disclaimerBlock.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "priceBlock";
            priceBlock.waitFor(new Locator.WaitForOptions().setTimeout(10000));

            return true; // если исключения не было, элемент найден
        } catch (Exception e) {
            System.out.println("Элемент не найден: " + locatorName);
            // скриншот, в случае падения теста
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/TypeElectricalWorkPage/LightingDevices/LampInstallation_error.png")));
            return false; // элемент не найден за 10 секунд
        }
    }

    public boolean verifyContent() {
        try {
            // проверяем каждый элемент
            assertThat(priceBlock).containsText("Полная предоплата");
            assertThat(priceBlock).containsText("1 500 ₽");
            assertThat(descriptionBlock).containsText(DESCRIPTION_INSTALLATION_BRA_ONE);
            assertThat(descriptionBlock).containsText(DESCRIPTION_INSTALLATION_BRA_TWO);
            assertThat(disclaimerBlock).containsText(DESCRIPTION_INSTALLATION_BRA_THREE);
            return true;
        } catch (Exception e) {
            System.out.println("Ошибка при проверке контента: " + e.getMessage());
            page.screenshot(new Page.ScreenshotOptions()
                    .setPath(Paths.get("screenshots/TypeElectricalWorkPage/LightingDevices/verifyContent.png")));
            return false;
        }
    }
}
