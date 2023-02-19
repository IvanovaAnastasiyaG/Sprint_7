package ru.yandex.practickum;

import client.CourierApiClient;
import model.Courier;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static ru.yandex.practickum.Constants.*;

@RunWith(Parameterized.class)
public class LoginCourierErrorsTest {

    CourierApiClient courierApiClient = new CourierApiClient();
    Courier courierNew = new Courier("courier-test", "1234", "Jack");
    Courier courier;
    String messageExpected;
    int statusCodeExpected;

    public LoginCourierErrorsTest(Courier courier, int statusCode, String message) {
        this.courier = courier;
        this.messageExpected = message;
        this.statusCodeExpected = statusCode;
    }

    @Before
    public void createCourier(){
        courierApiClient.createCourier(courierNew);
    }

    @Parameterized.Parameters
    public static Object[][] getCreateCourierDate() {
        return new Object[][] {
                { new Courier("courierTest", "1234"), HttpStatus.SC_NOT_FOUND, LOGIN_COURIER_ACCOUNT_NOT_FOUND},
                { new Courier("courier-test", "2345"), HttpStatus.SC_NOT_FOUND, LOGIN_COURIER_ACCOUNT_NOT_FOUND},
                { new Courier("courier-test", null), HttpStatus.SC_BAD_REQUEST, LOGIN_COURIER_NOT_ENOUGH_DATA},
                { new Courier(null, "1234"), HttpStatus.SC_BAD_REQUEST, LOGIN_COURIER_NOT_ENOUGH_DATA},
        };
    }

    @Test
    public void loginCourierNotPasswordStatusCode(){
        int status = courierApiClient.getCourier(courier).extract().statusCode();
        assertThat("Status code error", status, equalTo(statusCodeExpected));
    }
    @Test
    public void loginCourierNotPasswordNotEnoughDataMessage(){
        String message = courierApiClient.getCourier(courier).extract().path("message");
        assertThat("Message error is NotEnoughData", message, equalTo(messageExpected));
    }

    @After
    public void deleteCourier(){
        int id = courierApiClient.getCourier(courierNew).extract().path("id");
        courierApiClient.deleteCourier(String.valueOf(id));
    }
}
