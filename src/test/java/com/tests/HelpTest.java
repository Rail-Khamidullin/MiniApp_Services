package com.tests;

import com.pages.MainPage;
import jdk.jfr.Description;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HelpTest extends BaseTest {

    @Test
    @DisplayName("Проверка работы блока 'Помощь'")
    @Description("Проверка отображения блока 'Часто задаваемые вопросы'")
    public void testAskedQuestions() {
        // Создаем объект главного окна МиниАпп "Услуги"
        MainPage mainPage = new MainPage(page);
        // Проверяем отображение блока 'Помощь'
        assertTrue(mainPage.tapToHelp().isPageLoaded(),
                "Окно 'Помощь' не загрузилось");
        // Проверяем отображение окна 'Часто задаваемые вопросы'
        assertTrue(mainPage.tapToHelp().openAskedQuestions().isPageLoaded(),
                "Окно 'Часто задаваемые вопросы' не загрузилось");
        // Проверяем работу кнопок с вопросами окна 'Часто задаваемые вопросы'
        assertTrue(mainPage.tapToHelp().openAskedQuestions().openCloseQuestion(),
                "Кнопки с вопросами не работают");
    }

    @Test
    @DisplayName("Проверка работы блока 'Помощь'")
    @Description("Проверка открытие файлов окна 'О приложении'")
    public void testAboutApp() {
        // Создаем объект главного окна МиниАпп "Услуги"
        MainPage mainPage = new MainPage(page);
        // Проверяем отображение блока 'Помощь'
        assertTrue(mainPage.tapToHelp().isPageLoaded(),
                "Окно 'Помощь' не загрузилось");
        // Проверяем отображение окна 'О приложении'
        assertTrue(mainPage.tapToHelp().openAboutApp().isPageLoaded(),
                "Окно 'О приложении' не загрузилось");
        // Проверяем открытие файлов окна 'О приложении'
        assertTrue(mainPage.tapToHelp().openAboutApp().openFile(), "Документы окна 'О приложении' не отображается");
    }
}