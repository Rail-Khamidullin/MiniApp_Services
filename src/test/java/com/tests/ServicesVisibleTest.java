package com.tests;

import com.pages.MainPage;
import com.pages.services.ServicesPage;
import com.pages.services.electricalWork.*;
import com.tests.base.MobileBaseTest;
import jdk.jfr.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServicesVisibleTest extends MobileBaseTest {

    @Test
    @DisplayName("Проверка работы блока 'Услуги'")
    @Description("Создание заявки в блоке 'Услуги'")
    public void testVisibleServices() {

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
        assertTrue(registrationApplicationPage.isPageLoaded(), "Окно 'Оформление заявки' услуги Установка бра не загрузилась");

        // объект WindowRegistrationCompletedPage
        WindowRegistrationCompletedPage windowRegistrationCompletedPage = registrationApplicationPage.sendRequest();
        assertTrue(windowRegistrationCompletedPage.isPageLoaded(), "Успешное оформление заявки на услугу");

        // проверка соответствия комментария с информацией в БД
        String actualComment = registrationApplicationPage.textComment;
        String fact = registrationApplicationPage.getTextInBD();
        assertTrue(fact.contains(actualComment), "Текст комментария НЕ совпадает с БД");
    }
}
