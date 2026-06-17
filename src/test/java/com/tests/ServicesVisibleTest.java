package com.tests;

import com.dataBase.DatabaseService;
import com.pages.MainPage;
import com.pages.services.ServicesPage;
import com.pages.services.electricalWork.*;
import com.pages.services.enums.BonusOption;
import com.tests.base.MobileBaseTest;
import jdk.jfr.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServicesVisibleTest extends MobileBaseTest {

    @ParameterizedTest
    @CsvSource({
            "USE_BONUSES, 13:00",
            "NOT_USE_BONUSES, 14:00"
    })
    @DisplayName("Проверка работы блока 'Услуги'")
    @Description("Создание заявки в блоке 'Услуги' с использованием и без использования экобонусов")
    public void testCreateApplication(BonusOption bonusOption, String time) {

        MainPage mainPage = new MainPage(page);
        ServicesPage servicesPage = mainPage.tapToServices();
        assertTrue(servicesPage.isPageLoaded(), "Окно 'Услуги' не загрузилось");

        // объект TypeElectricalWorkPage
        TypeElectricalWorkPage electricalWorkPage = servicesPage.tapToConcreteService();
        assertTrue(electricalWorkPage.isPageLoaded(), "Окно 'Электромонтажные работы' не загрузилось");

        // объект LightingDevices
        LightingDevicesPage lightingDevicesPage = electricalWorkPage.tapToLightingDevices();
        assertTrue(lightingDevicesPage.isPageLoaded(), "Окно 'Приборы освещения' не загрузилось");

        // объект LampInstallation
        LampInstallationPage lampInstallationPage = lightingDevicesPage.tapToLampInstallation();
        assertTrue(lampInstallationPage.isPageLoaded(), "Окно 'Установка бра' не загрузилось");
        // проверяем контент
        assertTrue(lampInstallationPage.verifyContent(), "Описание услуги 'Установка бра' не верное");

        // объект LampInstallation
        RegistrationApplicationPage registrationApplicationPage = lampInstallationPage.tapToSubmitButton();
        assertTrue(registrationApplicationPage.isPageLoaded(), "Окно 'Оформление заявки' услуги 'Установка бра' не загрузилась");

        // объект WindowRegistrationCompletedPage
        WindowRegistrationCompletedPage windowRegistrationCompletedPage =
                registrationApplicationPage.registrationWithBonus(bonusOption, time);
        Double currentBonuses = registrationApplicationPage.currentBonuses;   // берём текущее кол-во бонусов
        assertTrue(windowRegistrationCompletedPage.isPageLoaded(), "Успешное оформление заявки на услугу");

        // проверка соответствия комментария с информацией в БД
        String actualComment = registrationApplicationPage.textComment;
        String factComment = new DatabaseService().getTextInBD(page);
        assertTrue(factComment.contains(actualComment), "Текст комментария НЕ совпадает с БД");

        // проверяем правильно ли система считает остаток бонусов
        windowRegistrationCompletedPage.backToMainPage();
        assertEquals(mainPage.getCurrentBonuses(), currentBonuses, "Система не верно считает бонусы");
    }
}
