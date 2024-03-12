package testsOrder;

import order.*;
import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderListTest {
    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверка, что список заказов успешно получен")
    public void getOrderList() {
        PartOrder partOrder = new PartOrder();
        ValidatableResponse responseOrderList = partOrder.getOrderList();
        responseOrderList.assertThat()
                .statusCode(200)
                .body("orders", notNullValue());
    }
}
