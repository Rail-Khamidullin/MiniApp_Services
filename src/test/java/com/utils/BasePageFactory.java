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
            System.out.println("Элемент '" + locator + "' не найден в '" + pageClass + "'");
            throw new RuntimeException("Ошибка создания page object", e);
        }
    }

    // Перегруженный метод для случаев, когда не нужно кликать (уже на нужной странице)
    public static <T extends BasePage> T openPage(Page page, Class<T> pageClass) {
        return createPageWithWait(page, pageClass);
    }

    // Приватный метод для создания страницы с ожиданием стабилизации
    private static <T extends BasePage> T createPageWithWait(Page page, Class<T> pageClass) {
        page.waitForLoadState();

        // Небольшая пауза для стабилизации DOM после загрузки
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // Ждем, пока страница полностью загрузится
        page.waitForFunction("() => document.readyState === 'complete'");

        return createPage(page, pageClass);
    }

    @Step("Клик по кнопке")
    public static void clickButton(Locator locator) {
        locator.waitFor(new Locator.WaitForOptions().setTimeout(10000));
        locator.click();
    }

    // Метод для простого создания объекта страницы (без ожиданий)
    private static <T extends BasePage> T createPage(Page page, Class<T> pageClass) {
        try {
            return pageClass.getDeclaredConstructor(Page.class).newInstance(page);
        } catch (Exception e) {
            System.out.println("Ошибка создания page object: " + pageClass.getSimpleName());
            throw new RuntimeException("Ошибка создания page object", e);
        }
    }
}
