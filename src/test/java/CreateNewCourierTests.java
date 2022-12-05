import io.qameta.allure.Description;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class CreateNewCourierTests {

    @BeforeClass
    public static void log() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    //для проверки создания нового курьера необходимо создавать каждый раз новое оригинальное имя!!!

    @Test //проверка возмжности создания нового курьера, статус кода и содержания тела ответа
    @Description("Always create original login")
    public void CreateNewCourier() {
        String json = "{\"login\": \"Andrey21\", \"password\": \"P@ssw0rd123\", \"firstName\": \"Sparrow000\"}";
        given()
                .header("Content-Type", "application/json")
                .baseUri("https://qa-scooter.praktikum-services.ru")
                .body(json)
                .when()
                .post("/api/v1/courier")
                .then()
                .statusCode(201) //провекра кода ответа
                .and()
                .assertThat().body("ok", equalTo(true)); // успешный запрос возвращает ok: true
    }

    @Test
    //для проверки невозможности создания двух курьеров с одинаковыми именами необходимо поменять название на то, которое указано в Тесте выше или было создано ранее
    public void CreateNewCourierWithTheSameName() {
        String json = "{\"login\": \"Andrey1\", \"password\": \"P@ssw0rd123\", \"firstName\": \"Sparrow000\"}";
        given()
                .header("Content-Type", "application/json")
                .baseUri("https://qa-scooter.praktikum-services.ru")
                .body(json)
                .when()
                .post("/api/v1/courier")
                .then()
                .assertThat()
                .statusCode(409) //ошибки не будет, т.к. сразу закладываем ожидаемый код 409, а не 201
                .and()
                .assertThat().body("message", equalTo("Этот логин уже используется. Попробуйте другой.")); // запрос возвращает ошибку
        ;
    }

    @Test //для проверки возможности создания нового курьера без обязательного поля "login"
    public void CreateNewCourierWithoutLogin() {
        String json = "{\"login\": \"\", \"password\": \"P@ssw0rd123\", \"firstName\": \"Sparrow000\"}";
        given()
                .header("Content-Type", "application/json")
                .baseUri("https://qa-scooter.praktikum-services.ru")
                .body(json)
                .when()
                .post("/api/v1/courier")
                .then()
                .assertThat()
                .statusCode(400) //ошибки не будет, т.к. сразу закладываем ожидаемый код 400, а не 201
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи")); // запрос возвращает ошибку
        ;
    }

    @Test //для проверки возможности создания нового курьера без обязательного поля "password"
    @Description("Always create original login")
    public void CreateNewCourierWithoutPassword() {
        String json = "{\"login\": \"Andrey22\", \"password\": \"\", \"firstName\": \"Sparrow000\"}";
        given()
                .header("Content-Type", "application/json")
                .baseUri("https://qa-scooter.praktikum-services.ru")
                .body(json)
                .when()
                .post("/api/v1/courier")
                .then()
                .assertThat()
                .statusCode(400) //ошибки не будет, т.к. сразу закладываем ожидаемый код 400, а не 201
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для создания учетной записи")); // запрос возвращает ошибку
        ;
    }

    @Test //для проверки возможности создания нового курьера без поля "firstName"
    @Description("Always create original login")
    public void CreateNewCourierWithoutFirstName() {
        String json = "{\"login\": \"Andrey23\", \"password\": \"P@ssw0rd123\", \"firstName\": \"\"}";
        given()
                .header("Content-Type", "application/json")
                .baseUri("https://qa-scooter.praktikum-services.ru")
                .body(json)
                .when()
                .post("/api/v1/courier")
                .then()
                .assertThat()
                .statusCode(201) //ошибки не будет, т.к. поле не является обязательным
                .and()
                .assertThat().body("ok", equalTo(true)); // запрос возвращает ok: true
        ;
    }
}
