package testsCourier;

import courier.*;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TestsLogin {
    private final RandomCourier randomCourier = new RandomCourier();
    private  AssertCourier assertCourier;
    private int courierID;
    private Courier courier;
    private PartCourier partCourier;
    private ModelCourier modelCourier;

    @Before
    @Step("Создание тестовых данных для  курьера")
    public void setUp() {
        partCourier = new PartCourier();
        modelCourier = randomCourier.createNewRandomCourier();
        partCourier.createCourier(modelCourier);
        courier = Courier.from(modelCourier);
        assertCourier = new AssertCourier();
    }

    @Test
    @DisplayName("Логин курьера успешен")
    @Description("Проверка, что курьер может войти в систему с корректными данными")
    public void courierLoginValid() {
        ValidatableResponse responseLoginCourier = partCourier.loginCourier(courier);
        assertCourier.loginCourier(responseLoginCourier);
        courierID = responseLoginCourier.extract().path("id");
    }

    @Test
    @DisplayName("Логин курьера с пустым полем логина")
    @Description("Проверка, что курьер не может войти в систему без логина")
    public void courierLoginErrorEmptyLogin() {
        Courier courierCredsWithoutLogin = new Courier("", modelCourier.getPassword()); // c null тесты виснут
        ValidatableResponse responseLoginErrorMessage = partCourier.loginCourier(courierCredsWithoutLogin);
        assertCourier.loginCourierError(responseLoginErrorMessage);

    }

    @Test
    @DisplayName("Логин курьера с пустым полем пароля")
    @Description("Проверка что курьер не может войти в систему без пароля")
    public void courierLoginErrorEmptyPassword() {
        Courier courierCredsWithoutPass = new Courier(modelCourier.getLogin(), "");
        ValidatableResponse responseLoginErrorMessage = partCourier.loginCourier(courierCredsWithoutPass);
        assertCourier.loginCourierError(responseLoginErrorMessage);
    }

    @Test
    @DisplayName("Логин курьера с пустым полями логина и пароля")
    @Description("Проверяем, что курьер не может войти в систему без логина и пароля")
    public void courierLoginErrorEmptyLoginAndPassword() {
        Courier courierCredsWithoutLoginAndPassword = new Courier("", "");
        ValidatableResponse responseLoginErrorMessage = partCourier.loginCourier(courierCredsWithoutLoginAndPassword);
        assertCourier.loginCourierError(responseLoginErrorMessage);
    }

    @Test
    @DisplayName("Курьер c некорректным логином")
    @Description("Проверка, что курьер не может войти в систему с  не зарегистрированным логином")
    public void courierLoginErrorAccountNotFound() {
        Courier courierCredsErrorAccountNotFound = new Courier(RandomCourier.NEW_LOGIN_FAKED, modelCourier.getPassword());
        ValidatableResponse responseLoginErrorMessage = partCourier.loginCourier(courierCredsErrorAccountNotFound);
        assertCourier.loginCourierNotFound(responseLoginErrorMessage);
    }

    @After
    @Step("Удаление курьера")
    public void deleteCourier() {
        if (courierID != 0) {
            partCourier.deleteCourier(courierID);
        }
    }
}
