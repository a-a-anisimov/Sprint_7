import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class OrderListTest {

    @BeforeClass
    public static void log() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test // получение общего списка заказов без заполнения полей, т.к. ни одно из них не является обязательным
    public void GetOrderList() {
        String json = "{\"courierId\": \"\", \"nearestStation\": \"\", \"limit\": \"\", \"page\": \"\"}";
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .get("/api/v1/orders")
                .then().statusCode(200) //провекра кода ответа
                .and()
                .assertThat().body("orders", notNullValue()); //успешный запрос возвращает все orders
    }
}
