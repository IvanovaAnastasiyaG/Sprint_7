package client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.Courier;

public class CourierApiClient extends BaseHttpClient {

    private final String endPointLogin = "/api/v1/courier/login";
    private final String endPointCreate = "/api/v1/courier";
    private final String endPointDelete = "/api/v1/courier/";

    @Step("Получение id курьера")
    public ValidatableResponse getCourier(Courier courier) {
        return doPostRequest(baseUrl + endPointLogin, courier);
    }

    @Step("Создание курьера")
    public ValidatableResponse createCourier(Courier courier) {
        return doPostRequest(baseUrl + endPointCreate, courier);
    }

    @Step("Удаление курьера")
    public ValidatableResponse deleteCourier(String id) {
        return doDeleteRequest(baseUrl + endPointDelete + id);
    }

}
