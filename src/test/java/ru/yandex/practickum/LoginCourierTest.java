package ru.yandex.practickum;

import client.CourierApiClient;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.*;
import model.Courier;
import io.qameta.allure.junit4.DisplayName;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTest {
    CourierApiClient courierApiClient = new CourierApiClient();
    Courier courierNew = new Courier("courier-test", "1234", "Jack");

    @Before
    public void createCourier() {
        courierApiClient.createCourier(courierNew);
    }

    @Test
    @DisplayName("Авторизация существующего пользователя")
    public void loginCourierStatusCode200AndReturnId() {
        Courier courier = new Courier("courier-test", "1234");
        ValidatableResponse response = courierApiClient.getCourier(courier);
        int status = response.extract().statusCode();
        assertThat("Status code is 200", status, equalTo(HttpStatus.SC_OK));
        String courierId = response.extract().path("id").toString();
        assertThat("Return id", courierId, notNullValue());
    }

    @After
    public void deleteCourier() {
        int id = courierApiClient.getCourier(courierNew).extract().path("id");
        courierApiClient.deleteCourier(String.valueOf(id));
    }
}
