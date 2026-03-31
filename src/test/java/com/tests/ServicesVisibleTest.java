package com.tests;

import com.pages.MainPage;
import com.pages.services.ServicesPage;
import com.pages.services.electricalWork.LampInstallation;
import com.pages.services.electricalWork.LightingDevices;
import com.pages.services.electricalWork.TypeElectricalWorkPage;
import jdk.jfr.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class ServicesVisibleTest extends BaseTest {

    @Test
    @DisplayName("Проверка отображения окна 'Услуги'")
    @Description("Проверка отображения блока 'Часто задаваемые вопросы'")
    public void testVisibleServices() {

        MainPage mainPage = new MainPage(page);
        ServicesPage servicesPage = mainPage.tapToServices();
        assertTrue(servicesPage.isPageLoaded(), "Окно 'Услуги' не загрузилось");

        // объект TypeElectricalWorkPage
        TypeElectricalWorkPage electricalWorkPage = servicesPage.tapToConcreteService();
        assertTrue(electricalWorkPage.isPageLoaded(), "Окно 'Электромонтажные работы' не загрузилось");

        // объект LightingDevices
        LightingDevices lightingDevices = electricalWorkPage.tapToLightingDevices();
        assertTrue(lightingDevices.isPageLoaded(), "Окно 'Приборы освещения' не загрузилось");

        // объект LampInstallation
        LampInstallation lampInstallation = lightingDevices.tapToLampInstallation();
        assertTrue(lampInstallation.isPageLoaded(), "Окно 'Установка бра' не загрузилось");
        // проверяем контент
        assertTrue(lampInstallation.verifyContent(), "Описание услуги 'Установка бра' не верное");

        /*
        // Создаем объект главного окна МиниАпп "Услуги"
        MainPage mainPage = new MainPage(page);

        ServicesPage servicesPage = mainPage.tapToServices();

        // Проверяем отображение окна 'Услуги'
        assertTrue(servicesPage
                        .isPageLoaded(),
                "Окно 'Услуги' не загрузилось");

        // Проверяем отображение услуг окна 'Электромонтажные работы'
        assertTrue(servicesPage
                        .tapToConcreteService()
                        .isPageLoaded(),
                "Окно 'Электромонтажные работы' не загрузилось");

        // Проверяем отображение работу окна 'Приборы освещения'
        assertTrue(servicesPage
                        .tapToConcreteService()
                        .tapToLightingDevices()
                        .isPageLoaded(),
                "Окно 'Приборы освещения' не загрузилось");

        // Проверяем отображение работу окна 'Установка бра'
        assertTrue(servicesPage
                        .tapToConcreteService()
                        .tapToLightingDevices()
                        .tapToLampInstallation()
                        .isPageLoaded(),
                "Окно 'Установка бра' не загрузилось");

        // Проверяем описание услуги и цену 'Установка бра'
        assertTrue(servicesPage
                        .tapToConcreteService()
                        .tapToLightingDevices()
                        .tapToLampInstallation()
                        .verifyContent(),
                "Описание услуги 'Установка бра' не верное");
 */
    }
}
