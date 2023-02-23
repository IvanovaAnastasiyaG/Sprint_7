package ru.yandex.practickum;

import org.junit.Test;
import client.OrderApiClient;
import model.Order;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;


public class GetOrdersTest {

    OrderApiClient orderApiClient = new OrderApiClient();

    @Test
    public void getOrdersReturnOrdersList() {
        boolean isListOrders = true;
        List<Order> orders = orderApiClient.getOrders();
        if (orders == null) {
            isListOrders = false;
        }
        assertThat("Is orders list: true", isListOrders, equalTo(true));
    }
}
