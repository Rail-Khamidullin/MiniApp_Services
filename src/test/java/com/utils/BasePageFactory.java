package com.utils;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.pages.BasePage;
import io.qameta.allure.Step;

public final class BasePageFactory {

    // универсальный метод, который проверяет отображение кнопки, выбирает её и возвращает необходимый класс
    public static <T extends BasePage> T openPage(Locator locator, Page page, Class<T> pageClass) {
        clickButton(locator);
        page.waitForLoadState();
        try {
            return pageClass.getDeclaredConstructor(Page.class).newInstance(page);
        } catch (Exception e) {
            throw new RuntimeException("Ошибка создания page object", e);
        }
    }

    @Step("Клик по кнопке")
    public static void clickButton(Locator locator) { locator.click(); }
}
