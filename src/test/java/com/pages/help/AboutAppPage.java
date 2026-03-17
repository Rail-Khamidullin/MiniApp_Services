package com.pages.help;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.pages.BasePage;
import io.qameta.allure.Step;
import java.nio.file.Paths;
import static com.data.TextHelpWindow.AGREEMENT_FILE;
import static com.data.TextHelpWindow.MANUAL_FILE;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class AboutAppPage extends BasePage {

    private final Locator help;                     // заголовок 1 "Помощь"
    private final Locator aboutApp;                 // заголовок 2 "О приложении"
    private final Locator onMainButton;             // кнопка "На главную"
    private final Locator userManual;               // документ "Руководство пользователя"
    private final Locator agreementToProcessing;    // документ "Согласие на обработку ПД"
    private final Locator headerManual;             // файл "О приложении"
    private final Locator headerAgreement;          // файл "Согласие на обработку ПД"
    private final Locator closeButtonFile;          // закрытие страницы файла "О приложении" или "Согласие на обработку ПД"

    public AboutAppPage(Page page) {
        super(page);
        this.help = page.locator("text='Помощь'");
        this.aboutApp = page.locator("text='О приложении'");
        this.onMainButton = page.locator("text='На главную'");
        this.userManual = page.locator("text='Руководство пользователя'");
        this.agreementToProcessing = page.locator("text='Согласие на обработку ПД'");
        this.headerManual = page.locator("//h2[contains(@class, 'MuiDialogTitle-root')]/p[contains(text(), 'Руководство пользователя')]");
        this.headerAgreement = page.locator("//h2[contains(@class, 'MuiDialogTitle-root')]/p[contains(text(), 'Согласие на обработку ПД')]");
        this.closeButtonFile = page.locator("//button[contains(@class, 'MuiIconButton-sizeMedium css-mfslm7')]");
    }

    @Override
    @Step("Проверка отображения всех элементов окна 'Часто задаваемые вопросы'")
    public boolean isPageLoaded() {
        String locatorName = "Пусто";
        // Ждем появления кнопок (с таймаутом 10 секунд)
        try {
            locatorName = "help";
            help.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "aboutApp";
            aboutApp.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "onMainButton";
            onMainButton.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "userManual";
            userManual.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "agreementToProcessing";
            agreementToProcessing.waitFor(new Locator.WaitForOptions().setTimeout(10000));

            return true; // если исключения не было, элемент найден
        } catch (Exception e) {
            System.out.println("Элемент не найден: " + locatorName);
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/AboutAppPage/isPageLoaded.png")));
            return false; // элемент не найден за 10 секунд
        }
    }

    @Step("Открытие документа 'Руководство пользователя'")
    public boolean openFile() {
        if (checkFile(userManual, headerManual, MANUAL_FILE)) {
            if (checkFile(agreementToProcessing, headerAgreement, AGREEMENT_FILE)) {
                return true;
            }
        }
        page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/AboutAppPage/openManual.png")));
        return false;
    }

    @Step("Открытие документа 'Согласие на обработку ПД'")
    public boolean checkFile(Locator nameFile, Locator textHeader, String textExpend) {
        boolean fileIsOpen = true;
        nameFile.click();
        String currentText = textHeader.textContent();
        System.out.println(currentText);
        if (!currentText.equals(textExpend)) {
            fileIsOpen = false;
        }
        assertEquals(currentText, textExpend, "Текст не соответствует требованиям");
        closeButtonFile.click();
        return fileIsOpen;
    }
}
