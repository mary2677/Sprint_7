package TestsCourier;

import courier.*;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class CreateCourier {
    protected final RandomCourier courierRandom = new RandomCourier();
    int courierId;
    private PartCourier partCourier;
    private ModelCourier modelCourier;
    private AssertCourier assertCourier;

    @Before
    @Step("Создание курьера")
    public void setUp() {
        partCourier = new PartCourier();
        modelCourier = courierRandom.createNewRandomCourier();
        assertCourier = new AssertCourier();
    }

    @After
    @Step("Удаление курьера")
    public void deleteCourier() {
        if (courierId != 0) {
            partCourier.deleteCourier(courierId);
        }
    }

    @Test
    @DisplayName("Создание курьера")
    @Description("Проверка, есть ли такой курьер")
    public void courierCanBeCreated() {
        ValidatableResponse responseCreateCourier = partCourier.createCourier(modelCourier);
        Courier courier = Courier.from(modelCourier);
        courierId = partCourier.loginCourier(courier).extract().path("id");
        assertCourier.createCourier(responseCreateCourier);
    }

    @Test
    @DisplayName("Пустой логин")
    @Description("Проверка на создание курьера без логина")
    public void courierCanNotBeCreatedWithoutLogin() {
        modelCourier.setLogin(null);
        ValidatableResponse responseNullLogin = partCourier.createCourier(modelCourier);
        assertCourier.CourierError(responseNullLogin);
    }

    @Test
    @DisplayName("Создание курьера с пустым полем пароля")
    @Description("Проверка на создание курьера без пароля")
    public void courierCanNotBeCreatedWithoutPassword() {
        modelCourier.setPassword(null);
        ValidatableResponse responseNullPassword = partCourier.createCourier(modelCourier);
        assertCourier.CourierError(responseNullPassword);
    }

    @Test
    @DisplayName("Создание курьера с пустым полем логина и пароля")
    @Description("Проверка на создание курьера без логина и пароля")
    public void courierCanNotBeCreatedWithoutLoginAndPassword() {
        modelCourier.setLogin(null);
        modelCourier.setPassword(null);
        ValidatableResponse responseNullFields = partCourier.createCourier(modelCourier);
        assertCourier.CourierError(responseNullFields);
    }

    @Test
    @DisplayName("Создание курьера с ранее зарегистрированным логином")
    @Description("Проверяем, что курьера нельзя создать с ранее созданным логином")
    public void courierCanNotBeCreatedWithSameLogin() {
        partCourier.createCourier(modelCourier);
        ValidatableResponse responseCreateCourier = partCourier.createCourier(modelCourier);
        assertCourier.CourierLoginError(responseCreateCourier);
    }
}
