package com.tests;

import com.pages.MainPage;
import jdk.jfr.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest extends BaseTest {

    @Test
    @DisplayName("Открытие главного окна МиниАпп 'Услуги'")
    @Description("Проверка отображение блока 'Услуги'")
    public void testServices() {
        // Создаем объект главного окна МиниАпп "Услуги"
        MainPage mainPage = new MainPage(page);

        // Проверяем отображение 'Каталог услуг' в блоке 'Услуги'
        assertTrue(mainPage.isPageLoaded(),
                "Страница 'Услуг' не загрузилась");
    }
}
