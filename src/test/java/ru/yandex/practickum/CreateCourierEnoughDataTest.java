package ru.yandex.practickum;

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

    @Parameterized.Parameters
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
    public void createCourierErrorStatusCode() {
        int status = courierApiClient.createCourier(courier).extract().statusCode();
        assertThat("Status code is 400", status, equalTo(HttpStatus.SC_BAD_REQUEST));
    }

    @Test
    public void createCourierErrorText() {
        String message = courierApiClient.createCourier(courier).extract().path("message");
        assertThat("Message error is NotEnoughData", message, equalTo(CREATE_COURIER_NOT_ENOUGH_DATA));
    }

}
