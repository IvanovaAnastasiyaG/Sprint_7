package ru.yandex.practickum;

import org.apache.http.HttpStatus;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import client.CourierApiClient;
import model.Courier;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static ru.yandex.practickum.Constants.CREATE_COURIER_LOGIN_ALREADY_USE;

@RunWith(Parameterized.class)
public class CreateCourierTest {

    Courier courier;

    CourierApiClient courierApiClient = new CourierApiClient();

    public CreateCourierTest(Courier courier){
        this.courier = courier;
    }

    @Parameterized.Parameters
    public static Object[][] getCreateCourierDate() {
        return new Object[][] {
                { new Courier("courier-test", "1234", "Jack")},
                { new Courier("courier-test", "1234", null)},
        };
    }

    @Test
    public void createNewCourierIsCreate(){
        courierApiClient.createCourier(courier);
        String courierId = courierApiClient.getCourier(courier).extract().path("id").toString();
        assertThat("Courier's id is exist", courierId, notNullValue());
    }

    @Test
    public void createNewCourierStatusCode201(){
        int status = courierApiClient.createCourier(courier).extract().statusCode();
        assertThat("Status code is 201", status, equalTo(HttpStatus.SC_CREATED));
    }

    @Test
    public void createNewCourierReturnOk(){
        boolean ok = courierApiClient.createCourier(courier).extract().path("ok");
        assertThat("Success request is ok", ok, equalTo(true));
    }

    @Test
    public void createExistCourierStatusCode409(){
        courierApiClient.createCourier(courier);
        int status = courierApiClient.createCourier(courier).extract().statusCode();
        assertThat("Status code is 409", status, equalTo(HttpStatus.SC_CONFLICT));
    }

    @Test
    public void createExistCourierRequiredFieldsReturnIsExist(){
        courierApiClient.createCourier(courier);
        String message = courierApiClient.createCourier(courier).extract().path("message");
        assertThat("Message error is login already in use", message, equalTo(CREATE_COURIER_LOGIN_ALREADY_USE));
    }

    @After
    public void deleteCourier(){
        int id = courierApiClient.getCourier(courier).extract().path("id");
        courierApiClient.deleteCourier(String.valueOf(id));
    }

}
