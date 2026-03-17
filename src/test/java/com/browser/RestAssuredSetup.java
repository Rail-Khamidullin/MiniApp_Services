package com.browser;

import com.microsoft.playwright.Page;
import groovy.util.logging.Slf4j;
import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Slf4j
@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class RestAssuredSetup {

    private Page page;
    @NonNull
    private final String authURL;

    // Статический метод для настройки SSL (не требуют создания объекта)
    public static void disableSslValidation() {
        RestAssured.config = RestAssured.config()
                .sslConfig(SSLConfig.sslConfig().relaxedHTTPSValidation());
        System.out.println("✅ SSL validation disabled for RestAssured");
    }

    // Метод для навигации на страницу авторизации
    public void navigateToAuthPage() {
        if (page == null) {
            throw new IllegalStateException("Page is not initialized. Use constructor with Page parameter.");
        }
        page.navigate(authURL);
        System.out.println("🌐 Navigated to auth page: " + authURL);
    }
}
