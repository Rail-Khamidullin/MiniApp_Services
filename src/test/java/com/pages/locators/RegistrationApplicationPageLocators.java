package com.pages.locators;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;


public class RegistrationApplicationPageLocators {
    private final Page page;

    public RegistrationApplicationPageLocators(Page page) {
        this.page = page;
    }

    // ========== КНОПКИ НАВИГАЦИИ ==========

    public Locator getOnMainButton() {
        return page.locator("//button[contains(text(), 'На главную')]");
    }

    public Locator getButtonBack() {
        return page.locator("//div[contains(@id, 'header')]//button[contains(@class, 'MuiIconButton-root')]");
    }

    public Locator getSendRequestButton() {
        return page.locator("//button[contains(text(), 'Отправить заявку')]");
    }

    // ========== ЗАГОЛОВКИ ==========

    // наименование услуги, например Установка бра
    public Locator getMainHeader(String nameService) {
        return page.locator("//h1[contains(text(), '"+ nameService +"')]");
    }

    public Locator getHeader() {
        return page.locator("//h1[contains(text(), 'Оформление заявки')]");
    }

    // ========== БОНУСЫ ==========

    public Locator getQuantityBonus() {
        return page.locator("//div[contains(@class, 'MuiBox-root')]//div[@class = 'css-mgcte9']//p[contains(@class, 'MuiTypography-body1')]");
    }

    public Locator getWillAddBonus() {
        return page.locator("//div[@class = 'css-rpim9t']//p[contains(@class, 'css-1q8gi9y')]");
    }

    public Locator getUseBonus() {
        return page.locator("//span[@class = 'css-1xqhkmm']");
    }

    public Locator getSwitchButton() {
        return page.locator("//input[contains(@class,'PrivateSwitchBase-input')]");
    }

    // ========== ДАТА И ВРЕМЯ ==========

    public Locator getDataField() {
        return page.locator("//div[contains(@class, 'MuiPickersInputBase-sectionsContainer')]");
    }

    public Locator getTimeField() {
        return page.locator("//input[@class ='css-2qh9zc']");
    }

    public Locator getSetNewMonth() {
        return page.locator("//div[contains(@class, 'MuiPickersArrowSwitcher-root')]//button[contains(@class, 'MuiPickersArrowSwitcher-nextIconButton')]");
    }

    public Locator getOkButton() {
        return page.locator("//button[contains(text(), 'Ок')]");
    }

    public Locator getCancelButton() {
        return page.locator("//button[contains(text(), 'Отмена')]");
    }

    // ========== ВРЕМЕННЫЕ ИНТЕРВАЛЫ ==========

    public Locator getTimeInterval() {
        return page.locator("//div[@class ='mbsc-timegrid-row']//div[contains(text(), '09:00')]");
    }

    public Locator getSetTime() {
        return page.locator("//button[contains(text(), 'Установить')]");
    }

    public Locator getCancelTime() {
        return page.locator("//button[contains(text(), 'Отмена')]");
    }

    // ========== ПОЛЯ ВВОДА ==========

    public Locator getComments() {
        return page.locator("//label[text()='Комментарий']/following-sibling::div//textarea");
    }

    // ========== ДИНАМИЧЕСКИЕ ЛОКАТОРЫ ==========

    // Получить локатор для ячейки с конкретным днем в календаре
    // @param day день месяца (1-31)
    public Locator getDayCell(String day) {
        return page.locator(String.format(
                "button[role='gridcell']:text-is('%s'):not([disabled])",
                day
        ));
    }

    // Получить локатор для ячейки с конкретным днем (с учетом формата)
    public Locator getDayCellWithDate(String day, String month, String year) {
        return page.locator(String.format(
                "button[role='gridcell'][aria-label*='%s'][aria-label*='%s'][aria-label*='%s']:not([disabled])",
                day, month, year
        ));
    }

    // Получить локатор для временного интервала
    // time время в формате "09:00" или "09:30"
    public Locator getTimeInterval(String time) {
        return page.locator(String.format(
                "//div[@class ='mbsc-timegrid-row']//div[contains(text(), '%s')]",
                time
        ));
    }

    // Получить локатор для кнопки навигации по месяцам
    // direction направление: "next" или "prev"

    public Locator getMonthNavigationButton(String direction) {
        String className = direction.equals("next")
                ? "MuiPickersArrowSwitcher-nextIconButton"
                : "MuiPickersArrowSwitcher-previousIconButton";

        return page.locator(String.format(
                "//div[contains(@class, 'MuiPickersArrowSwitcher-root')]//button[contains(@class, '%s')]",
                className
        ));
    }

    // Получить локатор для выбора конкретного месяца
    // monthName название месяца на русском (Январь, Февраль и т.д.)
    public Locator getMonthPicker(String monthName) {
        return page.locator(String.format(
                "//div[contains(@class, 'MuiPickersMonth-root')]//button[contains(text(), '%s')]",
                monthName
        ));
    }


    // Получить локатор для выбора года
    public Locator getYearPicker(String year) {
        return page.locator(String.format(
                "//div[contains(@class, 'MuiPickersYear-root')]//button[contains(text(), '%s')]",
                year
        ));
    }

    // ========== ВСПОМОГАТЕЛЬНЫЕ МЕТОДЫ ==========

    // Получить все элементы бонусов для проверки
    public Locator[] getAllBonusLocators() {
        return new Locator[] {
                getQuantityBonus(),
                getWillAddBonus(),
                getUseBonus()
        };
    }

     // Проверить видимость всех основных элементов страницы
    public boolean areAllMainElementsVisible(String nameService) {
        try {
            return getOnMainButton().isVisible() &&
                    getButtonBack().isVisible() &&
                    getMainHeader(nameService).isVisible() &&
                    getHeader().isVisible() &&
                    getComments().isVisible() &&
                    getSendRequestButton().isVisible();
        } catch (Exception e) {
            return false;
        }
    }
}
