package ru.yandex.practickum;

import client.OrderApiClient;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import model.Order;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class GetOrdersTest {
    OrderApiClient orderApiClient = new OrderApiClient();

    @Test
    @DisplayName("Получение полного списка заказов")
    public void getOrdersReturnOrdersList() {
        boolean isListOrders = true;
        List<Order> orders = orderApiClient.getOrders();
        if (orders == null) {
            isListOrders = false;
        }
        assertThat("Is orders list: true", isListOrders, equalTo(true));
    }
}
