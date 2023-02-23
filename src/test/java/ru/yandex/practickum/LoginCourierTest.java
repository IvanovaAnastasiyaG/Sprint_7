package ru.yandex.practickum;

import org.apache.http.HttpStatus;
import org.junit.*;
import client.CourierApiClient;
import model.Courier;

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
    public void loginCourierStatusCode200() {
        Courier courier = new Courier("courier-test", "1234");
        int status = courierApiClient.getCourier(courier).extract().statusCode();
        assertThat("Status code is 200", status, equalTo(HttpStatus.SC_OK));
    }

    @Test
    public void loginCourierReturnId() {
        Courier courier = new Courier("courier-test", "1234");
        String courierId = courierApiClient.getCourier(courier).extract().path("id").toString();
        assertThat("Return id", courierId, notNullValue());
    }

    @After
    public void deleteCourier() {
        int id = courierApiClient.getCourier(courierNew).extract().path("id");
        courierApiClient.deleteCourier(String.valueOf(id));
    }
}
