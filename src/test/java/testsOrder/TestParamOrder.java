package testsOrder;

import order.*;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;


import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;

@RunWith(Parameterized.class)
public class TestParamOrder {
    private final List<String> colour;
    private int track;
    private PartOrder partOrder;

    public TestParamOrder(List<String> colour) {
        this.colour = colour;
    }
    @Parameterized.Parameters(name = "Цвет самоката: {0}")
    public static Object[][] getScooterColour() {
        return new Object[][]{
                {List.of("BLACK")},
                {List.of("GRAY")},
                {List.of("BLACK, GRAY")},
                {List.of()}
        };
    }
    @Before
    public void setUp() {
        partOrder = new PartOrder();
    }

    @After
    @Step("Cancel test order")
    public void CancelTestOrder() {
        partOrder.cancelOrder(track);
    }

    @Test
    @DisplayName("Размещение заказа с самокатами разных цветов")
    @Description("Проверка на корректность размещения заказа с самокатами разных цветов")
    public void OrderingWithScootersInDifferentColors() {
        ModelOrder modelOrder = new ModelOrder(colour);
        ValidatableResponse responseCreateOrder = partOrder.createNewOrder(modelOrder);
        track = responseCreateOrder.extract().path("track");
        responseCreateOrder.assertThat()
                .statusCode(201)
                .body("track", is(notNullValue()));
    }

}
