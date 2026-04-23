package com.api;

import com.tests.base.MobileBaseTest;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.io.IOException;
import static com.data.InitialData.URL_AUTH_14;
import static io.restassured.RestAssured.given;

public class GetServicesDate {

    /// === Запрос списка созданных услуг ===

    @Step("Запрос списка созданных услуг")
    public Response servicesList() throws IOException {
        Response response =
                given()
                        .header("Authorization", "Bearer " + MobileBaseTest.TOKEN)
                        .log().all()
                        .when()
                        .contentType(ContentType.JSON)
                        .post(URL_AUTH_14 + "api/client-relations/application/" +
                                "filtering?sort=createdDate," +
                                "desc&page=0&size=25&showArchive=false");

        return response;
    }

    @Step("Получение успешного ответа и комментария в заявке")
    public String getDescription(Response responseList) {
        return responseList.then().assertThat()
                .statusCode(200)
                .extract().response()
                .jsonPath()
                .getString("content[0].description");
    }
}
