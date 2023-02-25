package ru.yandex.practickum;

import client.OrderApiClient;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import model.Color;
import model.Order;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    OrderApiClient orderApiClient = new OrderApiClient();
    Order order = new Order(
            "Naruto",
            "Uchiha",
            "Konoha, 142 apt.",
            "4",
            "+7 800 355 35 35",
            5,
            "2020-06-06",
            "Saske, come back to Konoha");
    Color[] colors;

    public CreateOrderTest(Color[] colors) {
        this.colors = colors;
    }

    @Parameterized.Parameters(name = "Создание заказа. Тестовые данные: {0}")
    public static Color[][][] setData() {
        return new Color[][][]{
                {{Color.BLACK}},
                {{Color.GREY}},
                {{Color.BLACK, Color.GREY}},
                {{null}}
        };
    }

    @Test
    @DisplayName("Создание заказа. Код 201. Возращен трек-номер")
    public void createOrderDifferentColorAndReturnTrack() {
        order.setColor(Arrays.asList(colors));
        ValidatableResponse response = orderApiClient.createOrder(order);
        int status = response.extract().statusCode();
        int track = response.extract().path("track");
        orderApiClient.deleteOrder(track);
        assertThat("Status code is 201", status, equalTo(HttpStatus.SC_CREATED));
        assertThat("Courier's id is exist", track, notNullValue());
    }

}
