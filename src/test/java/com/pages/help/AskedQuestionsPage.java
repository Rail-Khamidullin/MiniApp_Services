package com.pages.help;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.pages.BasePage;
import io.qameta.allure.Step;
import java.nio.file.Paths;
import static com.data.TextHelpWindow.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AskedQuestionsPage extends BasePage {

    private final Locator help;                 // заголовок 1 "Помощь"
    private final Locator askedQuestions;       // заголовок 2 "Часто задаваемые вопросы"
    private final Locator questionOne;          // кнопка "Вопрос #1"
    private final Locator questionTwo;          // кнопка "Вопрос #2"
    private final Locator questionThree;        // кнопка "Вопрос #3"
    private final Locator questionFore;         // кнопка "Вопрос #4"
    private final Locator onMainButton;         // кнопка "На главную"
    // локтор описания вопросов
    Locator textOnBlock = page.locator(".MuiAccordionDetails-root p");

    public AskedQuestionsPage(Page page) {
        super(page);
        this.help = page.locator("text='Помощь'");
        this.askedQuestions = page.locator("text='Часто задаваемые вопросы'");
        this.questionOne = page.locator("//span[text()='Вопрос #1']/ancestor::button");
        this.questionTwo = page.locator("//span[text()='Вопрос #2']/ancestor::button");
        this.questionThree = page.locator("//span[text()='Вопрос #3']/ancestor::button");
        this.questionFore = page.locator("//span[text()='Вопрос #4']/ancestor::button");
        this.onMainButton = page.locator("text='На главную'");
    }

    @Override
    @Step("Проверка отображения всех элементов окна 'Часто задаваемые вопросы'")
    public boolean isPageLoaded() {
        String locatorName = "Пусто";
        // Ждем появления кнопок (с таймаутом 10 секунд)
        try {
            locatorName = "help";
            help.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "askedQuestions";
            askedQuestions.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "questionOne";
            questionOne.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "questionTwo";
            questionTwo.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "questionThree";
            questionThree.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "questionFore";
            questionFore.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "onMainButton";
            onMainButton.waitFor(new Locator.WaitForOptions().setTimeout(10000));

            return true; // если исключения не было, элемент найден
        } catch (Exception e) {
            System.out.println("Элемент не найден: " + locatorName);
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/AskedQuestionsPage/isPageLoaded.png")));
            return false; // элемент не найден за 10 секунд
        }
    }

    @Step("Открытие и закрытие вопросов и проверка текста в окне 'Часто задаваемые вопросы'")
    public boolean openCloseQuestion() {

        if (checkWorkQuestionBlocks(questionOne, textOnBlock.first(), QUESTION_ONE_TEXT)) {
            if (checkWorkQuestionBlocks(questionTwo, textOnBlock.nth(1), QUESTION_TWO_TEXT)) {
                if (checkWorkQuestionBlocks(questionThree, textOnBlock.nth(2), QUESTION_THREE_TEXT)) {
                    if (checkWorkQuestionBlocks(questionThree, textOnBlock.nth(3), QUESTION_THREE_TEXT_TWO)) {
                        if (checkWorkQuestionBlocks(questionFore, textOnBlock.nth(4), QUESTION_FORE_TEXT)) {
                            return true;
                        }
                    }
                }
            }
        }
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/AskedQuestionsPage/errorOnQuestion.png")));
        return false;
    }

    private boolean checkWorkQuestionBlocks(Locator nameQuestion, Locator textDescription, String textExpend) {
        if("false".equals(nameQuestion.getAttribute("aria-expanded"))) {
            nameQuestion.click();
            String currentText = textDescription.textContent();
            System.out.println(currentText);
            if(!currentText.equals(textExpend)) {
                page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/AskedQuestionsPage/questionText.png")));
            }
            assertEquals(currentText, textExpend, "Текст не соответствует требованиям");
            nameQuestion.click();
            return "false".equals(nameQuestion.getAttribute("aria-expanded"));
        }
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/AskedQuestionsPage/questionBlocksError.png")));
        return false;
    }
}
