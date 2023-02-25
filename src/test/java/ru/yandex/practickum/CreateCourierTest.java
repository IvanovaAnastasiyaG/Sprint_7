package ru.yandex.practickum;

import client.CourierApiClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import model.Courier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static ru.yandex.practickum.Constants.CREATE_COURIER_LOGIN_ALREADY_USE;

@RunWith(Parameterized.class)
public class CreateCourierTest {

    Courier courier;

    CourierApiClient courierApiClient = new CourierApiClient();

    public CreateCourierTest(Courier courier) {
        this.courier = courier;
    }

    @Parameterized.Parameters(name = "Создание курьера. Тестовые данные: {0}")
    public static Object[][] getCreateCourierDate() {
        return new Object[][]{
                {new Courier("courier-test", "1234", "Jack")},
                {new Courier("courier-test", "1234", null)},
        };
    }

    @Test
    @DisplayName("Создание курьера. Код 201. Id существует")
    public void createNewCourierIsCreate() {
        ValidatableResponse response = courierApiClient.createCourier(courier);
        int status = response.extract().statusCode();
        assertThat("Status code is 201", status, equalTo(HttpStatus.SC_CREATED));
        String courierId = courierApiClient.getCourier(courier).extract().path("id").toString();
        assertThat("Courier's id is exist", courierId, notNullValue());
    }

    @Test
    @DisplayName("Создание курьера. Код 201. Возвращено Ок")
    public void createNewCourierReturnOk() {
        ValidatableResponse response = courierApiClient.createCourier(courier);
        int status = response.extract().statusCode();
        assertThat("Status code is 201", status, equalTo(HttpStatus.SC_CREATED));
        boolean ok = response.extract().path("ok");
        assertThat("Success request is ok", ok, equalTo(true));
    }

    @Test
    @DisplayName("Создание существующего пользователя. Код 409. Сообщение \"Логин уже существует\"")
    public void createExistCourierRequiredFieldsReturnIsExist() {
        courierApiClient.createCourier(courier);
        ValidatableResponse response = courierApiClient.createCourier(courier);
        int status = response.extract().statusCode();
        assertThat("Status code is 409", status, equalTo(HttpStatus.SC_CONFLICT));
        String message = response.extract().path("message");
        assertThat("Message error is login already in use", message, equalTo(CREATE_COURIER_LOGIN_ALREADY_USE));
    }

    @After
    public void deleteCourier() {
        int id = courierApiClient.getCourier(courier).extract().path("id");
        courierApiClient.deleteCourier(String.valueOf(id));
    }

}
