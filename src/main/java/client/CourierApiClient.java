package client;

import io.restassured.response.ValidatableResponse;
import model.Courier;

public class CourierApiClient extends BaseHttpClient {

    private final String endPointLogin = "/api/v1/courier/login";
    private final String endPointCreate = "/api/v1/courier";
    private final String endPointDelete = "/api/v1/courier/";

    public ValidatableResponse getCourier(Courier courier) {
        return doPostRequest(baseUrl + endPointLogin, courier);
    }

    public ValidatableResponse createCourier(Courier courier) {
        return doPostRequest(baseUrl + endPointCreate, courier);
    }

    public ValidatableResponse deleteCourier(String id) {
        return doDeleteRequest(baseUrl + endPointDelete + id);
    }

}
