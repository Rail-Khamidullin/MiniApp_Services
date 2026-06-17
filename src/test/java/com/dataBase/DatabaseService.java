package com.dataBase;

import com.api.GetServicesDate;
import com.microsoft.playwright.Page;
import io.qameta.allure.Step;
import java.io.IOException;

public class DatabaseService {
    @Step("Получение описания созданной заявки из БД")
    public String getTextInBD(Page page) {
        try {
            // Добавляем задержку перед запросом
            page.waitForTimeout(3000);

            GetServicesDate getServicesDate = new GetServicesDate();
            String text = getServicesDate.getDescription(getServicesDate.servicesList());
            System.out.println(text);
            return text;
        } catch (IOException e) {
            e.printStackTrace();
            return "Ошибка при получении данных: " + e.getMessage();
        }
    }
}
