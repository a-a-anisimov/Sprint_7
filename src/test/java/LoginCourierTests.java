import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class LoginCourierTest {
    @BeforeClass
    public static void log() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Before
    public void setUp() {
        RestAssured.baseURI = "https://qa-scooter.praktikum-services.ru";
    }

    @Test //проверка возможности авторизации и присвоения id новому курьеру
    public void LoginCourierStatusCodeAndId() {
        String json = "{\"login\": \"Andrey1\", \"password\": \"P@ssw0rd123\", \"firstName\": \"Sparrow000\"}";
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login")
                .then().statusCode(200) //провекра кода ответа
                .and()
                .assertThat().body("id", notNullValue()); //успешный запрос возвращает id
    }

    @Test //если авторизоваться под несуществующим пользователем - запрос возвращает код 404 и сообщение с ошибкой
    public void LoginCourierNonExistentName() {
        String json = "{\"login\": \"Andrey0\", \"password\": \"P@ssw0rd123\", \"firstName\": \"Sparrow000\"}";
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login")
                .then().statusCode(404) //провекра кода ответа
                .and()
                .assertThat().body("message", equalTo("Учетная запись не найдена")); // запрос возвращает ошибку
    }
    @Test //попытка авторизоваться c неверным пароллем пользователя - запрос возвращает код 404 и сообщение с ошибкой
    public void LoginCourierWithWrongPassword() {
        String json = "{\"login\": \"Andrey0\", \"password\": \"WrongPassw0rd\", \"firstName\": \"Sparrow000\"}";
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login")
                .then().statusCode(404) //провекра кода ответа
                .and()
                .assertThat().body("message", equalTo("Учетная запись не найдена")); // запрос возвращает ошибку
    }

    @Test //попытка авторизоваться без логина -  запрос возвращает код 400 и сообщение с ошибкой
    public void LoginCourierWithouLogin() {
        String json = "{\"login\": \"\", \"password\": \"WrongPassw0rd\", \"firstName\": \"Sparrow000\"}";
        given()
                .header("Content-type", "application/json")
                .and()
                .body(json)
                .when()
                .post("/api/v1/courier/login")
                .then().statusCode(400) //провекра кода ответа
                .and()
                .assertThat().body("message", equalTo("Недостаточно данных для входа")); // запрос возвращает ошибку
    }
}
