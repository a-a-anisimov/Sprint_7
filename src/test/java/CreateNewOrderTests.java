import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)

public class CreateNewOrderTests<firstName, lastName, address, metroStation, phone, color, comment, deliveryDate, rentTime> {

    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List<String> color;

    public CreateNewOrderTests(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, List color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    }


    @Parameterized.Parameters(name = "firstName = {0}, lastName = {1}, address = {2}, metroStation = {3}, phone = {4}, rentTime = {5}, " +
            "deliveryDate = {6}, comment = {7}, color = {8}")

    public static Object[][] getData() {
        return new Object[][]{
                {"Андрей", "Анисимов", "Дунайский, 5, 45", "1", "+7 921 777 77 77", 1, "2022-12-12", "Comment1", Arrays.asList("BLACK", "GREY")},
                {"Семён", "Семёнов", "Среднерогатская, 20, 450", "14", "+7 911 111 11 11", 3, "2022-12-17", "Comment2", Arrays.asList("BLACK")},
                {"Иван", "Иванов", "Петрововка, 38, 13", "101", "+7 901 999 99 99", 7, "2022-12-25", "Comment3", null},
        };
    }

    @BeforeClass
    public static void log() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test
    public void CreateNewCourierStatusCode() {
        Order order = new Order(firstName, lastName, address, metroStation, phone, rentTime, deliveryDate, comment, color);
        given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post("/api/v1/orders")
                .then().statusCode(201) //успешное создание заказа с разным набором данных
                .and()
                .assertThat().body("track", notNullValue()); //тело ответа содержит track
    }
}