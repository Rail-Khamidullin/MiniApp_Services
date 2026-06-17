package com.pages.services.electricalWork;

import com.microsoft.playwright.Locator;
import com.microsoft.playwright.Page;
import com.pages.BasePage;
import com.pages.services.enums.BonusOption;
import io.qameta.allure.Step;
import java.nio.file.Paths;
import static com.utils.BasePageFactory.openPage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RegistrationApplicationPage extends BasePage {

    private static final Logger logger = LoggerFactory.getLogger(RegistrationApplicationPage.class);

    // Локаторы (инициализируем в конструкторе)
    private final Locator onMainButton;                  // кнопка "На главную"
    private final Locator buttonBack;                    // кнопка "Назад"
    private final Locator mainHeader;                    // главный заголовок "Оформление заявки"
    private final Locator header;                        // заголовок
    private final Locator sendRequestButton;             // кнопка "Оформить заявку"
    private final Locator quantityBonus;                 // кол-во экобонусов
    private final Locator willAddBonus;                  // кол-во экобонусов, которые начислятся после оформления
    private final Locator useBonus;                      // кол-во экобонусов для использования
    private final Locator dataField;                     // поле с выбором даты выпол. заявки
    private final Locator timeField;                     // поле с выбором времени заявки
    private final Locator setNewMonth;                   // кнопка выбора следующего месяца
    private final Locator okButton;                      // кнопка подтверждения
    private final Locator cancelButton;                  // отмена выбора
    private final Locator timeInterval;                  // выбор поля временного интервала
    private final Locator setTime;                       // установка временного интервала
    private final Locator cancelTime;                    // отмена выбранного времен. интервала
    private final Locator comments;                      // поле для заполнения комментария
    private final Locator switchButton;                  // переключатель для использования бонусов в качестве оплаты
    private SupportRegistrationPage supportRegistrationPage = new SupportRegistrationPage(page);
    public String textComment;                           // текст, который будет добавлен в поле комментарий
    public Double currentBonuses;                        // кол-во бонусов после оформления услуги

    public RegistrationApplicationPage(Page page) {
        super(page);
        this.onMainButton = nameLocator("На главную");
        this.buttonBack = page.locator("//div[contains(@id, 'header')]//button[contains(@class, 'MuiIconButton-root')]");
        this.mainHeader = nameLocator("Установка бра");
        this.header = nameLocator("Оформление заявки");
        this.sendRequestButton = nameLocator("Отправить заявку");
        this.quantityBonus = page.locator("//div[contains(@class, 'MuiBox-root')]//div[@class = 'css-mgcte9']//p[contains(@class, 'MuiTypography-body1')]");
        this.willAddBonus = page.locator("//div[@class = 'css-rpim9t']//p[contains(@class, 'css-1q8gi9y')]");
        this.useBonus = page.locator("//span[@class = 'css-1xqhkmm']");
        this.dataField = page.locator("//div[contains(@class, 'MuiPickersInputBase-sectionsContainer')]");
        this.timeField = page.locator("//input[@class ='css-2qh9zc']");
        this.setNewMonth = page.locator("//div[contains(@class, 'MuiPickersArrowSwitcher-root')]//button[contains(@class, 'MuiPickersArrowSwitcher-nextIconButton')]");
        this.okButton = nameLocator("Ок");
        this.cancelButton = nameLocator("Отмена");
        this.timeInterval = page.locator("//div[@class ='mbsc-timegrid-row']//div[contains(text(), '09:00')]");
        this.setTime = nameLocator("Установить");
        this.cancelTime = nameLocator("Отмена");
        this.comments = page.locator("//label[text()='Комментарий']/following-sibling::div//textarea");
        this.switchButton = page.locator("//input[contains(@class,'PrivateSwitchBase-input')]");
    }

    @Override
    @Step("Проверка отображение объектов окна 'Услуги'")
    public boolean isPageLoaded() {
        String locatorName = "Пусто";
        // Ждем появления кнопок (с таймаутом 10 секунд)
        try {
            locatorName = "onMainButton";
            onMainButton.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "buttonBack";
            buttonBack.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "mainHeader";
            mainHeader.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "header";
            header.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "quantityBonus";
            quantityBonus.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "dataField";
            dataField.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "timeField";
            timeField.waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "comments";
            comments.first().waitFor(new Locator.WaitForOptions().setTimeout(10000));
            locatorName = "submitButton";
            sendRequestButton.waitFor(new Locator.WaitForOptions().setTimeout(10000));

            return true;              // если исключения не было, элемент найден
        } catch (Exception e) {
            logger.error("Элемент не найден: {}", locatorName);
            // скриншот, в случае падения теста
            page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("screenshots/TypeElectricalWorkPage/LightingDevices/LampInstallationPage/RegistrationApplicationPage_error.png")));
            return false;             // элемент не найден за 10 секунд
        }
    }

    @Step("Оформление заявки")
    public WindowRegistrationCompletedPage registrationWithBonus(BonusOption bonusOption, String time) {
        if (bonusOption == BonusOption.NOT_USE_BONUSES) {
            switchButton.click();                                                     // отключаем использование бонусов
        }
        dataField.click();                                                            // выбор поля с датой
        int setDay = supportRegistrationPage.setDay(setNewMonth);                     // получаем следующий день для выбора дня получения услуги
        page.locator("button[role='gridcell']:text-is('" + setDay + "'):not([disabled])").click();
        okButton.click();                                                             // принимаем дату
        supportRegistrationPage.setTimeInterval(timeField,
                time,
                setTime);                                                             // устанавливаем временной интервал в формате 09:30
        textComment = supportRegistrationPage.setTextInComment(comments.first());     // вводим комментарий
        currentBonuses = futureBonuses();
        return openPage(sendRequestButton,                                            // выбираем кнопку "Отправить заявку"
                page,
                WindowRegistrationCompletedPage.class);
    }

    // считаем будущее количество бонусов после оформления услуги
    private Double futureBonuses () {
        if (quantityBonus.count() > 0 || useBonus.count() > 0 || willAddBonus.count() > 0) {
            try {
                Double allBonuses = Double.parseDouble(quantityBonus.textContent());   // текущее кол-во бонусов
                Double useBonuses = Double.parseDouble(useBonus.textContent());        // используемое кол-во бонусов
//                Double newBonuses = Double.parseDouble(willAddBonus.textContent());  // кол-во бонусов кэшбэком
//                Double current = allBonuses - useBonuses + newBonuses;
                Double current = allBonuses - useBonuses;
                logger.info("Registration = {}", current);
                return current;
            } catch (Exception e) {
                return 0.0;
            }
        }
        return 0.0;
    }
}
