package com.utils;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import java.util.Map;
import static com.data.InitialData.*;
import static io.restassured.RestAssured.given;
import io.restassured.http.ContentType;

public class TokenAuthorization {

    @Step("Получение токена")
    public static String getToken() {
        Map<String, String> createBody = Map.of("login", LOGIN, "password", PASSWORD);
        RestAssured.useRelaxedHTTPSValidation();
        return  given()
                .when()
                .body(createBody)
                .contentType(ContentType.JSON)
                .post(URL_AUTH_14 + "api/users/auth/login")
                .then()
                .extract().response().jsonPath().get("accessToken").toString();
    }
}