import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;

public class CreateNewCourierTest {

    @Test //для проверки создания нового курьера необходимо создавать каждый раз новое оригинальное имя
    public void CreateNewCourier() {
        String json = "{\"login\": \"Andrey10\", \"password\": \"P@ssw0rd123\", \"firstName\": \"Sparrow000\"}";
        boolean created = given().log().all()
                .header("Content-Type", "application/json")
                .baseUri("https://qa-scooter.praktikum-services.ru")
                .body(json)
                .when()
                .post("/api/v1/courier")
                .then().log().all()
                .assertThat()
                .statusCode(201) //проверка правильности кода ответа
                .extract()
                .path("ok",": true!!!"); //проверка правильности содержания ответа
    }
        @Test //для проверки невозможности создания двух курьеров с одинаковыми именами необходимо поменять название на то, которое указано в Тесте выше
        public void CreateNewCourierWithTheSameName() {
            String json = "{\"login\": \"Andrey1\", \"password\": \"P@ssw0rd123\", \"firstName\": \"Sparrow000\"}";
            boolean created = given().log().all()
                    .header("Content-Type", "application/json")
                    .baseUri("https://qa-scooter.praktikum-services.ru")
                    .body(json)
                    .when()
                    .post("/api/v1/courier")
                    .then().log().all()
                    .assertThat()
                    .statusCode(409) //будет ошибка с кодом 409 и сообщением "Этот логин уже используется. Попробуйте другой."
                    .extract()
                    .path("code")
                    ;
    }
    @Test //для проверки невозможности создания двух курьеров с одинаковыми именами необходимо поменять название на то, которое указано в Тесте выше
    public void CreateNewCourierWithTheSameName1() {
        String json = "{\"login\": \"Andrey14\"}";
        boolean created = given().log().all()
                .header("Content-Type", "application/json")
                .baseUri("https://qa-scooter.praktikum-services.ru")
                .body(json)
                .when()
                .post("/api/v1/courier")
                .then().log().all()
                .assertThat()
                .statusCode(201) //будет ошибка с кодом 409 и сообщением "Этот логин уже используется. Попробуйте другой."
                .extract()
                .path("ok")
                ;
    }
}
