package ru.yandex.practickum;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import client.CourierApiClient;
import model.Courier;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static ru.yandex.practickum.Constants.CREATE_COURIER_NOT_ENOUGH_DATA;

@RunWith(Parameterized.class)
public class CreateCourierEnoughDataTest {

    Courier courier;
    CourierApiClient courierApiClient = new CourierApiClient();

    public CreateCourierEnoughDataTest(Courier courier) {
        this.courier = courier;
    }

    @Parameterized.Parameters(name = "Создание курьера. Тестовые данные: {0}")
    public static Object[][] getCreateCourierDate() {
        return new Object[][]{
                {new Courier("courier-test", null)},
                {new Courier(null, "1234")},
                {new Courier(null, null, "Jack")},
                {new Courier(null, null)},
                {new Courier("courier-test", null, "Jack")},
                {new Courier(null, "1234", "Jack")},
        };
    }

    @Test
    @DisplayName("Создание курьера. Код 400. Сообщение \"Недостаточно данных\"")
    public void createCourierErrorNotEnoughData() {
        ValidatableResponse response = courierApiClient.createCourier(courier);
        int status = response.extract().statusCode();
        assertThat("Status code is 400", status, equalTo(HttpStatus.SC_BAD_REQUEST));
        String message = response.extract().path("message");
        assertThat("Message error is NotEnoughData", message, equalTo(CREATE_COURIER_NOT_ENOUGH_DATA));
    }

}
