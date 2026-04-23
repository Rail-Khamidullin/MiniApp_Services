package com.pages.services.electricalWork;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.YearMonth;
import static com.microsoft.playwright.assertions.PlaywrightAssertions.assertThat;

public class SupportRegistrationPage {

    protected Page page;

    public SupportRegistrationPage(Page page) {
        this.page = page;
    }

    @Step("Добавляем комментарий в поле с временем проведения тест кейса")
    public String setTextInComment(Locator textField) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        String text = "Автотест для проверки оформления заявки от '" + currentDateTime + "'";
        textField.fill(text);
        return text;
    }

    @Step("Установка временного интервала с выбором времени начала периода в формате чч:мм (09:00)")
    public void setTimeInterval(Locator timeField, String timeBegin, Locator buttonSet) {
        timeField.click();
        Locator timeOption = page.locator("//div[@class='mbsc-timegrid-row']//div[contains(text(), '" + timeBegin + "')]");
        assertThat(timeOption).isVisible();
        timeOption.click();
        // Подтверждение
        buttonSet.click();
        buttonSet.click();
    }

    @Step("Возвращаем следующий день после текущего, предварительно выбрав новый месяц (при необходим.)")
    public Integer setDay(Locator newMonth) {
        LocalDate currentDate = LocalDate.now();
        int currentDay = currentDate.getDayOfMonth();

        YearMonth currentMonth = YearMonth.now();
        int daysInMonth = currentMonth.lengthOfMonth();
        if (currentDay < daysInMonth) {
            return currentDay + 1;
        } else if (currentDay == daysInMonth) {
            newMonth.click();
            return 1;
        }
        return null;
    }
}
