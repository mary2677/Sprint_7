package courier;
import constant.*;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static constant.ScooterEndpoints.*;
import static io.restassured.RestAssured.given;

public class PartCourier {
    public static RequestSpecification requestSpec() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(ScooterEndpoints.URL);
    }

    @Step("Регистрация нового курьера")
    public ValidatableResponse createCourier(ModelCourier modelCourier) {
        return requestSpec()
                .body(modelCourier)
                .when()
                .post(COURIER_CREATE)
                .then();
    }

    @Step("Авторизация курьера")
    public ValidatableResponse loginCourier(Courier courier) {
        return requestSpec()
                .body(courier)
                .when()
                .post(COURIER_LOGIN)
                .then();
    }

    @Step("Удаление курьера")
    public ValidatableResponse deleteCourier(int courierId) {
        return requestSpec()
                .when()
                .delete(DELETE + courierId)
                .then();
    }
}
