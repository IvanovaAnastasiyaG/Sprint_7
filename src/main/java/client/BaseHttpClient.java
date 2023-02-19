package client;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class BaseHttpClient {

    protected final String baseUrl = "https://qa-scooter.praktikum-services.ru";

    private RequestSpecification baseSpec() {
        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .build();
    }

    protected ValidatableResponse doGetRequest(String uri) {
        return given().spec(baseSpec())
                .get(uri).then();
    }

    protected ValidatableResponse doPostRequest(String uri, Object body) {
        return given().spec(baseSpec())
                .body(body)
                .post(uri).then();
    }

    protected ValidatableResponse doPutRequest(String uri, Object body) {
        return given().spec(baseSpec())
                .body(body)
                .put(uri).then();
    }

    protected ValidatableResponse doDeleteRequest(String uri) {
        return given().spec(baseSpec())
                .delete(uri).then();
    }
}