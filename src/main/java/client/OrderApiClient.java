package client;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import model.Order;

import java.util.List;

public class OrderApiClient extends BaseHttpClient {

    private final String endPointCreate = "/api/v1/orders";
    private final String endPointGetOrders = "/api/v1/orders";
    private final String endPointDelete = "/api/v1/orders/cancel";

    @Step("Получение списка заказов")
    public List<Order> getOrders() {
        return doGetRequest(baseUrl + endPointGetOrders).extract().path("orders");
    }

    @Step("Создание заказа")
    public ValidatableResponse createOrder(Order order) {
        return doPostRequest(baseUrl + endPointCreate, order);
    }

    @Step("Удаление заказа")
    public ValidatableResponse deleteOrder(int id) {
        return doPutRequest(baseUrl + endPointDelete, new Order(id));
    }

}
