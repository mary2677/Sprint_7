package order;


import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static constant.ScooterEndpoints.*;
import static io.restassured.RestAssured.given;

public class PartOrder {
    public static RequestSpecification requestSpec() {
        return given().log().all()
                .contentType(ContentType.JSON)
                .baseUri(URL);
    }

    @Step("Создание заказа")
    public ValidatableResponse createNewOrder(ModelOrder modelOrder) {
        return requestSpec()
                .body(modelOrder)
                .when()
                .post(ORDER_CREATE)
                .then();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrderList() {
        return requestSpec()
                .when()
                .get(ORDER_LIST)
                .then();
    }

    @Step("Отмена заказа")
    public ValidatableResponse cancelOrder(int track) {
        return requestSpec()
                .body(track)
                .when()
                .put(ORDER_CANCEL)
                .then();
    }
}
