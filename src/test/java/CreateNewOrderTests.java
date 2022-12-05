import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collections;

import static io.restassured.RestAssured.given;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class CreateNewOrderTest {

    @BeforeClass
    public static void log() { RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter()); }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }
    private String firstName;
    private String lastName;
    private String address;
    private String metroStation;
    private String phone;
    private int rentTime;
    private String deliveryDate;
    private String comment;
    private List<Color> color;

    public CreateOrderTests(String firstName, String lastName, String address, String metroStation, String phone, int rentTime, String deliveryDate, String comment, List color) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.address = address;
        this.metroStation = metroStation;
        this.phone = phone;
        this.rentTime = rentTime;
        this.deliveryDate = deliveryDate;
        this.comment = comment;
        this.color = color;
    @Test
    public void CreateNewCourierStatusCode1() {
        Order order = new Order("Андрей", "Анисимов", "СПб", "1", "+7 921 355 35 35", 3, "2022-06-12", "Тра-ля-ля",
                Arrays.asList("BLACK"));
        given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post("/api/v1/orders")
                .then().statusCode(201);
    }
    @Test
    public void CreateNewCourierStatusCode2() {
        Order order = new Order("Андрей", "Анисимов", "СПб", "1", "+7 921 355 35 35", 3, "2022-06-12", "Тра-ля-ля",
                Collections.singletonList("GREY"));
        given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post("/api/v1/orders")
                .then().statusCode(201);
    }
    @Test
    public void CreateNewCourierStatusCode3() {
        Order order = new Order("Андрей", "Анисимов", "СПб", "1", "+7 921 355 35 35", 3, "2022-06-12", "Тра-ля-ля",
                Collections.singletonList("BLACK, GREY"));
        given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post("/api/v1/orders")
                .then().statusCode(201);
    }
}